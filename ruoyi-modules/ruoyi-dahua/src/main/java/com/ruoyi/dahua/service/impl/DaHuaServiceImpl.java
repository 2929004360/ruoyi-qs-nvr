package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.dahua.api.domain.*;
import com.ruoyi.dahua.api.domain.DahuaStorageInfo;
import com.ruoyi.dahua.common.ErrorCode;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.config.DahuaConfig;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.structure.NET_IN_STORAGE_DEV_INFOS;
import com.ruoyi.dahua.lib.structure.NET_OUT_STORAGE_DEV_INFOS;
import com.ruoyi.dahua.lib.structure.NET_STORAGE_DEVICE;
import com.ruoyi.dahua.lib.structure.NET_STORAGE_PARTITION;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.manager.StreamManager;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;
import com.ruoyi.dahua.service.IDahuaMediaStreamService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.RemoteQsDeviceAlarmService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.ruoyi.qs.api.domain.QsDeviceAlarm;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 大华sdk 服务
 *
 * @FileName DaHuaServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-30
 **/
@Service
@Slf4j
public class DaHuaServiceImpl implements IDaHuaService {

    @Autowired
    private IDahuaMediaStreamService mediaStreamService;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteQsDeviceSnapshotService remoteQsDeviceSnapshotService;

    @Autowired
    private RemoteQsDeviceAlarmService remoteQsDeviceAlarmService;

    @Autowired
    private org.springframework.data.redis.core.RedisTemplate redisTemplate;

    @Autowired
    private DahuaConfig dahuaConfig;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.prefix}")
    private String filePrefix;

    public static final NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;

    // 存储抓图回调信息
    private final Map<Integer, CaptureContext> captureContextMap = new ConcurrentHashMap<>();
    private int captureCmdSerial = 1;

    // 抓图回调单例
    private static final SnapReceiveCallback snapReceiveCallback = new SnapReceiveCallback();

    // 告警回调单例 - 必须先声明并初始化
    private static final AlarmCallback alarmCallback = new AlarmCallback();

    // 告警去重：记录最近的告警，避免短时间内重复保存
    private static final java.util.Map<String, Long> recentAlarms = new java.util.concurrent.ConcurrentHashMap<>();
    private static final long ALARM_DEDUP_INTERVAL = 300000; // 5分钟内相同告警只保存一次，大幅减少数据量
    private static final long CACHE_CLEAN_INTERVAL = 60000; // 每分钟清理一次过期缓存
    private static volatile boolean cacheCleanerRunning = false;

    // 报警抓图任务队列
    private static final java.util.concurrent.BlockingQueue<AlarmCaptureTask> alarmCaptureQueue = 
            new java.util.concurrent.LinkedBlockingQueue<>(1000);
    private static volatile boolean captureProcessorRunning = false;
    private static Thread captureProcessorThread;

    static {
        // 设置抓图回调
        netsdk.CLIENT_SetSnapRevCallBack(snapReceiveCallback, null);
        // 设置告警回调
        netsdk.CLIENT_SetDVRMessCallBackEx1(alarmCallback, null);
        // 启动报警抓图处理线程
        startCaptureProcessor();
        // 启动去重缓存清理线程
        startCacheCleaner();
    }

    // 启动去重缓存清理线程
    private static void startCacheCleaner() {
        if (cacheCleanerRunning) {
            return;
        }
        cacheCleanerRunning = true;
        Thread cleanerThread = new Thread(() -> {
            log.info("去重缓存清理线程启动");
            while (cacheCleanerRunning) {
                try {
                    Thread.sleep(CACHE_CLEAN_INTERVAL);
                    cleanExpiredCache();
                } catch (InterruptedException e) {
                    log.warn("去重缓存清理线程被中断");
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("去重缓存清理线程异常", e);
                }
            }
            log.info("去重缓存清理线程停止");
        }, "Dahua-Alarm-Cache-Cleaner");
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    // 清理过期的去重缓存
    private static void cleanExpiredCache() {
        long now = System.currentTimeMillis();
        int beforeSize = recentAlarms.size();
        recentAlarms.entrySet().removeIf(entry -> (now - entry.getValue()) > ALARM_DEDUP_INTERVAL * 2);
        int afterSize = recentAlarms.size();
        if (beforeSize != afterSize) {
            log.debug("清理过期去重缓存，清理前:{}, 清理后:{}", beforeSize, afterSize);
        }
    }

    // 报警抓图任务类
    public static class AlarmCaptureTask {
        public Long deviceId;
        public String deviceCode;
        public String deviceName;
        public Long alarmId;
        public int channelId;
        public String ipAddress;
        public int retryCount;

        public AlarmCaptureTask(Long deviceId, String deviceCode, String deviceName, Long alarmId, int channelId, String ipAddress) {
            this.deviceId = deviceId;
            this.deviceCode = deviceCode;
            this.deviceName = deviceName;
            this.alarmId = alarmId;
            this.channelId = channelId;
            this.ipAddress = ipAddress;
            this.retryCount = 0;
        }
    }

    // 启动抓图处理线程
    private static void startCaptureProcessor() {
        if (captureProcessorRunning) {
            return;
        }
        captureProcessorRunning = true;
        captureProcessorThread = new Thread(() -> {
            log.info("报警抓图处理线程启动");
            while (captureProcessorRunning) {
                try {
                    AlarmCaptureTask task = alarmCaptureQueue.take();
                    log.info("处理报警抓图任务, deviceId:{}, alarmId:{}, channelId:{}, retryCount:{}", 
                            task.deviceId, task.alarmId, task.channelId, task.retryCount);
                    
                    // 获取Service实例执行抓图
                    DaHuaServiceImpl service = com.ruoyi.common.core.utils.SpringUtils.getBean(DaHuaServiceImpl.class);
                    if (service != null) {
                        try {
                            Long snapshotId = service.captureAndSaveForAlarm(task);
                            log.info("报警抓图完成, snapshotId:{}, alarmId:{}", snapshotId, task.alarmId);
                        } catch (Exception e) {
                            log.error("报警抓图处理异常, deviceId:{}, alarmId:{}, channelId:{}, retryCount:{}", 
                                    task.deviceId, task.alarmId, task.channelId, task.retryCount, e);
                            
                            // 失败后重试，最多重试5次
                            if (task.retryCount < 5) {
                                task.retryCount++;
                                
                                // 尝试不同的通道号
                                if (task.retryCount == 1) {
                                    task.channelId = 0; // 尝试通道0
                                } else if (task.retryCount == 2) {
                                    task.channelId = 1; // 尝试通道1
                                } else if (task.retryCount == 3) {
                                    task.channelId = task.channelId == 0 ? 1 : 0; // 切换通道
                                }
                                
                                log.info("报警抓图任务重新加入队列, deviceId:{}, alarmId:{}, newChannelId:{}, retryCount:{}", 
                                        task.deviceId, task.alarmId, task.channelId, task.retryCount);
                                
                                // 延迟2秒后重试
                                Thread.sleep(2000);
                                alarmCaptureQueue.offer(task);
                            } else {
                                log.error("报警抓图任务重试次数已达上限，放弃, deviceId:{}, alarmId:{}", 
                                        task.deviceId, task.alarmId);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    log.warn("报警抓图处理线程被中断");
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("报警抓图处理线程异常", e);
                }
            }
            log.info("报警抓图处理线程停止");
        }, "Dahua-Alarm-Capture-Processor");
        captureProcessorThread.setDaemon(true);
        captureProcessorThread.start();
    }

    // 告警回调
    private static class AlarmCallback implements NetSDKLib.fMessCallBackEx1 {
        @Override
        public boolean invoke(int lCommand, NetSDKLib.LLong lLoginID, Pointer pStuEvent, int dwBufLen,
                               String strDeviceIP, NativeLong nDevicePort, int bAlarmAckFlag,
                               NativeLong nEventID, Pointer dwUser) {
            // 获取DaHuaServiceImpl实例
            DaHuaServiceImpl service = null;
            try {
                service = com.ruoyi.common.core.utils.SpringUtils.getBean(DaHuaServiceImpl.class);
            } catch (Exception e) {
                log.error("获取DaHuaServiceImpl失败", e);
                return false;
            }

            if (service == null) {
                log.error("DaHuaServiceImpl实例为空");
                return false;
            }

            // 检查配置是否开启报警监听
            if (!service.dahuaConfig.isEnableAlarmListen()) {
                log.debug("告警监听已禁用，跳过处理, 命令码:{}", lCommand);
                return true;
            }

            // 过滤垃圾报警，只保留关键的安全报警
            boolean isCriticalAlarm = isCriticalAlarm(lCommand);
            if (!isCriticalAlarm) {
                log.debug("非关键报警，跳过处理, 命令码:0x{}", Integer.toHexString(lCommand));
                return true;
            }

            log.info("收到告警回调, 命令码(lCommand):{}, 设备IP(strDeviceIP):{}, 设备端口(nDevicePort):{}, 数据长度(dwBufLen):{}, 事件ID(nEventID):{}", 
                    lCommand, strDeviceIP, nDevicePort, dwBufLen, nEventID);
            log.debug("告警回调详细信息 - 登录句柄(lLoginID):{}, 报警确认标志(bAlarmAckFlag):{}, 事件数据指针(pStuEvent):{}", 
                    lLoginID, bAlarmAckFlag, pStuEvent);

            try {
                // 根据登录句柄查找设备信息
                String deviceKey = null;
                for (Map.Entry<String, NetSDKLib.LLong> entry : loginHandleHandleMap.entrySet()) {
                    if (entry.getValue().longValue() == lLoginID.longValue()) {
                        deviceKey = entry.getKey();
                        break;
                    }
                }

                if (deviceKey != null) {
                    // 从deviceKey中提取IP
                    String ip = deviceKey.replace("login:handle:", "");
                    // 通过IP查找设备
                    QsDevice queryParam = new QsDevice();
                    queryParam.setIpAddress(ip);
                    com.ruoyi.common.core.domain.R<List<QsDevice>> listResult = service.remoteQsDeviceService.list(
                            queryParam, com.ruoyi.common.core.constant.SecurityConstants.INNER);

                    QsDevice qsDevice = null;
                    if (com.ruoyi.common.core.domain.R.isSuccess(listResult) 
                            && listResult.getData() != null 
                            && !listResult.getData().isEmpty()) {
                        // 只选择大华SDK类型的设备（type="9"）
                        List<QsDevice> devices = listResult.getData();
                        log.info("查询到 {} 个IP为 {} 的设备，开始筛选大华SDK设备", devices.size(), ip);
                        for (QsDevice device : devices) {
                            log.debug("设备信息 - 设备ID:{}, 设备名称:{}, 设备类型(type):{}", 
                                    device.getId(), device.getDeviceName(), device.getType());
                            if ("9".equals(device.getType())) {
                                qsDevice = device;
                                log.info("找到匹配的大华SDK设备 - 设备ID:{}, 设备名称:{}", qsDevice.getId(), qsDevice.getDeviceName());
                                break;
                            }
                        }
                        
                        if (qsDevice == null && !devices.isEmpty()) {
                            log.warn("IP为 {} 的设备中没有找到大华SDK类型的设备，但找到 {} 个其他类型设备", ip, devices.size());
                        }
                    }

                    if (qsDevice != null) {
                        // 解析报警状态：判断是报警开始还是报警结束
                        boolean isAlarmStart = parseAlarmState(lCommand, pStuEvent, dwBufLen);
                        
                        // 完全忽略结束事件，只处理开始事件
                        if (!isAlarmStart) {
                            log.debug("忽略报警结束事件, deviceCode:{}, command:{}", qsDevice.getDeviceCode(), lCommand);
                            return true;
                        }
                        
                        // 获取设备配置的通道号
                        Integer channel = qsDevice.getAlarmChannelId();
                        if (channel == null) {
                            channel = qsDevice.getChannel();
                        }
                        if (channel == null) {
                            channel = 0;
                        }
                        log.info("使用设备配置的通道号, deviceCode:{}, channel:{}, command:{}", 
                                qsDevice.getDeviceCode(), channel, lCommand);
                        
                        // 解析告警事件类型（不添加_end后缀，只使用原始类型）
                        String alarmType = convertAlarmType(lCommand, true); // 强制用开始事件类型
                        String alarmContent = convertAlarmContent(lCommand);

                        // 告警去重：同一设备同一通道同一类型5分钟内只记录一次
                        String alarmKey = qsDevice.getDeviceCode() + "_" + channel + "_" + alarmType + "_" + lCommand;
                        long now = System.currentTimeMillis();
                        Long lastTime = recentAlarms.get(alarmKey);
                        
                        // 如果在去重间隔内，直接跳过
                        if (lastTime != null && (now - lastTime) < ALARM_DEDUP_INTERVAL) {
                            log.debug("跳过重复告警, deviceCode:{}, channel:{}, alarmType:{}", 
                                    qsDevice.getDeviceCode(), channel, alarmType);
                            return true;
                        }
                        
                        recentAlarms.put(alarmKey, now);

                        // 构造告警记录
                        QsDeviceAlarm alarm = new QsDeviceAlarm();
                        alarm.setDeviceId(qsDevice.getId());
                        alarm.setDeviceCode(qsDevice.getDeviceCode());
                        alarm.setDeviceName(qsDevice.getDeviceName());
                        alarm.setSdkType("dahua");
                        alarm.setAlarmTime(new Date());
                        alarm.setHandled(0);
                        alarm.setChannel(channel); // 设置设备配置的通道号

                        alarm.setAlarmType(alarmType);

                        // 设置告警级别，默认为中等
                        alarm.setAlarmLevel("medium");

                        // 设置告警内容为中文
                        alarm.setAlarmContent(alarmContent);

                        log.info("处理告警, deviceCode:{}, channel:{}, alarmType:{}", 
                                alarm.getDeviceCode(), channel, alarmType);

                        // 保存到数据库
                        com.ruoyi.common.core.domain.R<Long> result = service.remoteQsDeviceAlarmService.add(
                                alarm, com.ruoyi.common.core.constant.SecurityConstants.INNER);

                        if (com.ruoyi.common.core.domain.R.isSuccess(result)) {
                            Long alarmId = result.getData();
                            log.info("告警记录保存成功, alarmId:{}, channel:{}", alarmId, channel);
                            
                            // 把报警ID存入Redis，供定时任务处理
                            String redisKey = "dahua_alarm_pic_list:" + qsDevice.getDeviceCode();
                            service.redisTemplate.opsForList().leftPush(redisKey, alarmId);
                            service.redisTemplate.opsForList().trim(redisKey, 0, 49);
                            service.redisTemplate.expire(redisKey, 30, java.util.concurrent.TimeUnit.MINUTES);
                            log.info("报警ID已存入Redis, key:{}, alarmId:{}", redisKey, alarmId);
                            
                            // 同时也添加抓图任务到队列（双重保险）
                            AlarmCaptureTask captureTask = new AlarmCaptureTask(
                                    qsDevice.getId(),
                                    qsDevice.getDeviceCode(),
                                    qsDevice.getDeviceName(),
                                    alarmId,
                                    channel, // 使用设备配置的通道号
                                    ip
                            );
                            boolean offered = alarmCaptureQueue.offer(captureTask);
                            if (offered) {
                                log.info("报警抓图任务已加入队列, alarmId:{}, deviceId:{}, channel:{}", 
                                        alarmId, qsDevice.getId(), channel);
                            } else {
                                log.warn("报警抓图队列已满，任务被丢弃, alarmId:{}", alarmId);
                            }
                        } else {
                            log.error("保存告警记录失败: {}", result.getMsg());
                        }
                    } else {
                        log.warn("未找到设备信息, IP:{}", ip);
                    }
                }
            } catch (Exception e) {
                log.error("处理告警异常", e);
            }

            return true;
        }
        
        // 解析报警状态
        private boolean parseAlarmState(int lCommand, Pointer pStuEvent, int dwBufLen) {
            if (pStuEvent == null || dwBufLen <= 0) {
                log.debug("无报警状态数据，默认视为报警开始");
                return true;
            }
            
            try {
                // 打印事件数据的十六进制，方便调试
                byte[] eventData = pStuEvent.getByteArray(0, dwBufLen);
                StringBuilder hexBuilder = new StringBuilder();
                for (byte b : eventData) {
                    hexBuilder.append(String.format("%02X ", b));
                }
                log.debug("报警事件原始数据(HEX): {}", hexBuilder.toString().trim());
                
                // 根据不同报警类型解析状态
                switch (lCommand) {
                    case NetSDKLib.NET_ALARM_ALARM_EX: // 0x2101 外部报警
                    case NetSDKLib.NET_MOTION_ALARM_EX: // 0x2102 动态检测报警
                    case NetSDKLib.NET_VIDEOLOST_ALARM_EX: // 0x2103 视频丢失报警
                    case NetSDKLib.NET_SHELTER_ALARM_EX: // 0x2104 视频遮挡报警
                    case NetSDKLib.NET_DISKFULL_ALARM_EX: // 0x2106 硬盘满报警
                    case NetSDKLib.NET_DISKERROR_ALARM_EX: // 0x2107 坏硬盘报警
                        // 读取第一个字节判断状态（1=有报警/开始，0=无报警/结束）
                        byte state = pStuEvent.getByte(0);
                        String stateText = (state != 0) ? "报警开始" : "报警结束";
                        log.info("解析报警状态 - 命令码:0x{}, 状态字节:{}, 状态:{}", 
                                Integer.toHexString(lCommand), state, stateText);
                        return state != 0;
                    default:
                        log.debug("未知报警类型，默认视为报警开始, lCommand:0x{}", Integer.toHexString(lCommand));
                        return true;
                }
            } catch (Exception e) {
                log.error("解析报警状态异常", e);
                return true;
            }
        }

        // 转换告警类型
        private String convertAlarmType(int lCommand, boolean isAlarmStart) {
            String suffix = isAlarmStart ? "" : "_end";
            switch (lCommand) {
                case NetSDKLib.NET_ALARM_ALARM_EX: // 0x2101 外部报警
                    return "alarm_ex" + suffix;
                case NetSDKLib.NET_MOTION_ALARM_EX: // 0x2102 动态检测报警
                    return "motion_ex" + suffix;
                case NetSDKLib.NET_VIDEOLOST_ALARM_EX: // 0x2103 视频丢失报警
                    return "video_loss_ex" + suffix;
                case NetSDKLib.NET_SHELTER_ALARM_EX: // 0x2104 视频遮挡报警
                    return "cover_ex" + suffix;
                case NetSDKLib.NET_DISKFULL_ALARM_EX: // 0x2106 硬盘满报警
                    return "disk_full" + suffix;
                case NetSDKLib.NET_DISKERROR_ALARM_EX: // 0x2107 坏硬盘报警
                    return "disk_error" + suffix;
                case 0x1000: // 动态检测报警
                    return "motion" + suffix;
                case 0x1001: // 视频丢失报警
                    return "video_loss" + suffix;
                case 0x1002: // 视频遮挡报警
                    return "cover" + suffix;
                case 0x1100: // 外部报警输入
                    return "alarm_in" + suffix;
                case 0x1101: // 外部报警输出
                    return "alarm_out" + suffix;
                default:
                    return "other_" + Integer.toHexString(lCommand) + suffix; // 其他告警，带上命令码
            }
        }

        // 转换报警内容为中文
        private String convertAlarmContent(int lCommand) {
            switch (lCommand) {
                case NetSDKLib.NET_ALARM_ALARM_EX: // 0x2101 外部报警
                case 0x1100: // 外部报警输入
                    return "外部报警触发";
                case NetSDKLib.NET_MOTION_ALARM_EX: // 0x2102 动态检测报警
                case 0x1000: // 动态检测报警
                    return "检测到移动目标";
                case NetSDKLib.NET_VIDEOLOST_ALARM_EX: // 0x2103 视频丢失报警
                case 0x1001: // 视频丢失报警
                    return "视频信号丢失";
                case NetSDKLib.NET_SHELTER_ALARM_EX: // 0x2104 视频遮挡报警
                case 0x1002: // 视频遮挡报警
                    return "视频遮挡报警";
                case NetSDKLib.NET_DISKFULL_ALARM_EX: // 0x2106 硬盘满报警
                    return "硬盘存储空间不足";
                case NetSDKLib.NET_DISKERROR_ALARM_EX: // 0x2107 坏硬盘报警
                    return "硬盘错误报警";
                case 0x1101: // 外部报警输出
                    return "外部报警输出触发";
                default:
                    return "未知报警事件，命令码:0x" + Integer.toHexString(lCommand);
            }
        }

        // 判断是否是关键的安全报警
        private boolean isCriticalAlarm(int lCommand) {
            switch (lCommand) {
                case NetSDKLib.NET_ALARM_ALARM_EX: // 0x2101 外部报警
                case 0x1100: // 外部报警输入
                case NetSDKLib.NET_MOTION_ALARM_EX: // 0x2102 动态检测报警
                case 0x1000: // 动态检测报警
                case NetSDKLib.NET_SHELTER_ALARM_EX: // 0x2104 视频遮挡报警
                case 0x1002: // 视频遮挡报警
                    return true;
                default:
                    return false;
            }
        }
    }

    public static final Map<String, NetSDKLib.LLong> loginHandleHandleMap = new ConcurrentHashMap<>();

    private final Map<String, NetSDKLib.NET_DEVICEINFO_Ex> deviceInfoMap = new ConcurrentHashMap<>();

    /**
     * 大华设备登录
     *
     * @param m_strIp       设备IP
     * @param m_nPort       设备端口
     * @param m_strUser     设备用户名
     * @param m_strPassword 设备密码
     * @param deviceId      设备ID
     * @param onlineType    上线类型(1=主动添加, 2=主动注册)
     */
    @Override
    public NetSDKLib.LLong loginDevice(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceId, String onlineType) {
        log.info("开始登录大华设备, IP:{}, Port:{}, deviceId:{}, onlineType:{}", m_strIp, m_nPort, deviceId, onlineType);
        String loginKey = "login:handle:" + m_strIp;

        NetSDKLib.LLong existingHandle = loginHandleHandleMap.get(loginKey);
        if (existingHandle != null && existingHandle.longValue() != 0) {
            log.warn("设备已登录，返回现有登录句柄, IP:{}, deviceId:{}", m_strIp, deviceId);
            return existingHandle;
        }

        NetSDKLib.LLong m_hLoginHandle;
        try {
            if ("2".equals(onlineType)) {
                log.debug("使用主动注册方式登录, IP:{}, deviceId:{}", m_strIp, deviceId);
                final int tcpSpecCap = 2;
                final IntByReference errorReference = new IntByReference(0);
                final NetSDKLib.NET_DEVICEINFO_Ex deviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();

                com.ruoyi.dahua.lib.NativeString serial = new com.ruoyi.dahua.lib.NativeString(deviceId);
                m_hLoginHandle = netsdk.CLIENT_LoginEx2(m_strIp, m_nPort, m_strUser, m_strPassword, tcpSpecCap, serial.getPointer(), deviceInfo, errorReference);
                if (0 == m_hLoginHandle.longValue()) {
                    String errorMsg = "大华设备登录失败, IP:" + m_strIp + ", Port:" + m_nPort + ", " + getErrorCodePrint();
                    log.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
                loginHandleHandleMap.put(loginKey, m_hLoginHandle);
                deviceInfoMap.put("device:info:" + m_strIp, deviceInfo);
                log.info("大华设备登录成功(主动注册), IP:{}, Port:{}, deviceId:{}", m_strIp, m_nPort, deviceId);
                // 启动告警监听
                if (dahuaConfig.isEnableAlarmListen()) {
                    boolean listenResult = netsdk.CLIENT_StartListenEx(m_hLoginHandle);
                    if (listenResult) {
                        log.info("大华设备告警监听启动成功, IP:{}", m_strIp);
                    } else {
                        log.warn("大华设备告警监听启动失败, IP:{}", m_strIp);
                    }
                } else {
                    log.info("告警监听已禁用, IP:{}", m_strIp);
                }
            } else {
                log.debug("使用普通方式登录, IP:{}, deviceId:{}", m_strIp, deviceId);
                NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam = new NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
                pstInParam.nPort = m_nPort;
                pstInParam.szIP = m_strIp.getBytes();
                pstInParam.szPassword = m_strPassword.getBytes();
                pstInParam.szUserName = m_strUser.getBytes();

                NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam = new NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
                NetSDKLib.NET_DEVICEINFO_Ex m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();
                pstOutParam.stuDeviceInfo = m_stDeviceInfo;

                m_hLoginHandle = netsdk.CLIENT_LoginWithHighLevelSecurity(pstInParam, pstOutParam);
                if (m_hLoginHandle.longValue() == 0) {
                    String errorMsg = "大华设备登录失败, IP:" + m_strIp + ", Port:" + m_nPort + ", " + getErrorCodePrint();
                    log.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
                loginHandleHandleMap.put(loginKey, m_hLoginHandle);
                deviceInfoMap.put("device:info:" + m_strIp, pstOutParam.stuDeviceInfo);
                log.info("大华设备登录成功, IP:{}, Port:{}, deviceId:{}", m_strIp, m_nPort, deviceId);
                // 启动告警监听
                if (dahuaConfig.isEnableAlarmListen()) {
                    boolean listenResult = netsdk.CLIENT_StartListenEx(m_hLoginHandle);
                    if (listenResult) {
                        log.info("大华设备告警监听启动成功, IP:{}", m_strIp);
                    } else {
                        log.warn("大华设备告警监听启动失败, IP:{}", m_strIp);
                    }
                } else {
                    log.info("告警监听已禁用, IP:{}", m_strIp);
                }
            }
            return m_hLoginHandle;
        } catch (Exception e) {
            log.error("大华设备登录异常, IP:{}, deviceId:{}", m_strIp, deviceId, e);
            throw e;
        }
    }

    /**
     * 查询是否登录
     *
     * @param ip 设备IP
     */
    @Override
    public Boolean isUserId(String ip) {
        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + ip);
        boolean isLoggedIn = lLong != null && lLong.longValue() != 0;
        log.debug("查询设备登录状态, IP:{}, isLoggedIn:{}", ip, isLoggedIn);
        return isLoggedIn;
    }

    /**
     * 大华设备获取时间
     *
     * @param ip 设备IP
     * @return
     */
    @Override
    public String getTime(String ip) {
        log.info("开始获取大华设备时间, IP:{}", ip);
        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + ip);
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, IP:{}", ip);
            throw new RuntimeException("大华设备未登录, IP:" + ip);
        }
        log.debug("设备已登录, IP:{}", ip);
        NetSDKLib.NET_TIME deviceTime = new NetSDKLib.NET_TIME();

        if (!netsdk.CLIENT_QueryDeviceTime(m_hLoginHandle, deviceTime, 3000)) {
            String errorMsg = "客户端查询设备时间失败, " + ToolKits.getErrorCodePrint();
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        String date = deviceTime.toStringTime();
        if (date == null) {
            log.error("获取大华设备时间失败，时间为空, IP:{}", ip);
            throw new ServiceException("获取大华设备时间失败");
        }
        date = date.replace("/", "-");
        log.info("获取大华设备时间成功, IP:{}, time:{}", ip, date);
        return date;
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备IP
     */
    @Override
    public DahuaDevice getDahuaDevice(String ip) {
        log.info("开始获取大华主动上线设备, IP:{}", ip);
        DahuaDevice dahuaDevice = DahuaCommandLineRunnerImpl.deviceMap.get(ip);
        if (dahuaDevice == null) {
            log.error("大华设备未注册, IP:{}", ip);
            throw new RuntimeException("大华设备未注册");
        }
        log.info("获取大华主动上线设备成功, IP:{}", ip);
        return dahuaDevice;
    }

    /**
     * 大华设备登出
     *
     * @param ip 设备IP
     * @return
     */
    @Override
    public Boolean logoutDevice(String ip) {
        String loginKey = "login:handle:" + ip;
        NetSDKLib.LLong lLong = loginHandleHandleMap.get(loginKey);

        if (lLong == null || lLong.longValue() == 0) {
            log.warn("设备未登录，无需登出, IP:{}", ip);
            return true;
        }

        try {
            log.info("开始登出大华设备, IP:{}", ip);

            cleanDeviceStreamResources(ip);

            boolean bRet = netsdk.CLIENT_Logout(lLong);
            if (bRet) {
                lLong.setValue(0);
                loginHandleHandleMap.remove(loginKey);
                deviceInfoMap.remove("device:info:" + ip);
                log.info("大华设备登出成功, IP:{}", ip);
            } else {
                log.error("大华设备登出失败, IP:{}, {}", ip, getErrorCodePrint());
            }
            return bRet;
        } catch (Exception e) {
            log.error("大华设备登出异常, IP:{}", ip, e);
            throw e;
        }
    }

    /**
     * 清理设备相关的流媒体资源
     */
    private void cleanDeviceStreamResources(String ip) {
        try {
            // 复制一份 key 列表，避免在遍历中修改集合
            java.util.List<String> streamKeysToClean = new java.util.ArrayList<>();
            StreamManager.streamKeyAndRealHandleMap.forEach((streamKey, handle) -> {
                if (streamKey.contains(ip)) {
                    streamKeysToClean.add(streamKey);
                }
            });

            // 逐个清理资源
            for (String streamKey : streamKeysToClean) {
                log.info("清理设备流媒体资源, streamKey:{}", streamKey);
                NetSDKLib.LLong handle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
                com.ruoyi.dahua.callback.FRealDatarTPCallback callback = StreamManager.streamKeyAndFRealDatarTPCallbackMap.get(streamKey);
                com.ruoyi.common.core.domain.RtpServerParam rtpParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);

                mediaStreamService.cleanupResources(streamKey, rtpParam, handle, callback);
            }

        } catch (Exception e) {
            log.error("清理设备流媒体资源异常, IP:{}", ip, e);
        }
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam 播放参数
     */
    @Override
    public void startPlay(RtpServerParam rtpServerParam) {
        log.info("开始播放大华设备流, deviceId:{}", rtpServerParam.getId());
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", rtpServerParam.getId(), r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}, channel:{}", device.getId(), device.getIpAddress(), device.getChannel());

        String streamKey = "dahua:play:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", device.getId(), device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }
        log.info("开始播放大华设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.startPlay(lLong, device, streamKey, rtpServerParam);
        log.info("播放大华设备流调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 停止播放
     *
     * @param id 设备ID
     */
    @Override
    public void stopPlay(Long id) {
        log.info("开始停止播放大华设备流, deviceId:{}", id);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}, channel:{}", device.getId(), device.getIpAddress(), device.getChannel());
        String streamKey = "dahua:play:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            log.warn("大华设备未登录，无法停止播放, deviceId:{}, IP:{}", id, device.getIpAddress());
            return;
        }
        log.info("停止播放大华设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.stopPlay(lLong, device.getId(), device.getChannel(), streamKey);
        log.info("停止播放大华设备流调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 大华设备云台控制（开始）
     *
     * @param direction 方向
     * @param id        设备id
     * @param speed     速度
     * @param channelId 通道id
     * @return
     */
    @Override
    public boolean ptzControlStart(String direction, Long id, Integer speed, int channelId) {
        log.info("开始大华设备云台控制(开始), deviceId:{}, direction:{}, speed:{}, channelId:{}", id, direction, speed, channelId);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        boolean result = false;
        if ("up".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_UP_CONTROL, 0, speed, 0, 0);
        } else if ("down".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_DOWN_CONTROL, 0, speed, 0, 0);
        } else if ("left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LEFT_CONTROL, 0, speed, 0, 0);
        } else if ("right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_RIGHT_CONTROL, 0, speed, 0, 0);
        } else if ("top-left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTTOP, 0, speed, 0, 0);
        } else if ("upper-right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTTOP, 0, speed, 0, 0);
        } else if ("bottom-left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTDOWN, 0, speed, 0, 0);
        } else if ("lower-right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTDOWN, 0, speed, 0, 0);
        } else if ("doubling+".equals(direction) || "zoomin".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_ADD_CONTROL, 0, speed, 0, 0);
        } else if ("doubling-".equals(direction) || "zoomout".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_DEC_CONTROL, 0, speed, 0, 0);
        } else if ("zoom+".equals(direction) || "near".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_ADD_CONTROL, 0, speed, 0, 0);
        } else if ("zoom-".equals(direction) || "far".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_DEC_CONTROL, 0, speed, 0, 0);
        } else if ("aperture+".equals(direction) || "in".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_ADD_CONTROL, 0, speed, 0, 0);
        } else if ("aperture-".equals(direction) || "out".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_DEC_CONTROL, 0, speed, 0, 0);
        } else {
            log.warn("未知的云台控制方向, deviceId:{}, direction:{}", id, direction);
        }

        log.info("大华设备云台控制(开始)完成, deviceId:{}, direction:{}, result:{}", id, direction, result);
        return result;
    }

    /**
     * 大华设备云台控制（停止）
     *
     * @param direction 方向
     * @param id        设备id
     * @param channelId 通道id
     * @return
     */
    @Override
    public boolean ptzControlUpEnd(String direction, Long id, int channelId) {
        log.info("开始大华设备云台控制(停止), deviceId:{}, direction:{}, channelId:{}", id, direction, channelId);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        boolean result = false;
        if ("up".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_UP_CONTROL, 0, 0, 0, 1);
        } else if ("down".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_DOWN_CONTROL, 0, 0, 0, 1);
        } else if ("left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LEFT_CONTROL, 0, 0, 0, 1);
        } else if ("right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_RIGHT_CONTROL, 0, 0, 0, 1);
        } else if ("top-left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTTOP, 0, 0, 0, 1);
        } else if ("upper-right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTTOP, 0, 0, 0, 1);
        } else if ("bottom-left".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_LEFTDOWN, 0, 0, 0, 1);
        } else if ("lower-right".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_RIGHTDOWN, 0, 0, 0, 1);
        } else if ("doubling+".equals(direction) || "zoomin".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_ADD_CONTROL, 0, 0, 0, 1);
        } else if ("doubling-".equals(direction) || "zoomout".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_ZOOM_DEC_CONTROL, 0, 0, 0, 1);
        } else if ("zoom+".equals(direction) || "near".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_ADD_CONTROL, 0, 0, 0, 1);
        } else if ("zoom-".equals(direction) || "far".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_FOCUS_DEC_CONTROL, 0, 0, 0, 1);
        } else if ("aperture+".equals(direction) || "in".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_ADD_CONTROL, 0, 0, 0, 1);
        } else if ("aperture-".equals(direction) || "out".equals(direction)) {
            result = netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle, channelId, NetSDKLib.NET_PTZ_ControlType.NET_PTZ_APERTURE_DEC_CONTROL, 0, 0, 0, 1);
        } else {
            log.warn("未知的云台控制方向, deviceId:{}, direction:{}", id, direction);
        }

        log.info("大华设备云台控制(停止)完成, deviceId:{}, direction:{}, result:{}", id, direction, result);
        return result;
    }

    /**
     * 大华设备获取预置点列表
     *
     * @param id
     * @param channelId
     */
    @Override
    public ArrayList<HashMap<String, Object>> getPresetList(Long id, int channelId) {
        log.info("开始获取大华设备预置点列表, deviceId:{}, channelId:{}", id, channelId);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        NetSDKLib.NET_PTZ_PRESET_LIST ptzPresetList = new NetSDKLib.NET_PTZ_PRESET_LIST();
        ptzPresetList.dwSize = ptzPresetList.size();
        ptzPresetList.dwMaxPresetNum = 255;
        ptzPresetList.dwRetPresetNum = 10;
        Pointer presetMemory = new Memory(ptzPresetList.dwMaxPresetNum * new NetSDKLib.NET_PTZ_PRESET().size());
        ptzPresetList.pstuPtzPorsetList = presetMemory;
        ptzPresetList.write();
        IntByReference pRetLen = new IntByReference(0);
        boolean bResult = netsdk.CLIENT_QueryRemotDevState(m_hLoginHandle, NetSDKLib.NET_DEVSTATE_PTZ_PRESET_LIST, channelId,
                ptzPresetList.getPointer(), ptzPresetList.size(), pRetLen, 1000);
        if (!bResult) {
            log.error("获取预置点失败, deviceId:{}, error:{}", id, getErrorCodePrint());
        } else {
            ptzPresetList.read();
            int returnedPresetNum = ptzPresetList.dwRetPresetNum;
            log.debug("获取到预置点数量, deviceId:{}, count:{}", id, returnedPresetNum);
            Pointer presetListPointer = ptzPresetList.pstuPtzPorsetList;

            ArrayList<HashMap<String, Object>> presetList = new ArrayList<>();

            for (int i = 0; i < returnedPresetNum; i++) {
                NetSDKLib.NET_PTZ_PRESET preset = new NetSDKLib.NET_PTZ_PRESET();
                Pointer presetPointer = presetListPointer.share(i * preset.size());
                preset.nIndex = presetPointer.getInt(0);
                preset.szName = presetPointer.getByteArray(4, NetSDKLib.PTZ_PRESET_NAME_LEN);
                preset.szReserve = presetPointer.getByteArray(4 + NetSDKLib.PTZ_PRESET_NAME_LEN, 64);

                HashMap<String, Object> map = new HashMap<>();
                map.put("index", preset.nIndex);
                map.put("name", new String(preset.szName, Charset.forName("GBK")).trim());

                presetList.add(map);
            }

            log.info("获取大华设备预置点列表成功, deviceId:{}, count:{}", id, presetList.size());
            return presetList;
        }
        log.warn("获取大华设备预置点列表返回空列表, deviceId:{}", id);
        return new ArrayList<>();
    }

    /**
     * 大华设备设置预置点
     *
     * @param id
     * @param channelId
     * @param presetIndex
     */
    @Override
    public void setPreset(Long id, int channelId, int presetIndex) {
        log.info("开始设置大华设备预置点, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_POINT_SET_CONTROL,
                0,
                presetIndex,
                0,
                0);
        log.info("设置大华设备预置点完成, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
    }

    @Override
    public void delPreset(Long id, int channelId, int presetIndex) {
        log.info("开始删除大华设备预置点, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_POINT_DEL_CONTROL,
                0,
                presetIndex,
                0,
                0);
        log.info("删除大华设备预置点完成, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
    }

    @Override
    public void invokePreset(Long id, int channelId, int presetIndex) {
        log.info("开始调用大华设备预置点, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_POINT_MOVE_CONTROL,
                0,
                presetIndex,
                0,
                0);
        log.info("调用大华设备预置点完成, deviceId:{}, channelId:{}, presetIndex:{}", id, channelId, presetIndex);
    }

    /**
     * 获取接口错误码和错误信息，用于打印
     *
     * @return
     */
    public static String getErrorCodePrint() {
        return "error code: (0x80000000|" + (netsdk.CLIENT_GetLastError() & 0x7fffffff) + "), error info:" + ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError());
    }

    @Override
    public void controlLight(Long id, int channelId, int action) {
        log.info("开始控制大华设备灯光, deviceId:{}, channelId:{}, action:{}", id, channelId, action);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LAMP_CONTROL,
                0,
                action,
                0,
                0);
        log.info("控制大华设备灯光完成, deviceId:{}, channelId:{}, action:{}", id, channelId, action);
    }

    @Override
    public void controlWiper(Long id, int channelId, int action) {
        log.info("开始控制大华设备雨刷, deviceId:{}, channelId:{}, action:{}", id, channelId, action);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_LAMP_CONTROL,
                1,
                action,
                0,
                0);
        log.info("控制大华设备雨刷完成, deviceId:{}, channelId:{}, action:{}", id, channelId, action);
    }

    @Override
    public void startTour(Long id, int channelId, int tourIndex) {
        log.info("开始大华设备巡航, deviceId:{}, channelId:{}, tourIndex:{}", id, channelId, tourIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_POINT_LOOP_CONTROL,
                tourIndex,
                1,
                0,
                0);
        log.info("大华设备巡航开始完成, deviceId:{}, channelId:{}, tourIndex:{}", id, channelId, tourIndex);
    }

    @Override
    public void stopTour(Long id, int channelId) {
        log.info("开始停止大华设备巡航, deviceId:{}, channelId:{}", id, channelId);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_PTZ_ControlType.NET_PTZ_POINT_LOOP_CONTROL,
                0,
                0,
                0,
                0);
        log.info("停止大华设备巡航完成, deviceId:{}, channelId:{}", id, channelId);
    }

    @Override
    public void addPresetToTour(Long id, int channelId, int tourIndex, int presetIndex) {
        log.info("开始添加预置点到巡航线路, deviceId:{}, channelId:{}, tourIndex:{}, presetIndex:{}", id, channelId, tourIndex, presetIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_ADDTOLOOP,
                tourIndex,
                presetIndex,
                0,
                0);
        log.info("添加预置点到巡航线路完成, deviceId:{}, channelId:{}, tourIndex:{}, presetIndex:{}", id, channelId, tourIndex, presetIndex);
    }

    @Override
    public void removePresetFromTour(Long id, int channelId, int tourIndex, int presetIndex) {
        log.info("开始从巡航线路删除预置点, deviceId:{}, channelId:{}, tourIndex:{}, presetIndex:{}", id, channelId, tourIndex, presetIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_DELFROMLOOP,
                tourIndex,
                presetIndex,
                0,
                0);
        log.info("从巡航线路删除预置点完成, deviceId:{}, channelId:{}, tourIndex:{}, presetIndex:{}", id, channelId, tourIndex, presetIndex);
    }

    @Override
    public void clearTour(Long id, int channelId, int tourIndex) {
        log.info("开始清除巡航线路, deviceId:{}, channelId:{}, tourIndex:{}", id, channelId, tourIndex);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        netsdk.CLIENT_DHPTZControlEx(m_hLoginHandle,
                channelId,
                NetSDKLib.NET_EXTPTZ_ControlType.NET_EXTPTZ_CLOSELOOP,
                tourIndex,
                0,
                0,
                0);
        log.info("清除巡航线路完成, deviceId:{}, channelId:{}, tourIndex:{}", id, channelId, tourIndex);
    }

    @Override
    public boolean setTime(Long id, String date, boolean type) {
        log.info("开始设置大华设备时间, deviceId:{}, date:{}, type:{}", id, date, type);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        NetSDKLib.NET_TIME deviceTime = new NetSDKLib.NET_TIME();
        String originalDate = date;
        if (date == null || date.isEmpty()) {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = dateFormat.format(new java.util.Date());
            log.debug("使用当前时间作为设备时间, deviceId:{}, currentTime:{}", id, date);
        }

        try {
            String[] dateTime = date.split(" ");
            String[] arrDate = dateTime[0].split("-");
            String[] arrTime = dateTime[1].split(":");
            deviceTime.dwYear = Integer.parseInt(arrDate[0]);
            deviceTime.dwMonth = Integer.parseInt(arrDate[1]);
            deviceTime.dwDay = Integer.parseInt(arrDate[2]);
            deviceTime.dwHour = Integer.parseInt(arrTime[0]);
            deviceTime.dwMinute = Integer.parseInt(arrTime[1]);
            deviceTime.dwSecond = Integer.parseInt(arrTime[2]);
            log.debug("解析日期时间成功, deviceId:{}, year:{}, month:{}, day:{}, hour:{}, minute:{}, second:{}",
                    id, deviceTime.dwYear, deviceTime.dwMonth, deviceTime.dwDay,
                    deviceTime.dwHour, deviceTime.dwMinute, deviceTime.dwSecond);
        } catch (Exception e) {
            log.error("解析日期时间失败, deviceId:{}, date:{}, error:{}", id, date, e.getMessage(), e);
            throw new RuntimeException("解析日期时间失败: " + e.getMessage(), e);
        }

        boolean success = netsdk.CLIENT_SetupDeviceTime(m_hLoginHandle, deviceTime);
        if (success) {
            log.info("设置大华设备时间成功, deviceId:{}, IP:{}, originalDate:{}, finalDate:{}",
                    id, device.getIpAddress(), originalDate, date);
        } else {
            log.error("设置大华设备时间失败, deviceId:{}, IP:{}, date:{}, error:{}",
                    id, device.getIpAddress(), date, getErrorCodePrint());
        }
        return success;
    }

    @Override
    public boolean reboot(Long id) {
        log.info("开始重启大华设备, deviceId:{}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        boolean success = netsdk.CLIENT_ControlDevice(m_hLoginHandle, NetSDKLib.CtrlType.CTRLTYPE_CTRL_REBOOT, null, 3000);
        if (success) {
            log.info("重启大华设备成功, deviceId:{}, IP:{}", id, device.getIpAddress());
        } else {
            log.error("重启大华设备失败, deviceId:{}, IP:{}, error:{}",
                    id, device.getIpAddress(), getErrorCodePrint());
        }
        return success;
    }

    /**
     * 大华设备查询录像
     *
     * @param id        设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryRecord(Long id, int channelId, String startTime, String endTime) {
        log.info("开始查询大华设备录像, deviceId:{}, channelId:{}, startTime:{}, endTime:{}", id, channelId, startTime, endTime);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("大华设备登录句柄获取成功, deviceId:{}, IP:{}, handle:{}", id, device.getIpAddress(), m_hLoginHandle.longValue());

        // 开始时间
        NetSDKLib.NET_TIME stTimeStart = new NetSDKLib.NET_TIME();

        // 结束时间
        NetSDKLib.NET_TIME stTimeEnd = new NetSDKLib.NET_TIME();

        // 开始时间
        String[] dateStartByFile = startTime.split(" ");
        String[] dateStart1 = dateStartByFile[0].split("-");
        String[] dateStart2 = dateStartByFile[1].split(":");

        stTimeStart.dwYear = Integer.parseInt(dateStart1[0]);
        stTimeStart.dwMonth = Integer.parseInt(dateStart1[1]);
        stTimeStart.dwDay = Integer.parseInt(dateStart1[2]);

        stTimeStart.dwHour = Integer.parseInt(dateStart2[0]);
        stTimeStart.dwMinute = Integer.parseInt(dateStart2[1]);
        stTimeStart.dwSecond = Integer.parseInt(dateStart2[2]);


        // 结束时间
        String[] dateEndByFile = endTime.split(" ");
        String[] dateEnd1 = dateEndByFile[0].split("-");
        String[] dateEnd2 = dateEndByFile[1].split(":");

        stTimeEnd.dwYear = Integer.parseInt(dateEnd1[0]);
        stTimeEnd.dwMonth = Integer.parseInt(dateEnd1[1]);
        stTimeEnd.dwDay = Integer.parseInt(dateEnd1[2]);

        stTimeEnd.dwHour = Integer.parseInt(dateEnd2[0]);
        stTimeEnd.dwMinute = Integer.parseInt(dateEnd2[1]);
        stTimeEnd.dwSecond = Integer.parseInt(dateEnd2[2]);
        
        log.debug("时间参数解析成功, deviceId:{}, 开始时间:{}-{}-{} {}:{}:{}, 结束时间:{}-{}-{} {}:{}:{}",
                id, stTimeStart.dwYear, stTimeStart.dwMonth, stTimeStart.dwDay,
                stTimeStart.dwHour, stTimeStart.dwMinute, stTimeStart.dwSecond,
                stTimeEnd.dwYear, stTimeEnd.dwMonth, stTimeEnd.dwDay,
                stTimeEnd.dwHour, stTimeEnd.dwMinute, stTimeEnd.dwSecond);


        if (stTimeStart.dwYear != stTimeEnd.dwYear || stTimeStart.dwMonth != stTimeEnd.dwMonth || (stTimeEnd.dwDay - stTimeStart.dwDay > 1)) {
            log.error("时间间隔超过一天, deviceId:{}, 开始时间:{}-{}-{}, 结束时间:{}-{}-{}",
                    id, stTimeStart.dwYear, stTimeStart.dwMonth, stTimeStart.dwDay,
                    stTimeEnd.dwYear, stTimeEnd.dwMonth, stTimeEnd.dwDay);
            throw new ServiceException("时间间隔不能超过一天");
        }

//        int time = 0;
//        if (stTimeEnd.dwDay - stTimeStart.dwDay == 1) {
//            time = (24 + stTimeEnd.dwHour) * 60 * 60 + stTimeEnd.dwMinute * 60 + stTimeEnd.dwSecond - stTimeStart.dwHour * 60 * 60 - stTimeStart.dwMinute * 60 - stTimeStart.dwSecond;
//        } else {
//            time = stTimeEnd.dwHour * 60 * 60 + stTimeEnd.dwMinute * 60 + stTimeEnd.dwSecond - stTimeStart.dwHour * 60 * 60 - stTimeStart.dwMinute * 60 - stTimeStart.dwSecond;
//        }

//        if (time > 6 * 60 * 60 || time <= 0) {
//            throw new ServiceException("时间间隔不能超过6小时");
//        }

        ArrayList<HashMap<String, Object>> recordList = new ArrayList<HashMap<String, Object>>();

        // 录像文件信息
        NetSDKLib.NET_RECORDFILE_INFO[] stFileInfo = (NetSDKLib.NET_RECORDFILE_INFO[]) new NetSDKLib.NET_RECORDFILE_INFO().toArray(2000);

        IntByReference nFindCount = new IntByReference(0);

        if (!queryRecordFile(channelId, stTimeStart, stTimeEnd, stFileInfo, nFindCount, m_hLoginHandle)) {
            log.error("查询录像失败, deviceId:{}, channelId:{}", id, channelId);
            throw new RuntimeException("查询不到录像");
        } else {
            int totalCount = nFindCount.getValue();

            if (nFindCount.getValue() == 0) {
                log.warn("未查询到录像文件, deviceId:{}, channelId:{}", id, channelId);
                throw new RuntimeException("查询不到录像");
            }

            log.debug("查询到录像文件, deviceId:{}, channelId:{}, 数量:{}", id, channelId, totalCount);

            // 🔥 核心：遍历 stFileInfo，存入 list
            for (int j = 0; j < totalCount; j++) {
                String ch = String.valueOf(stFileInfo[j].ch + 1);
                String type = Res.string().getRecordTypeStr(stFileInfo[j].nRecordFileType);
                String start = stFileInfo[j].starttime.toStringTime();
                String end = stFileInfo[j].endtime.toStringTime();
                HashMap<String, Object> record = new HashMap<>(16);
                record.put("channel", ch);
                record.put("type", type);
                record.put("start", start);
                record.put("end", end);
                recordList.add(record);
            }
        }
        
        log.info("查询大华设备录像完成, deviceId:{}, channelId:{}, 共查询到 {} 条录像记录", id, channelId, recordList.size());
        return recordList;
    }

    public boolean queryRecordFile(int nChannelId, NetSDKLib.NET_TIME stTimeStart, NetSDKLib.NET_TIME stTimeEnd, NetSDKLib.NET_RECORDFILE_INFO[] stFileInfo, IntByReference nFindCount, NetSDKLib.LLong m_hLoginHandle) {
        // RecordFileType 录像类型 0:所有录像  1:外部报警  2:动态监测报警  3:所有报警  4:卡号查询   5:组合条件查询
        // 6:录像位置与偏移量长度   8:按卡号查询图片(目前仅HB-U和NVS特殊型号的设备支持)  9:查询图片(目前仅HB-U和NVS特殊型号的设备支持)
        // 10:按字段查询    15:返回网络数据结构(金桥网吧)  16:查询所有透明串数据录像文件
        int nRecordFileType = 0;
        boolean bRet = netsdk.CLIENT_QueryRecordFile(m_hLoginHandle, nChannelId, nRecordFileType, stTimeStart, stTimeEnd, null, stFileInfo, stFileInfo.length * stFileInfo[0].size(), nFindCount, 5000, false);

        if (bRet) {
            System.out.println("QueryRecordFile  Succeed! \n" + "查询到的视频个数：" + nFindCount.getValue());
        } else {
            System.err.println("QueryRecordFile  Failed!");
            return false;
        }
        return true;
    }

    /**
     * 大华设备开始录像回放
     *
     * @param rtpServerParam 回放参数
     */
    @Override
    public void startPlayback(RtpServerParam rtpServerParam) {
        log.info("开始回放大华设备录像, deviceId:{}", rtpServerParam.getId());
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", rtpServerParam.getId(), r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}, channel:{}", device.getId(), device.getIpAddress(), device.getChannel());

        String playbackKey = "dahua:playback:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", device.getId(), device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }
        log.info("开始回放大华设备录像, deviceId:{}, channel:{}, playbackKey:{}", device.getId(), device.getChannel(), playbackKey);
        mediaStreamService.startPlayback(lLong, device, playbackKey, rtpServerParam);
        log.info("回放大华设备录像调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 大华设备停止录像回放
     *
     * @param id 设备id
     */
    @Override
    public void stopPlayback(Long id) {
        log.info("开始停止回放大华设备录像, deviceId:{}", id);
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}, channel:{}", device.getId(), device.getIpAddress(), device.getChannel());
        String playbackKey = "dahua:playback:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            log.warn("大华设备未登录，无法停止回放, deviceId:{}, IP:{}", id, device.getIpAddress());
            return;
        }
        log.info("停止回放大华设备录像, deviceId:{}, channel:{}, playbackKey:{}", device.getId(), device.getChannel(), playbackKey);
        mediaStreamService.stopPlayback(lLong, device.getId(), device.getChannel(), playbackKey);
        log.info("停止回放大华设备录像调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 获取大华设备详细信息
     *
     * @param id 设备ID
     * @return 设备详细信息
     */
    @Override
    public DahuaDeviceInfo getDeviceInfo(Long id) {
        log.info("开始获取大华设备详细信息, deviceId:{}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        return getDeviceInfoByIp(device.getIpAddress());
    }

    /**
     * 获取大华设备详细信息(通过IP)
     *
     * @param ip 设备IP
     * @return 设备详细信息
     */
    @Override
    public DahuaDeviceInfo getDeviceInfoByIp(String ip) {
        log.info("开始获取大华设备详细信息, IP:{}", ip);

        NetSDKLib.NET_DEVICEINFO_Ex deviceInfo = deviceInfoMap.get("device:info:" + ip);
        if (deviceInfo == null) {
            log.error("设备信息不存在, IP:{}", ip);
            throw new RuntimeException("设备信息不存在，IP:" + ip);
        }

        DahuaDeviceInfo info = new DahuaDeviceInfo();
        info.setSerialNumber(new String(deviceInfo.sSerialNumber, Charset.forName("GBK")).trim());
        info.setAlarmInPortNum(deviceInfo.byAlarmInPortNum);
        info.setAlarmOutPortNum(deviceInfo.byAlarmOutPortNum);
        info.setDiskNum(deviceInfo.byDiskNum);
        info.setDvrType(deviceInfo.byDVRType);
        info.setChannelNum(deviceInfo.byChanNum);
        info.setLimitLoginTime((int) deviceInfo.byLimitLoginTime);
        info.setLeftLogTimes((int) deviceInfo.byLeftLogTimes);
        info.setLockLeftTime(deviceInfo.byLockLeftTime);

        log.info("获取大华设备详细信息成功, IP:{}, 序列号:{}, 通道数:{}",
                ip, info.getSerialNumber(), info.getChannelNum());
        return info;
    }

    @Override
    public DahuaSystemParam getSystemParam(Long id) {
        log.info("开始获取大华设备系统参数, deviceId:{}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        DahuaSystemParam param = new DahuaSystemParam();
        try {
            com.ruoyi.dahua.lib.structure.NET_CFG_VIDEOSTANDARD_INFO videoStandardInfo =
                    new com.ruoyi.dahua.lib.structure.NET_CFG_VIDEOSTANDARD_INFO();
            videoStandardInfo.write();
            boolean success = netsdk.CLIENT_GetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_VIDEOSTANDARD,
                    -1, videoStandardInfo.getPointer(), videoStandardInfo.size(), 5000, null);
            if (success) {
                videoStandardInfo.read();
                param.setVideoStandard(videoStandardInfo.emVideoStandard);
                param.setCountry(new String(videoStandardInfo.szCountry).trim());
            } else {
                log.warn("获取视频制式失败, deviceId:{}, error:{}", id, getErrorCodePrint());
            }
        } catch (Exception e) {
            log.error("获取系统参数异常, deviceId:{}, error:{}", id, e.getMessage(), e);
        }

        log.info("获取大华设备系统参数成功, deviceId:{}, videoStandard:{}", id, param.getVideoStandard());
        return param;
    }

    @Override
    public DahuaVideoParam getVideoParam(Long id, int channelId, int streamType) {
        log.info("开始获取大华设备视频参数, deviceId:{}, channelId:{}, streamType:{}", id, channelId, streamType);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        DahuaVideoParam param = new DahuaVideoParam();
        try {
            com.ruoyi.dahua.lib.structure.NET_ENCODE_VIDEO_INFO videoInfo =
                    new com.ruoyi.dahua.lib.structure.NET_ENCODE_VIDEO_INFO();
            // 映射streamType到NET_EM_FORMAT_TYPE枚举值
            int emFormatType = 1; // 默认主码流
            if (streamType == 1) {
                emFormatType = 4; // 辅码流1
            } else if (streamType == 2) {
                emFormatType = 5; // 辅码流2
            }
            videoInfo.dwSize = videoInfo.size();
            videoInfo.emFormatType = emFormatType;
            videoInfo.write();
            boolean success = netsdk.CLIENT_GetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_ENCODE_VIDEO,
                    channelId, videoInfo.getPointer(), videoInfo.size(), 5000, null);
            if (success) {
                videoInfo.read();
                param.setFormatType(videoInfo.emFormatType);
                param.setVideoEnable(videoInfo.bVideoEnable);
                param.setCompression(videoInfo.emCompression);
                param.setWidth(videoInfo.nWidth);
                param.setHeight(videoInfo.nHeight);
                param.setBitRateControl(videoInfo.emBitRateControl);
                param.setBitRate(videoInfo.nBitRate);
                param.setFrameRate(videoInfo.nFrameRate);
                param.setIframeInterval(videoInfo.nIFrameInterval);
                param.setImageQuality(videoInfo.emImageQuality);
                log.info("视频参数详细信息 - width:{}, height:{}, frameRate:{}, bitRate:{}, compression:{}", 
                        videoInfo.nWidth, videoInfo.nHeight, videoInfo.nFrameRate, 
                        videoInfo.nBitRate, videoInfo.emCompression);
            } else {
                log.warn("获取视频参数失败, deviceId:{}, channelId:{}, error:{}", id, channelId, getErrorCodePrint());
            }
        } catch (Exception e) {
            log.error("获取视频参数异常, deviceId:{}, channelId:{}, error:{}", id, channelId, e.getMessage(), e);
        }

        log.info("获取大华设备视频参数成功, deviceId:{}, channelId:{}, streamType:{}, width:{}, height:{}",
                id, channelId, streamType, param.getWidth(), param.getHeight());
        return param;
    }

    @Override
    public DahuaDeviceVideoParam getDeviceVideoParam(Long id, int channelId) {
        log.info("开始获取大华设备视频输入参数, deviceId:{}, channelId:{}", id, channelId);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        DahuaDeviceVideoParam param = new DahuaDeviceVideoParam();
        try {
            com.ruoyi.dahua.lib.structure.NET_VIDEOIN_IMAGE_INFO imageInfo =
                    new com.ruoyi.dahua.lib.structure.NET_VIDEOIN_IMAGE_INFO();
            imageInfo.dwSize = imageInfo.size();
            imageInfo.write();
            boolean success = netsdk.CLIENT_GetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_VIDEOIN_IMAGE_OPT,
                    channelId, imageInfo.getPointer(), imageInfo.size(), 5000, null);
            if (success) {
                imageInfo.read();
                param.setBrightness(imageInfo.nBrightness);
                param.setContrast(imageInfo.nContrast);
                param.setSaturation(imageInfo.nSaturation);
                param.setChroma(imageInfo.nChroma);
                param.setSharpness(imageInfo.nSharpness);
                param.setHue(imageInfo.nHue);
                param.setGain(imageInfo.nGain);
                param.setBlackWhiteMode(imageInfo.nBlackWhiteMode);
                log.info("视频输入参数详细信息 - brightness:{}, contrast:{}, saturation:{}, sharpness:{}", 
                        imageInfo.nBrightness, imageInfo.nContrast, 
                        imageInfo.nSaturation, imageInfo.nSharpness);
            } else {
                log.warn("获取视频输入参数失败, deviceId:{}, channelId:{}, error:{}", id, channelId, getErrorCodePrint());
            }
        } catch (Exception e) {
            log.error("获取视频输入参数异常, deviceId:{}, channelId:{}, error:{}", id, channelId, e.getMessage(), e);
        }

        log.info("获取大华设备视频输入参数成功, deviceId:{}, channelId:{}, brightness:{}, contrast:{}",
                id, channelId, param.getBrightness(), param.getContrast());
        return param;
    }

    @Override
    public boolean setVideoParam(Long id, int channelId, int streamType, DahuaVideoParam param) {
        log.info("开始设置大华设备视频参数, deviceId:{}, channelId:{}", id, channelId);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        boolean success = false;
        try {
            com.ruoyi.dahua.lib.structure.NET_ENCODE_VIDEO_INFO videoInfo =
                    new com.ruoyi.dahua.lib.structure.NET_ENCODE_VIDEO_INFO();
            videoInfo.dwSize = videoInfo.size();
            // 先获取当前配置，然后再修改
            int emFormatType = 1; // 默认主码流
            if (param.getFormatType() != null) {
                emFormatType = param.getFormatType();
            } else if (streamType == 1) {
                emFormatType = 4; // 辅码流1
            } else if (streamType == 2) {
                emFormatType = 5; // 辅码流2
            }
            videoInfo.emFormatType = emFormatType;
            videoInfo.write();
            
            // 先获取当前配置
            boolean getSuccess = netsdk.CLIENT_GetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_ENCODE_VIDEO,
                    channelId, videoInfo.getPointer(), videoInfo.size(), 5000, null);
            if (getSuccess) {
                videoInfo.read();
            }
            
            // 然后修改配置
            if (param.getVideoEnable() != null) {
                videoInfo.bVideoEnable = param.getVideoEnable();
            }
            if (param.getCompression() != null) {
                videoInfo.emCompression = param.getCompression();
            }
            if (param.getWidth() != null) {
                videoInfo.nWidth = param.getWidth();
            }
            if (param.getHeight() != null) {
                videoInfo.nHeight = param.getHeight();
            }
            if (param.getBitRateControl() != null) {
                videoInfo.emBitRateControl = param.getBitRateControl();
            }
            if (param.getBitRate() != null) {
                videoInfo.nBitRate = param.getBitRate();
            }
            if (param.getFrameRate() != null) {
                videoInfo.nFrameRate = param.getFrameRate();
            }
            if (param.getIframeInterval() != null) {
                videoInfo.nIFrameInterval = param.getIframeInterval();
            }
            if (param.getImageQuality() != null) {
                videoInfo.emImageQuality = param.getImageQuality();
            }
            videoInfo.write();

            IntByReference errorCode = new IntByReference(0);
            success = netsdk.CLIENT_SetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_ENCODE_VIDEO,
                    channelId, videoInfo.getPointer(), videoInfo.size(), 5000, errorCode, null);
            if (success) {
                log.info("设置大华设备视频参数成功, deviceId:{}, channelId:{}", id, channelId);
            } else {
                log.error("设置大华设备视频参数失败, deviceId:{}, channelId:{}, error:{}, errorCode:{}",
                        id, channelId, getErrorCodePrint(), errorCode.getValue());
            }
        } catch (Exception e) {
            log.error("设置视频参数异常, deviceId:{}, channelId:{}, error:{}", id, channelId, e.getMessage(), e);
        }

        return success;
    }

    @Override
    public boolean setDeviceVideoParam(Long id, int channelId, DahuaDeviceVideoParam param) {
        log.info("开始设置大华设备视频输入参数, deviceId:{}, channelId:{}", id, channelId);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        boolean success = false;
        try {
            com.ruoyi.dahua.lib.structure.NET_VIDEOIN_IMAGE_INFO imageInfo =
                    new com.ruoyi.dahua.lib.structure.NET_VIDEOIN_IMAGE_INFO();
            imageInfo.dwSize = imageInfo.size();
            // 先获取当前配置，然后再修改
            boolean getSuccess = netsdk.CLIENT_GetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_VIDEOIN_IMAGE_OPT,
                    channelId, imageInfo.getPointer(), imageInfo.size(), 5000, null);
            if (getSuccess) {
                imageInfo.read();
            }
            
            // 然后修改配置
            if (param.getBrightness() != null) {
                imageInfo.nBrightness = param.getBrightness();
            }
            if (param.getContrast() != null) {
                imageInfo.nContrast = param.getContrast();
            }
            if (param.getSaturation() != null) {
                imageInfo.nSaturation = param.getSaturation();
            }
            if (param.getChroma() != null) {
                imageInfo.nChroma = param.getChroma();
            }
            if (param.getSharpness() != null) {
                imageInfo.nSharpness = param.getSharpness();
            }
            if (param.getHue() != null) {
                imageInfo.nHue = param.getHue();
            }
            if (param.getGain() != null) {
                imageInfo.nGain = param.getGain();
            }
            if (param.getBlackWhiteMode() != null) {
                imageInfo.nBlackWhiteMode = param.getBlackWhiteMode();
            }
            imageInfo.write();

            IntByReference errorCode = new IntByReference(0);
            success = netsdk.CLIENT_SetConfig(m_hLoginHandle,
                    com.ruoyi.dahua.lib.enumeration.NET_EM_CFG_OPERATE_TYPE.NET_EM_CFG_VIDEOIN_IMAGE_OPT,
                    channelId, imageInfo.getPointer(), imageInfo.size(), 5000, errorCode, null);
            if (success) {
                log.info("设置大华设备视频输入参数成功, deviceId:{}, channelId:{}", id, channelId);
            } else {
                log.error("设置大华设备视频输入参数失败, deviceId:{}, channelId:{}, error:{}, errorCode:{}",
                        id, channelId, getErrorCodePrint(), errorCode.getValue());
            }
        } catch (Exception e) {
            log.error("设置视频输入参数异常, deviceId:{}, channelId:{}, error:{}", id, channelId, e.getMessage(), e);
        }

        return success;
    }


    // 回调建议写成单例模式, 回调里处理数据，需要另开线程
    // 回放进度回调
    public static class PlayBackPosCallBack implements NetSDKLib.fDownLoadPosCallBack {
        private PlayBackPosCallBack() {
        }

        private static class PlayBackPosCallBackHolder {
            private static final PlayBackPosCallBack posCB = new PlayBackPosCallBack();
        }

        public static final PlayBackPosCallBack getInstance() {
            return PlayBackPosCallBackHolder.posCB;
        }

        @Override
        public void invoke(NetSDKLib.LLong lPlayHandle, int dwTotalSize, int dwDownLoadSize, Pointer dwUser) {
//            System.out.println("PlayBackPosCallBack dwTotalSize： " + dwTotalSize + "dwDownLoadSize+ " + dwDownLoadSize);
        }
    }

    // 抓图上下文
    private static class CaptureContext {
        Long deviceId;
        String deviceCode;
        String deviceName;
        int channelId;
        String snapshotType;
        CountDownLatch latch;
        Long snapshotId;
        String errorMsg;
        Long alarmId; // 报警ID，用于更新报警记录

        CaptureContext(Long deviceId, String deviceCode, String deviceName, int channelId, String snapshotType, Long alarmId) {
            this.deviceId = deviceId;
            this.deviceCode = deviceCode;
            this.deviceName = deviceName;
            this.channelId = channelId;
            this.snapshotType = snapshotType;
            this.alarmId = alarmId;
            this.latch = new CountDownLatch(1);
        }
    }

    // 抓图回调
    private static class SnapReceiveCallback implements NetSDKLib.fSnapRev {
        @Override
        public void invoke(NetSDKLib.LLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, int CmdSerial, Pointer dwUser) {
            log.info("收到抓图回调, CmdSerial:{}, RevLen:{}", CmdSerial, RevLen);

            // 获取DaHuaServiceImpl实例
            DaHuaServiceImpl service = null;
            try {
                // 通过Spring上下文获取bean
                service = com.ruoyi.common.core.utils.SpringUtils.getBean(DaHuaServiceImpl.class);
            } catch (Exception e) {
                log.error("获取DaHuaServiceImpl失败", e);
                return;
            }

            if (service == null) {
                log.error("DaHuaServiceImpl实例为空");
                return;
            }

            CaptureContext context = service.captureContextMap.remove(CmdSerial);
            if (context == null) {
                log.warn("未找到抓图上下文, CmdSerial:{}", CmdSerial);
                return;
            }

            try {
                if (pBuf != null && RevLen > 0) {
                    // 保存图片
                    String fileName = service.generateFileName(context.deviceId, context.channelId);
                    String localFilePath = service.filePath + "/dahua_snapshot/" + fileName;
                    String fileUrl = service.fileDomain + service.filePrefix + "/dahua_snapshot/" + fileName;

                    // 创建目录
                    File dir = new File(service.filePath + "/dahua_snapshot/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // 保存图片文件
                    byte[] imageData = pBuf.getByteArray(0, RevLen);
                    try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                        fos.write(imageData);
                    }

                    log.info("图片保存成功, deviceId:{}, channelId:{}, filePath:{}, fileUrl:{}",
                            context.deviceId, context.channelId, localFilePath, fileUrl);

                    // 判断是否是报警抓图
                    if (context.alarmId != null) {
                        // 报警抓图：将图片信息存入Redis，由定时任务负责匹配
                        try {
                            // 创建一个包含图片信息的Map
                            java.util.HashMap<String, Object> picInfo = new java.util.HashMap<>();
                            picInfo.put("fileUrl", fileUrl);
                            picInfo.put("deviceId", context.deviceCode);
                            picInfo.put("deviceIdLong", context.deviceId);
                            picInfo.put("createTime", new Date().getTime());

                            // 存入全局图片队列
                            String picQueueKey = "dahua_alarm_pic_queue:global";
                            service.redisTemplate.opsForList().leftPush(picQueueKey, picInfo);
                            service.redisTemplate.opsForList().trim(picQueueKey, 0, 99);
                            service.redisTemplate.expire(picQueueKey, 30, java.util.concurrent.TimeUnit.MINUTES);
                            log.info("图片信息已存入全局队列, key:{}, fileUrl:{}", picQueueKey, fileUrl);

                            // 如果有设备Code，也存入设备特定队列
                            if (context.deviceCode != null && !context.deviceCode.isEmpty()) {
                                String devicePicKey = "dahua_alarm_pic_queue:" + context.deviceCode;
                                service.redisTemplate.opsForList().leftPush(devicePicKey, picInfo);
                                service.redisTemplate.opsForList().trim(devicePicKey, 0, 49);
                                service.redisTemplate.expire(devicePicKey, 30, java.util.concurrent.TimeUnit.MINUTES);
                                log.info("图片信息已存入设备队列, key:{}, fileUrl:{}", devicePicKey, fileUrl);
                            }

                            context.snapshotId = 0L;
                        } catch (Exception e) {
                            log.error("保存图片信息到Redis失败, alarmId:{}", context.alarmId, e);
                            context.errorMsg = "保存图片信息到Redis失败: " + e.getMessage();
                        }
                    } else {
                        // 普通抓图：保存到抓图数据库
                        QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
                        snapshot.setDeviceId(context.deviceId);
                        snapshot.setDeviceCode(context.deviceCode);
                        snapshot.setDeviceName(context.deviceName);
                        snapshot.setFileUrl(fileUrl);
                        snapshot.setFilePath(localFilePath);
                        snapshot.setFileSize((long) RevLen);
                        snapshot.setFileName(fileName);
                        snapshot.setFileType("jpg");
                        snapshot.setSnapshotType(context.snapshotType);
                        snapshot.setSdkType("dahua");
                        snapshot.setChannel(context.channelId);
                        snapshot.setCaptureTime(new Date());

                        com.ruoyi.common.core.domain.R<Long> result = service.remoteQsDeviceSnapshotService.add(
                                snapshot, com.ruoyi.common.core.constant.SecurityConstants.INNER);

                        if (com.ruoyi.common.core.domain.R.isSuccess(result)) {
                            context.snapshotId = result.getData();
                            log.info("抓图记录保存成功, snapshotId:{}", context.snapshotId);
                        } else {
                            context.errorMsg = "保存抓图记录失败: " + result.getMsg();
                            log.error(context.errorMsg);
                        }
                    }
                } else {
                    context.errorMsg = "抓图数据为空";
                    log.error(context.errorMsg);
                }
            } catch (Exception e) {
                context.errorMsg = "抓图处理异常: " + e.getMessage();
                log.error(context.errorMsg, e);
            } finally {
                context.latch.countDown();
            }
        }
    }

    // 报警专用抓图方法
    public Long captureAndSaveForAlarm(AlarmCaptureTask task) throws InterruptedException {
        log.info("开始报警抓图, deviceId:{}, alarmId:{}, channelId:{}", 
                task.deviceId, task.alarmId, task.channelId);

        String ipAddress = task.ipAddress;
        
        // 如果IP为空，尝试从deviceId获取设备信息
        if (ipAddress == null || ipAddress.isEmpty()) {
            try {
                R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(task.deviceId, SecurityConstants.INNER);
                if (R.isSuccess(r) && r.getData() != null) {
                    ipAddress = r.getData().getIpAddress();
                    log.info("从deviceId获取设备IP成功, deviceId:{}, ip:{}", task.deviceId, ipAddress);
                }
            } catch (Exception e) {
                log.error("获取设备信息失败, deviceId:{}", task.deviceId, e);
            }
        }
        
        if (ipAddress == null || ipAddress.isEmpty()) {
            log.error("无法获取设备IP, deviceId:{}", task.deviceId);
            throw new RuntimeException("无法获取设备IP");
        }

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + ipAddress);
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, ip:{}", task.deviceId, ipAddress);
            throw new RuntimeException("大华设备未登录");
        }

        // 创建抓图上下文，传入alarmId
        int cmdSerial = captureCmdSerial++;
        CaptureContext context = new CaptureContext(
                task.deviceId,
                task.deviceCode,
                task.deviceName,
                task.channelId,
                "alarm", // 抓图类型为报警
                task.alarmId
        );
        captureContextMap.put(cmdSerial, context);

        try {
            // 设置抓图参数
            NetSDKLib.SNAP_PARAMS snapParams = new NetSDKLib.SNAP_PARAMS();
            snapParams.Channel = task.channelId;
            snapParams.mode = 0; // 0: 远程抓图
            snapParams.Quality = 3; // 图片质量 1-6, 3为中等（降低要求更容易成功）
            snapParams.InterSnap = 0;
            snapParams.CmdSerial = cmdSerial;

            IntByReference reserved = new IntByReference(0);

            log.info("发送报警抓图命令, deviceId:{}, channelId:{}, cmdSerial:{}", 
                    task.deviceId, task.channelId, cmdSerial);
            boolean success = netsdk.CLIENT_SnapPictureEx(m_hLoginHandle, snapParams, reserved);

            if (!success) {
                String errorMsg = "发送报警抓图命令失败: " + getErrorCodePrint();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

            // 等待抓图完成（最多30秒）
            boolean completed = context.latch.await(30, java.util.concurrent.TimeUnit.SECONDS);
            if (!completed) {
                log.warn("报警抓图超时, deviceId:{}, channelId:{}", task.deviceId, task.channelId);
                throw new RuntimeException("抓图超时");
            }

            if (context.errorMsg != null) {
                throw new RuntimeException(context.errorMsg);
            }

            return context.snapshotId;
        } catch (Exception e) {
            log.error("报警抓图异常, deviceId:{}, alarmId:{}", task.deviceId, task.alarmId, e);
            throw e;
        } finally {
            captureContextMap.remove(cmdSerial);
        }
    }

    // 生成文件名
    private String generateFileName(Long deviceId, int channelId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return deviceId + "_" + channelId + "_" + sdf.format(new Date()) + ".jpg";
    }

    /**
     * 大华设备抓图并保存
     *
     * @param id           设备id
     * @param channelId    通道id
     * @param snapshotType 抓图类型
     * @return 抓图记录id
     */
    @Override
    public Long captureAndSave(Long id, int channelId, String snapshotType) {
        log.info("开始大华设备抓图, deviceId:{}, channelId:{}, snapshotType:{}", id, channelId, snapshotType);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        // 创建抓图上下文
        int cmdSerial = captureCmdSerial++;
        CaptureContext context = new CaptureContext(
                device.getId(),
                device.getDeviceCode(),
                device.getDeviceName(),
                channelId,
                snapshotType,
                null // 不是报警抓图，alarmId为null
        );
        captureContextMap.put(cmdSerial, context);

        try {
            // 设置抓图参数
            NetSDKLib.SNAP_PARAMS snapParams = new NetSDKLib.SNAP_PARAMS();
            snapParams.Channel = channelId;
            snapParams.mode = 0; // 0: 远程抓图
            snapParams.Quality = 1; // 图片质量 1-6, 3为中等
            snapParams.InterSnap = 0;
            snapParams.CmdSerial = cmdSerial;

            IntByReference reserved = new IntByReference(0);

            log.info("发送抓图命令, deviceId:{}, channelId:{}, cmdSerial:{}", id, channelId, cmdSerial);
            boolean success = netsdk.CLIENT_SnapPictureEx(m_hLoginHandle, snapParams, reserved);

            if (!success) {
                String errorMsg = "发送抓图命令失败: " + getErrorCodePrint();
                log.error(errorMsg);
                captureContextMap.remove(cmdSerial);
                throw new RuntimeException(errorMsg);
            }

            // 等待抓图回调
            log.info("等待抓图回调, deviceId:{}, channelId:{}, cmdSerial:{}", id, channelId, cmdSerial);
            boolean awaitSuccess = context.latch.await(30, TimeUnit.SECONDS);

            if (!awaitSuccess) {
                String errorMsg = "等待抓图回调超时";
                log.error(errorMsg);
                captureContextMap.remove(cmdSerial);
                throw new RuntimeException(errorMsg);
            }

            if (context.errorMsg != null) {
                log.error(context.errorMsg);
                throw new RuntimeException(context.errorMsg);
            }

            log.info("大华设备抓图完成, deviceId:{}, channelId:{}, snapshotId:{}", id, channelId, context.snapshotId);
            return context.snapshotId;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            String errorMsg = "等待抓图回调被中断";
            log.error(errorMsg, e);
            captureContextMap.remove(cmdSerial);
            throw new RuntimeException(errorMsg, e);
        } catch (Exception e) {
            log.error("大华设备抓图异常", e);
            captureContextMap.remove(cmdSerial);
            throw e;
        }
    }

    @Override
    public DahuaStorageInfo getStorageInfo(Long id) {
        log.info("开始获取大华设备存储信息, deviceId:{}", id);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }

        DahuaStorageInfo result = new DahuaStorageInfo();
        List<DahuaStorageInfo.StorageDevice> deviceList = new ArrayList<>();

        try {
            NET_IN_STORAGE_DEV_INFOS inParam = new NET_IN_STORAGE_DEV_INFOS();
            inParam.dwSize = inParam.size();
            inParam.write();

            NET_OUT_STORAGE_DEV_INFOS outParam = new NET_OUT_STORAGE_DEV_INFOS();
            outParam.dwSize = outParam.size();
            outParam.write();

            boolean success = netsdk.CLIENT_QueryDevInfo(m_hLoginHandle, NetSDKLib.NET_QUERY_DEV_STORAGE_INFOS,
                    inParam.getPointer(), outParam.getPointer(), null, 5000);

            if (success) {
                outParam.read();
                log.info("获取到{}个存储设备", outParam.nDevInfosNum);

                for (int i = 0; i < outParam.nDevInfosNum; i++) {
                    NET_STORAGE_DEVICE storage = outParam.stuStoregeDevInfos[i];
                    DahuaStorageInfo.StorageDevice storageDevice = convertStorageDevice(storage);
                    if (storageDevice != null) {
                        deviceList.add(storageDevice);
                    }
                }
            } else {
                log.warn("获取存储信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
            }
        } catch (Exception e) {
            log.error("获取存储信息异常, deviceId:{}, error:{}", id, e.getMessage(), e);
        }

        result.setStorageDevices(deviceList);
        log.info("获取大华设备存储信息完成, deviceId:{}, 存储设备数:{}", id, deviceList.size());
        return result;
    }

    private DahuaStorageInfo.StorageDevice convertStorageDevice(NET_STORAGE_DEVICE storage) {
        DahuaStorageInfo.StorageDevice device = new DahuaStorageInfo.StorageDevice();

        // 基础信息
        device.setName(new String(storage.szName).trim());
        device.setTotalSpace(storage.nTotalSpace);
        device.setFreeSpace(storage.nFreeSpace);
        
        // 计算空间
        long usedSpace = storage.nTotalSpace - storage.nFreeSpace;
        device.setUsedSpace(usedSpace);
        
        // 转换为GB
        double gbUnit = 1024.0 * 1024.0 * 1024.0;
        device.setTotalSpaceGB(storage.nTotalSpace / gbUnit);
        device.setFreeSpaceGB(storage.nFreeSpace / gbUnit);
        device.setUsedSpaceGB(usedSpace / gbUnit);
        
        // 计算使用率
        if (storage.nTotalSpace > 0) {
            double usage = (double) usedSpace / storage.nTotalSpace * 100;
            device.setUsagePercent(Math.round(usage * 100.0) / 100.0);
        } else {
            device.setUsagePercent(0.0);
        }

        // 介质类型
        device.setMediaType((int) storage.byMedia);
        device.setMediaTypeDesc(getMediaTypeDesc(storage.byMedia));

        // 总线类型
        device.setBusType((int) storage.byBUS);
        device.setBusTypeDesc(getBusTypeDesc(storage.byBUS));

        // 卷类型
        device.setVolumeType((int) storage.byVolume);
        device.setVolumeTypeDesc(getVolumeTypeDesc(storage.byVolume));

        // 状态
        device.setState((int) storage.byState);
        device.setStateDesc(getStateDesc(storage.byState));

        // 其他信息
        device.setPhysicNo(storage.nPhysicNo);
        device.setLogicNo(storage.nLogicNo);
        device.setParent(new String(storage.szParent).trim());
        device.setModule(new String(storage.szModule).trim());
        device.setSerial(new String(storage.szSerial).trim());
        device.setFirmware(new String(storage.szFirmware).trim());
        device.setPartitionNum(storage.nPartitionNum);
        device.setOpState(storage.nOpState);
        device.setOpStateDesc(getOpStateDesc(storage.nOpState));

        // 转换分区信息
        List<DahuaStorageInfo.StoragePartition> partitions = new ArrayList<>();
        for (int i = 0; i < storage.nPartitionNum; i++) {
            if (i < storage.stuPartitions.length) {
                NET_STORAGE_PARTITION partition = storage.stuPartitions[i];
                DahuaStorageInfo.StoragePartition p = convertPartition(partition);
                if (p != null) {
                    partitions.add(p);
                }
            }
        }
        device.setPartitions(partitions);

        log.debug("存储设备: name={}, total={}GB, free={}GB, media={}", 
                device.getName(), device.getTotalSpaceGB(), device.getFreeSpaceGB(), device.getMediaTypeDesc());

        return device;
    }

    private DahuaStorageInfo.StoragePartition convertPartition(NET_STORAGE_PARTITION partition) {
        if (partition == null) {
            return null;
        }

        DahuaStorageInfo.StoragePartition p = new DahuaStorageInfo.StoragePartition();
        p.setName(new String(partition.szName).trim());
        p.setPath(new String(partition.szMountOn).trim());
        p.setTotalSpace(partition.nTotalSpace);
        p.setFreeSpace(partition.nFreeSpace);
        
        double gbUnit = 1024.0 * 1024.0 * 1024.0;
        p.setTotalSpaceGB(partition.nTotalSpace / gbUnit);
        p.setFreeSpaceGB(partition.nFreeSpace / gbUnit);
        p.setState(partition.nStatus);

        return p;
    }

    private String getMediaTypeDesc(byte type) {
        switch (type) {
            case 0: return "硬盘";
            case 1: return "CDROM";
            case 2: return "FLASH/SD卡";
            default: return "未知(" + type + ")";
        }
    }

    private String getBusTypeDesc(byte type) {
        switch (type) {
            case 0: return "ATA";
            case 1: return "SATA";
            case 2: return "USB";
            case 3: return "SDIO";
            case 4: return "SCSI";
            default: return "未知(" + type + ")";
        }
    }

    private String getVolumeTypeDesc(byte type) {
        switch (type) {
            case 0: return "物理卷";
            case 1: return "Raid卷";
            case 2: return "VG虚拟卷";
            case 3: return "ISCSI";
            case 4: return "独立物理卷";
            case 5: return "全局热备卷";
            case 6: return "NAS卷";
            default: return "未知(" + type + ")";
        }
    }

    private String getStateDesc(byte state) {
        switch (state) {
            case 0: return "离线";
            case 1: return "运行中";
            default: return "未知(" + state + ")";
        }
    }

    private String getOpStateDesc(int state) {
        switch (state) {
            case 0: return "正常工作";
            case 1: return "休眠中";
            case 2: return "等待格式化";
            case 3: return "格式化进行中";
            case 4: return "等待碎片整理";
            case 5: return "碎片整理中";
            case 6: return "等待创建RAID";
            case 7: return "创建RAID中";
            case 8: return "等待删除RAID";
            case 9: return "删除RAID中";
            case 10: return "等待文件系统修复";
            case 11: return "文件系统修复中";
            case 12: return "等待预检";
            case 13: return "正在预检";
            default: return "未知(" + state + ")";
        }
    }

    @Override
    public DahuaSystemResourceInfo getSystemResourceInfo(Long id) {
        log.info("开始获取大华设备系统资源信息, deviceId:{}", id);
        DahuaSystemResourceInfo result = new DahuaSystemResourceInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            // 尝试获取系统资源信息（超时时间缩短为3秒）
            try {
                IntByReference pRetLen = new IntByReference(0);
                NetSDKLib.NET_RESOURCE_STATE resourceState = new NetSDKLib.NET_RESOURCE_STATE();

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_RESOURCE,
                        resourceState.getPointer(),
                        resourceState.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    resourceState.read();

                    result.setIpChannelIn(resourceState.nIPChanneIn);
                    result.setNetRemain(resourceState.nNetRemain);
                    result.setNetCapability(resourceState.nNetCapability);
                    result.setRemotePreview(resourceState.nRemotePreview);
                    result.setRemotePlayDownload(resourceState.nRmtPlayDownload);
                    result.setRemoteSendRemain(resourceState.nRemoteSendRemain);
                    result.setRemoteSendCapability(resourceState.nRemoteSendCapability);

                    log.info("获取系统资源信息成功, deviceId:{}", id);
                } else {
                    log.warn("获取系统资源信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                }
            } catch (Exception e) {
                log.warn("获取系统资源信息时出现异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备系统资源信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取系统资源信息异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaSDCardInfo getSDCardInfo(Long id) {
        log.info("开始获取大华设备SD卡信息, deviceId:{}", id);
        DahuaSDCardInfo result = new DahuaSDCardInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            // 先尝试用NET_DEVSTATE_DISK获取存储设备信息
            try {
                IntByReference pRetLen = new IntByReference(0);
                NetSDKLib.NET_DEV_HARDDISK_STATE diskState = new NetSDKLib.NET_DEV_HARDDISK_STATE();

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_DISK,
                        diskState.getPointer(),
                        diskState.size(),
                        pRetLen,
                        5000
                );

                if (success) {
                    diskState.read();

                    result.setDiskCount(diskState.dwDiskNum);
                    List<DahuaSDCardInfo.DiskInfo> diskList = new ArrayList<>();

                    for (int i = 0; i < diskState.dwDiskNum && i < NetSDKLib.NET_MAX_DISKNUM; i++) {
                        NetSDKLib.NET_DEV_DISKSTATE disk = diskState.stDisks[i];
                        DahuaSDCardInfo.DiskInfo diskInfo = new DahuaSDCardInfo.DiskInfo();

                        diskInfo.setDiskNumber(disk.bDiskNum & 0xFF);
                        diskInfo.setPartitionNumber(disk.bSubareaNum & 0xFF);
                        diskInfo.setVolume(disk.dwVolume);
                        diskInfo.setFreeSpace(disk.dwFreeSpace);
                        diskInfo.setStatus(disk.dwStatus & 0xFF);
                        diskInfo.setSignal(disk.bSignal & 0xFF);

                        // 解析状态字节
                        int diskType = (disk.dwStatus >> 4) & 0x0F;
                        int diskStatus = disk.dwStatus & 0x0F;
                        diskInfo.setDiskType(diskType);
                        diskInfo.setDiskStatus(diskStatus);

                        // 判断是否是SD卡（通常SD卡的分区号或磁盘号有特定标识）
                        // 或者根据设备类型判断，这里我们把所有存储设备都列出来
                        diskList.add(diskInfo);
                    }

                    result.setDiskList(diskList);
                    result.setExists(diskState.dwDiskNum > 0);

                    log.info("获取SD卡信息成功, deviceId:{}, diskCount:{}", id, diskState.dwDiskNum);
                } else {
                    log.warn("获取SD卡信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                    result.setExists(false);
                }
            } catch (Exception e) {
                log.warn("获取SD卡信息时出现异常: {}", e.getMessage(), e);
                result.setExists(false);
            }

            result.setSuccess(true);
            log.info("获取大华设备SD卡信息完成, deviceId:{}, exists:{}", id, result.getExists());
        } catch (Exception e) {
            log.error("获取SD卡信息异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaBitrateInfo getBitrateInfo(Long id) {
        log.info("开始获取大华设备通道码流信息, deviceId:{}", id);
        DahuaBitrateInfo result = new DahuaBitrateInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            List<DahuaBitrateInfo.ChannelBitrate> channelBitrates = new ArrayList<>();
            
            // 尝试获取码流信息（超时时间缩短为3秒）
            try {
                IntByReference pRetLen = new IntByReference(0);
                // 分配足够大的缓冲区，假设最多64个通道
                int bufferSize = 4096;
                Memory buffer = new Memory(bufferSize);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_BITRATE,
                        buffer,
                        bufferSize,
                        pRetLen,
                        3000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取到码流信息数据长度: {}", retLen);
                    
                    // 尝试解析返回的数据（根据常见的大华SDK格式，可能是通道号+码流值的数组）
                    if (retLen > 0 && retLen <= bufferSize) {
                        // 这里需要根据实际设备返回的数据格式进行解析
                        // 暂时先创建一些占位信息
                        for (int i = 0; i < Math.min(retLen / 8, 32); i++) {
                            DahuaBitrateInfo.ChannelBitrate channel = new DahuaBitrateInfo.ChannelBitrate();
                            channel.setChannelId(i);
                            // 尝试从buffer中读取数据（这里只是示意，具体格式需要根据实际SDK文档）
                            channel.setBitrate(0);
                            channelBitrates.add(channel);
                        }
                    }
                } else {
                    log.warn("获取码流信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                }
            } catch (Exception e) {
                log.warn("获取码流信息时出现异常: {}", e.getMessage());
            }

            result.setChannelBitrates(channelBitrates);
            result.setSuccess(true);

            log.info("获取大华设备通道码流信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取码流信息异常, deviceId:{}, error:{}", id, e.getMessage());
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaNetworkStatusInfo getNetworkStatusInfo(Long id) {
        log.info("开始获取大华设备网络状态信息, deviceId:{}", id);
        DahuaNetworkStatusInfo result = new DahuaNetworkStatusInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            result.setIpAddress(device.getIpAddress());
            
            // 先尝试获取网络信息（超时时间缩短为3秒）
            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory buffer = new Memory(4096);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_NET,
                        buffer,
                        (int)buffer.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取到网络信息数据长度: {}", retLen);
                    result.setNetworkAvailable(true);
                } else {
                    log.warn("获取网络信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                    // 如果 NET 失败，尝试 NETINTERFACE
                    try {
                        netsdk.CLIENT_QueryDevState(
                                m_hLoginHandle,
                                NetSDKLib.NET_DEVSTATE_NETINTERFACE,
                                buffer,
                                (int)buffer.size(),
                                pRetLen,
                                3000
                        );
                        result.setNetworkAvailable(true);
                    } catch (Exception e2) {
                        log.warn("获取 NETINTERFACE 状态也失败: {}", e2.getMessage());
                    }
                }
            } catch (Exception e) {
                log.warn("获取网络状态时出现异常: {}", e.getMessage());
            }

            // 尝试获取无线信号强度（如果支持，超时时间缩短为2秒）
            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory buffer = new Memory(1024);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_NET_RSSI,
                        buffer,
                        (int)buffer.size(),
                        pRetLen,
                        2000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取到无线信号强度数据长度: {}", retLen);
                    result.setHasWireless(true);
                }
            } catch (Exception e) {
                log.debug("设备可能不支持无线信号查询: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备网络状态信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取网络状态异常, deviceId:{}, error:{}", id, e.getMessage());
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaSoftwareVersionInfo getSoftwareVersionInfo(Long id) {
        log.info("开始获取大华设备软件版本信息, deviceId:{}", id);
        DahuaSoftwareVersionInfo result = new DahuaSoftwareVersionInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            try {
                IntByReference pRetLen = new IntByReference(0);
                NetSDKLib.NETDEV_VERSION_INFO versionInfo = new NetSDKLib.NETDEV_VERSION_INFO();

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_SOFTWARE,
                        versionInfo.getPointer(),
                        versionInfo.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    versionInfo.read();

                    result.setSerialNumber(new String(versionInfo.szDevSerialNo, Charset.forName("GBK")).trim());
                    result.setDeviceModel(new String(versionInfo.szDevType, Charset.forName("GBK")).trim());
                    result.setSoftwareVersion(new String(versionInfo.szSoftWareVersion, Charset.forName("GBK")).trim());
                    result.setHardwareVersion(new String(versionInfo.szHardwareVersion, Charset.forName("GBK")).trim());
                    result.setWebVersion(new String(versionInfo.szWebVersion, Charset.forName("GBK")).trim());
                    result.setPeripheralVersion(new String(versionInfo.szPeripheralSoftwareVersion, Charset.forName("GBK")).trim());
                    result.setGeographyVersion(new String(versionInfo.szGeographySoftwareVersion, Charset.forName("GBK")).trim());
                    result.setProtocolVersion(versionInfo.nProtocalVer);
                    result.setSoftwareBuildDate(versionInfo.dwSoftwareBuildDate);
                    result.setPeripheralBuildDate(versionInfo.dwPeripheralSoftwareBuildDate);
                    result.setGeographyBuildDate(versionInfo.dwGeographySoftwareBuildDate);
                    result.setHardwareDate(versionInfo.dwHardwareDate);
                    result.setWebBuildDate(versionInfo.dwWebBuildDate);

                    // 组合完整版本信息
                    StringBuilder fullVersion = new StringBuilder();
                    if (!result.getSoftwareVersion().isEmpty()) {
                        fullVersion.append("软件:").append(result.getSoftwareVersion());
                    }
                    if (!result.getHardwareVersion().isEmpty()) {
                        if (fullVersion.length() > 0) fullVersion.append(", ");
                        fullVersion.append("硬件:").append(result.getHardwareVersion());
                    }
                    if (!result.getWebVersion().isEmpty()) {
                        if (fullVersion.length() > 0) fullVersion.append(", ");
                        fullVersion.append("Web:").append(result.getWebVersion());
                    }
                    result.setFullVersion(fullVersion.toString());

                    log.info("获取软件版本信息成功, deviceId:{}, model:{}", id, result.getDeviceModel());
                } else {
                    log.warn("获取软件版本信息失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                }
            } catch (Exception e) {
                log.warn("获取软件版本信息时出现异常: {}", e.getMessage(), e);
            }

            result.setSuccess(true);
            log.info("获取大华设备软件版本信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取软件版本信息异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaRecordStateInfo getRecordStateInfo(Long id) {
        log.info("开始获取大华设备录像状态信息, deviceId:{}", id);
        DahuaRecordStateInfo result = new DahuaRecordStateInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            // 获取整体录像状态
            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory wholeState = new Memory(4);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_WHOLE_RECORDING,
                        wholeState,
                        4,
                        pRetLen,
                        3000
                );

                if (success) {
                    result.setWholeRecording(wholeState.getInt(0) != 0);
                }
            } catch (Exception e) {
                log.warn("获取整体录像状态异常: {}", e.getMessage());
            }

            // 获取整体编码状态
            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory wholeState = new Memory(4);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_WHOLE_ENCODING,
                        wholeState,
                        4,
                        pRetLen,
                        3000
                );

                if (success) {
                    result.setWholeEncoding(wholeState.getInt(0) != 0);
                }
            } catch (Exception e) {
                log.warn("获取整体编码状态异常: {}", e.getMessage());
            }

            // 尝试获取每个通道的录像状态
            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory buffer = new Memory(8192);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_RECORDING_DETAIL,
                        buffer,
                        (int) buffer.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取录像细节数据长度: {}", retLen);
                    
                    List<DahuaRecordStateInfo.ChannelRecordState> channelStates = new ArrayList<>();
                    int structSize = new NetSDKLib.NET_RECORD_STATE_DETAIL().size();
                    int count = retLen / structSize;
                    
                    for (int i = 0; i < count && i < 64; i++) {
                        NetSDKLib.NET_RECORD_STATE_DETAIL detail = 
                            new NetSDKLib.NET_RECORD_STATE_DETAIL();
                        
                        // 手动实现结构体内存复制
                        detail.write();
                        Pointer pJavaMem = detail.getPointer();
                        pJavaMem.write(0, buffer.getByteArray(i * structSize, structSize), 0, structSize);
                        detail.read();
                        
                        DahuaRecordStateInfo.ChannelRecordState state = new DahuaRecordStateInfo.ChannelRecordState();
                        state.setChannelId(i);
                        state.setMainStreamRecording(detail.bMainStream != 0);
                        state.setExtraStream1Recording(detail.bExtraStream1 != 0);
                        state.setExtraStream2Recording(detail.bExtraStream2 != 0);
                        state.setExtraStream3Recording(detail.bExtraStream3 != 0);
                        
                        channelStates.add(state);
                    }
                    
                    result.setChannelStates(channelStates);
                }
            } catch (Exception e) {
                log.warn("获取录像细节异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备录像状态信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取录像状态异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaPowerStateInfo getPowerStateInfo(Long id) {
        log.info("开始获取大华设备电源状态信息, deviceId:{}", id);
        DahuaPowerStateInfo result = new DahuaPowerStateInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            try {
                com.ruoyi.dahua.lib.structure.NET_POWER_STATUS powerStatus =
                        new com.ruoyi.dahua.lib.structure.NET_POWER_STATUS();
                IntByReference pRetLen = new IntByReference(0);
                
                powerStatus.write();
                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_POWER_STATE,
                        powerStatus.getPointer(),
                        powerStatus.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    powerStatus.read();
                    int retLen = pRetLen.getValue();
                    log.info("获取电源状态数据长度: {}, 电源数量: {}, 电池数量: {}", 
                            retLen, powerStatus.nCount, powerStatus.nBatteryNum);
                    
                    // 处理电源状态
                    result.setPowerCount(powerStatus.nCount);
                    List<DahuaPowerStateInfo.PowerState> powerStates = new ArrayList<>();
                    
                    for (int i = 0; i < powerStatus.nCount && i < powerStatus.stuPowers.length; i++) {
                        com.ruoyi.dahua.lib.structure.NET_POWER_INFO powerInfo = powerStatus.stuPowers[i];
                        DahuaPowerStateInfo.PowerState state = new DahuaPowerStateInfo.PowerState();
                        state.setPowerId(i);
                        state.setPowerName("电源" + (i + 1));
                        state.setStatus(powerInfo.bPowerOn);
                        
                        String statusDesc = "";
                        if (powerInfo.bPowerOn == 0) {
                            statusDesc = "关闭";
                        } else if (powerInfo.bPowerOn == 1) {
                            statusDesc = "开启";
                        } else if (powerInfo.bPowerOn == 2) {
                            statusDesc = "开启但有故障";
                        }
                        state.setStatusDesc(statusDesc);
                        state.setOnline(powerInfo.bPowerOn != 0);
                        
                        powerStates.add(state);
                    }
                    
                    result.setPowerStates(powerStates);
                } else {
                    log.warn("获取电源状态失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                }
            } catch (Exception e) {
                log.warn("获取电源状态异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备电源状态信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取电源状态异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaAlarmArmInfo getAlarmArmInfo(Long id) {
        log.info("开始获取大华设备报警布撤防信息, deviceId:{}", id);
        DahuaAlarmArmInfo result = new DahuaAlarmArmInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            try {
                // 首先获取报警通道数量
                com.ruoyi.dahua.lib.structure.NET_ALARM_CHANNEL_COUNT channelCount = 
                    new com.ruoyi.dahua.lib.structure.NET_ALARM_CHANNEL_COUNT();
                IntByReference pRetLen = new IntByReference(0);
                
                channelCount.write();
                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_ALARM_CHN_COUNT,
                        channelCount.getPointer(),
                        channelCount.size(),
                        pRetLen,
                        3000
                );
                
                int totalAlarmIn = 0;
                if (success) {
                    channelCount.read();
                    totalAlarmIn = channelCount.nLocalAlarmIn + channelCount.nRemoteAlarmIn;
                    log.info("报警通道数量 - 本地输入:{}, 本地输出:{}, 远程输入:{}, 远程输出:{}", 
                        channelCount.nLocalAlarmIn, channelCount.nLocalAlarmOut,
                        channelCount.nRemoteAlarmIn, channelCount.nRemoteAlarmOut);
                }
                
                // 获取报警输入通道信息
                List<DahuaAlarmArmInfo.AlarmChannelState> channelStates = new ArrayList<>();
                if (totalAlarmIn > 0) {
                    int bufferSize = new com.ruoyi.dahua.lib.structure.NET_ALARM_IN_CHANNEL().size() * Math.max(totalAlarmIn, 64);
                    Memory buffer = new Memory(bufferSize);
                    pRetLen = new IntByReference(0);
                    
                    success = netsdk.CLIENT_QueryDevState(
                            m_hLoginHandle,
                            NetSDKLib.NET_DEVSTATE_ALARM_IN_CHANNEL,
                            buffer,
                            bufferSize,
                            pRetLen,
                            3000
                    );
                    
                    if (success) {
                        int retLen = pRetLen.getValue();
                        log.info("获取报警输入通道数据长度: {}", retLen);
                        
                        int structSize = new com.ruoyi.dahua.lib.structure.NET_ALARM_IN_CHANNEL().size();
                        int count = retLen / structSize;
                        
                        for (int i = 0; i < count && i < 64; i++) {
                            com.ruoyi.dahua.lib.structure.NET_ALARM_IN_CHANNEL alarmChannel = 
                                new com.ruoyi.dahua.lib.structure.NET_ALARM_IN_CHANNEL();
                            
                            // 手动实现结构体内存复制
                            alarmChannel.write();
                            Pointer pJavaMem = alarmChannel.getPointer();
                            pJavaMem.write(0, buffer.getByteArray(i * structSize, structSize), 0, structSize);
                            alarmChannel.read();
                            
                            if (alarmChannel.bValid != 0) {
                                DahuaAlarmArmInfo.AlarmChannelState state = new DahuaAlarmArmInfo.AlarmChannelState();
                                state.setChannelId(alarmChannel.nChannel);
                                state.setChannelName(new String(alarmChannel.szName).trim());
                                state.setArmed(false); // 默认值，需要根据实际布撤防状态设置
                                state.setArmType(0);
                                state.setArmTypeDesc("未知");
                                
                                channelStates.add(state);
                            }
                        }
                    }
                }
                
                // 尝试获取布撤防状态
                try {
                    pRetLen = new IntByReference(0);
                    Memory buffer = new Memory(4096);
                    
                    success = netsdk.CLIENT_QueryDevState(
                            m_hLoginHandle,
                            NetSDKLib.NET_DEVSTATE_ALARM_ARM_DISARM,
                            buffer,
                            (int)buffer.size(),
                            pRetLen,
                            3000
                    );
                    
                    if (success) {
                        int retLen = pRetLen.getValue();
                        log.info("获取报警布撤防数据长度: {}", retLen);
                        
                        // 如果有布撤防数据，尝试更新通道的布撤防状态
                        if (retLen > 0 && retLen <= channelStates.size() * 4) {
                            for (int i = 0; i < Math.min(channelStates.size(), retLen / 4); i++) {
                                int armState = buffer.getInt(i * 4);
                                channelStates.get(i).setArmed(armState != 0);
                                channelStates.get(i).setArmTypeDesc(armState != 0 ? "布防" : "撤防");
                            }
                        }
                    }
                } catch (Exception e) {
                    log.warn("获取报警布撤防状态异常: {}", e.getMessage());
                }
                
                result.setChannelCount(channelStates.size());
                result.setChannelStates(channelStates);
                
            } catch (Exception e) {
                log.warn("获取报警信息异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备报警布撤防信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取报警布撤防异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaCameraInfo getCameraInfo(Long id) {
        log.info("开始获取大华设备摄像头属性信息, deviceId:{}", id);
        DahuaCameraInfo result = new DahuaCameraInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory buffer = new Memory(8192);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_CAMERA,
                        buffer,
                        (int)buffer.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取摄像头属性数据长度: {}", retLen);
                    
                    // 尝试解析摄像头信息
                    if (retLen > 0) {
                        List<DahuaCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                        int structSize = new com.ruoyi.dahua.lib.structure.NET_CAMERA_INFO().size();
                        int cameraCount = retLen / structSize;
                        
                        for (int i = 0; i < cameraCount && i < 64; i++) {
                            com.ruoyi.dahua.lib.structure.NET_CAMERA_INFO cameraInfo = 
                                new com.ruoyi.dahua.lib.structure.NET_CAMERA_INFO();
                            
                            // 从内存中读取结构体
                            // 手动实现结构体内存复制
                            cameraInfo.write();
                            Pointer pJavaMem = cameraInfo.getPointer();
                            pJavaMem.write(0, buffer.getByteArray(i * structSize, structSize), 0, structSize);
                            cameraInfo.read();
                            
                            DahuaCameraInfo.CameraInfo info = new DahuaCameraInfo.CameraInfo();
                            info.setChannelId(cameraInfo.nChannel);
                            info.setCameraName(new String(cameraInfo.szName).trim());
                            info.setOnline(cameraInfo.bEnable != 0);
                            
                            String channelType = "";
                            switch (cameraInfo.emChannelType) {
                                case 0: channelType = "本地"; break;
                                case 1: channelType = "远程"; break;
                                case 2: channelType = "虚拟"; break;
                                default: channelType = "未知";
                            }
                            info.setCameraType(channelType);
                            
                            cameraList.add(info);
                        }
                        
                        result.setCameraCount(cameraList.size());
                        result.setCameraList(cameraList);
                    }
                } else {
                    log.warn("获取摄像头属性失败, deviceId:{}, error:{}", id, getErrorCodePrint());
                }
            } catch (Exception e) {
                log.warn("获取摄像头属性异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备摄像头属性信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取摄像头属性异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public DahuaRtspUrlInfo getRtspUrlInfo(Long id) {
        log.info("开始获取大华设备RTSP URL信息, deviceId:{}", id);
        DahuaRtspUrlInfo result = new DahuaRtspUrlInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
                result.setErrorMessage("大华设备未登录");
                return result;
            }

            try {
                IntByReference pRetLen = new IntByReference(0);
                Memory buffer = new Memory(4096);

                boolean success = netsdk.CLIENT_QueryDevState(
                        m_hLoginHandle,
                        NetSDKLib.NET_DEVSTATE_RTSP_URL,
                        buffer,
                        (int)buffer.size(),
                        pRetLen,
                        3000
                );

                if (success) {
                    int retLen = pRetLen.getValue();
                    log.info("获取RTSP URL数据长度: {}", retLen);
                }
            } catch (Exception e) {
                log.warn("获取RTSP URL异常: {}", e.getMessage());
            }

            // 生成标准的RTSP URL（备用方案）
            try {
                String baseUrl = "rtsp://" + device.getUserName() + ":" + device.getPassword() + "@" + device.getIpAddress() + ":554";
                
                DahuaRtspUrlInfo.RtspUrl mainUrl = new DahuaRtspUrlInfo.RtspUrl();
                mainUrl.setChannelId(1);
                mainUrl.setStreamType("主码流");
                mainUrl.setUrl(baseUrl + "/cam/realmonitor?channel=1&subtype=0");
                mainUrl.setDescription("主码流实时预览");
                result.getUrlList().add(mainUrl);

                DahuaRtspUrlInfo.RtspUrl subUrl = new DahuaRtspUrlInfo.RtspUrl();
                subUrl.setChannelId(1);
                subUrl.setStreamType("子码流");
                subUrl.setUrl(baseUrl + "/cam/realmonitor?channel=1&subtype=1");
                subUrl.setDescription("子码流实时预览");
                result.getUrlList().add(subUrl);

                result.setUrlCount(2);
            } catch (Exception e) {
                log.warn("生成标准URL异常: {}", e.getMessage());
            }

            result.setSuccess(true);
            log.info("获取大华设备RTSP URL信息完成, deviceId:{}", id);
        } catch (Exception e) {
            log.error("获取RTSP URL异常, deviceId:{}, error:{}", id, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true);
        }

        return result;
    }

    @Override
    public java.io.File downloadRecordFile(DahuaRecordDownloadRequest request) throws Exception {
        log.info("开始下载大华设备录像(直接返回文件), deviceId:{}, channelId:{}, 开始时间:{}, 结束时间:{}",
                request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(request.getId(), SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", request.getId(), r.getCode(), r.getMsg());
                throw new RuntimeException(r.getMsg());
            }
            QsDevice device = r.getData();
            log.debug("获取设备信息成功, deviceId:{}, IP:{}", request.getId(), device.getIpAddress());

            NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());
            if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
                log.error("大华设备未登录, deviceId:{}, IP:{}", request.getId(), device.getIpAddress());
                throw new RuntimeException("大华设备未登录");
            }

            // 🚀 第一步：先查询这个时间段里有哪些录像！
            log.info("先查询该时间段的录像列表, deviceId:{}, channelId:{}, startTime:{}, endTime:{}",
                    request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());

            ArrayList<HashMap<String, Object>> recordList;
            try {
                recordList = queryRecord(request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());
            } catch (Exception e) {
                log.warn("查询录像失败, 继续尝试直接下载, error:{}", e.getMessage());
                recordList = null;
            }

            // 解析开始和结束时间
            NetSDKLib.NET_TIME stTimeStart = new NetSDKLib.NET_TIME();
            NetSDKLib.NET_TIME stTimeEnd = new NetSDKLib.NET_TIME();

            try {
                String[] dateStartByFile = request.getStartTime().split(" ");
                String[] dateStart1 = dateStartByFile[0].split("-");
                String[] dateStart2 = dateStartByFile[1].split(":");

                stTimeStart.dwYear = Integer.parseInt(dateStart1[0]);
                stTimeStart.dwMonth = Integer.parseInt(dateStart1[1]);
                stTimeStart.dwDay = Integer.parseInt(dateStart1[2]);
                stTimeStart.dwHour = Integer.parseInt(dateStart2[0]);
                stTimeStart.dwMinute = Integer.parseInt(dateStart2[1]);
                stTimeStart.dwSecond = Integer.parseInt(dateStart2[2]);

                String[] dateEndByFile = request.getEndTime().split(" ");
                String[] dateEnd1 = dateEndByFile[0].split("-");
                String[] dateEnd2 = dateEndByFile[1].split(":");

                stTimeEnd.dwYear = Integer.parseInt(dateEnd1[0]);
                stTimeEnd.dwMonth = Integer.parseInt(dateEnd1[1]);
                stTimeEnd.dwDay = Integer.parseInt(dateEnd1[2]);
                stTimeEnd.dwHour = Integer.parseInt(dateEnd2[0]);
                stTimeEnd.dwMinute = Integer.parseInt(dateEnd2[1]);
                stTimeEnd.dwSecond = Integer.parseInt(dateEnd2[2]);

                // 验证时间间隔不超过10分钟
                long startSeconds = stTimeStart.dwHour * 3600 + stTimeStart.dwMinute * 60 + stTimeStart.dwSecond;
                long endSeconds = stTimeEnd.dwHour * 3600 + stTimeEnd.dwMinute * 60 + stTimeEnd.dwSecond;
                long durationSeconds;

                if (stTimeStart.dwDay == stTimeEnd.dwDay && stTimeStart.dwMonth == stTimeEnd.dwMonth
                        && stTimeStart.dwYear == stTimeEnd.dwYear) {
                    durationSeconds = endSeconds - startSeconds;
                } else {
                    // 跨天的情况，先检查是否是同一天的不同小时，然后限制在10分钟内
                    durationSeconds = 0; // 跨天不允许，为了简化逻辑
                }

                if (durationSeconds <= 0 || durationSeconds > 10 * 60) {
                    log.error("录像下载时间间隔必须在10分钟以内, deviceId:{}, 持续时间:{}秒", request.getId(), durationSeconds);
                    throw new RuntimeException("录像下载时间间隔必须在10分钟以内");
                }

            } catch (Exception e) {
                log.error("时间解析失败, deviceId:{}, error:{}", request.getId(), e.getMessage(), e);
                throw new RuntimeException("时间格式不正确，请使用 yyyy-MM-dd HH:mm:ss 格式");
            }

            // 🚀 如果查询到了录像，我们再调用一次 queryRecordFile 来获取更精确的 NET_RECORDFILE_INFO
            // 然后用第一个录像文件的真实开始和结束时间来下载！
            NetSDKLib.NET_RECORDFILE_INFO targetFileInfo = null;
            if (recordList != null && recordList.size() > 0) {
                log.info("查询到 {} 条录像记录，获取详细文件信息", recordList.size());

                // 录像文件信息
                NetSDKLib.NET_RECORDFILE_INFO[] stFileInfo = (NetSDKLib.NET_RECORDFILE_INFO[]) new NetSDKLib.NET_RECORDFILE_INFO().toArray(2000);
                IntByReference nFindCount = new IntByReference(0);

                if (queryRecordFile(request.getChannelId(), stTimeStart, stTimeEnd, stFileInfo, nFindCount, m_hLoginHandle)) {
                    int totalCount = nFindCount.getValue();
                    if (totalCount > 0) {
                        // 取第一条录像
                        targetFileInfo = stFileInfo[0];
                        log.info("获取到第一条录像的详细信息, 真实开始时间:{}, 真实结束时间:{}",
                                targetFileInfo.starttime.toStringTime(), targetFileInfo.endtime.toStringTime());

                        // ⚠️ 重要！使用真实的开始时间，但是结束时间只取开始时间+10分钟，或者用户指定的结束时间（取较小的那个）
                        stTimeStart = targetFileInfo.starttime;

                        // 计算开始时间+10分钟
                        NetSDKLib.NET_TIME maxEndTime = new NetSDKLib.NET_TIME();
                        maxEndTime.dwYear = stTimeStart.dwYear;
                        maxEndTime.dwMonth = stTimeStart.dwMonth;
                        maxEndTime.dwDay = stTimeStart.dwDay;
                        maxEndTime.dwHour = stTimeStart.dwHour;
                        maxEndTime.dwMinute = stTimeStart.dwMinute + 10;
                        maxEndTime.dwSecond = stTimeStart.dwSecond;

                        // 处理分钟+10后的溢出
                        if (maxEndTime.dwMinute >= 60) {
                            maxEndTime.dwMinute -= 60;
                            maxEndTime.dwHour += 1;
                        }
                        if (maxEndTime.dwHour >= 24) {
                            maxEndTime.dwHour -= 24;
                            maxEndTime.dwDay += 1;
                        }

                        // 比较 maxEndTime 和用户请求的 endTime，取较小的那个作为结束时间
                        // 先把用户的 endTime 也转成 Date 来比较
                        java.util.Calendar userEndCal = java.util.Calendar.getInstance();
                        userEndCal.set(stTimeEnd.dwYear, stTimeEnd.dwMonth - 1, stTimeEnd.dwDay, stTimeEnd.dwHour, stTimeEnd.dwMinute, stTimeEnd.dwSecond);

                        java.util.Calendar maxEndCal = java.util.Calendar.getInstance();
                        maxEndCal.set(maxEndTime.dwYear, maxEndTime.dwMonth - 1, maxEndTime.dwDay, maxEndTime.dwHour, maxEndTime.dwMinute, maxEndTime.dwSecond);

                        if (userEndCal.before(maxEndCal)) {
                            stTimeEnd = stTimeEnd; // 用户请求的结束时间更早，用用户的
                            log.info("使用用户请求的结束时间: {}", stTimeEnd.toStringTime());
                        } else {
                            stTimeEnd = maxEndTime; // 开始时间+10分钟更早，用这个
                            log.info("限制下载时间为开始时间+10分钟: {}", stTimeEnd.toStringTime());
                        }
                    }
                }
            }

            // 创建保存目录 - 使用原来的配置路径
            String saveDir = filePath + "/dahua/record/" + request.getId() + "/" + System.currentTimeMillis();
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成文件名
            String fileName = "device_" + request.getId() + "_channel_" + request.getChannelId() + "_"
                    + request.getStartTime().replace(":", "-").replace(" ", "_") + ".dav";
            String fullPath = saveDir + File.separator + fileName;

            log.debug("保存录像文件路径: {}", fullPath);

            // 创建下载回调
            RecordDownloadCallback callback = new RecordDownloadCallback();
            callback.setSavePath(fullPath);
            // 不需要 response，设置 null

            // 录像文件类型，默认主码流
            int recordFileType = request.getRecordFileType() != null ? request.getRecordFileType() : 0;

            // 开始下载
            NetSDKLib.LLong downloadHandle = netsdk.CLIENT_DownloadByTimeEx(
                    m_hLoginHandle,
                    request.getChannelId(),
                    recordFileType,
                    stTimeStart,
                    stTimeEnd,
                    fullPath,
                    callback,
                    null,
                    null,
                    null,
                    null
            );

            if (downloadHandle.longValue() == 0) {
                log.error("录像下载失败, deviceId:{}, error:{}", request.getId(), getErrorCodePrint());
                throw new RuntimeException("录像下载失败: " + getErrorCodePrint());
            }

            // 等待下载完成，最多等待5分钟
            int maxWaitTime = 5 * 60 * 1000;
            int waitInterval = 1000;
            int waitedTime = 0;
            boolean downloadCompleted = false;

            while (waitedTime < maxWaitTime && !downloadCompleted) {
                Thread.sleep(waitInterval);
                waitedTime += waitInterval;

                if (callback.isCompleted()) {
                    downloadCompleted = true;
                }

                if (callback.getDownloadSize() > 0) {
                    long totalSize = callback.getTotalSize();
                    int progress = totalSize > 0 ? (int) (callback.getDownloadSize() * 100 / totalSize) : 0;
                    // 直接返回文件，不需要进度
                }
            }

            // 如果下载还未完成，强制停止
            if (!downloadCompleted) {
                netsdk.CLIENT_StopDownload(downloadHandle);
                log.warn("录像下载超时，强制停止, deviceId:{}", request.getId());
                throw new RuntimeException("录像下载超时");
            }

            // 检查是否下载成功
            if (callback.getError() != null) {
                // 检查文件是否存在（双重保险）
                File downloadFile = new File(fullPath);
                if (downloadFile.exists() && downloadFile.length() > 0) {
                    log.warn("回调报告失败但文件实际已下载成功，继续处理, deviceId:{}", request.getId());
                } else {
                    log.error("录像下载失败, deviceId:{}, error:{}", request.getId(), callback.getError());
                    throw new RuntimeException("录像下载失败: " + callback.getError());
                }
            }

            // 检查文件是否存在
            File downloadFile = new File(fullPath);
            if (!downloadFile.exists() || downloadFile.length() == 0) {
                log.error("录像文件不存在或为空, deviceId:{}, path:{}", request.getId(), fullPath);
                throw new RuntimeException("录像文件下载失败");
            }

            log.info("录像下载成功, deviceId:{}, 路径:{}, 大小:{}字节", request.getId(), fullPath, downloadFile.length());
            return downloadFile;

        } catch (Exception e) {
            log.error("录像下载异常, deviceId:{}, error:{}", request.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public DahuaRecordDownloadResponse downloadRecord(DahuaRecordDownloadRequest request) {
        log.info("开始下载大华设备录像, deviceId:{}, channelId:{}, 开始时间:{}, 结束时间:{}",
                request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());

        DahuaRecordDownloadResponse response = new DahuaRecordDownloadResponse();
        response.setSuccess(false);

        try {
            File downloadFile = downloadRecordFile(request);

            // 构建响应
            response.setSuccess(true);
            response.setFilePath(downloadFile.getAbsolutePath());
            response.setFileSize(downloadFile.length());

            // 构建访问URL
            String timestampDir = downloadFile.getParentFile().getName();
            response.setFileUrl(fileDomain + "/dahua/record/" + request.getId() + "/" + timestampDir + "/" + downloadFile.getName());
            response.setProgress(100);

            log.info("录像下载成功, deviceId:{}, 路径:{}, 大小:{}字节", request.getId(), downloadFile.getAbsolutePath(), downloadFile.length());

        } catch (Exception e) {
            log.error("录像下载异常, deviceId:{}, error:{}", request.getId(), e.getMessage(), e);
            response.setErrorMessage("录像下载异常: " + e.getMessage());
        }

        return response;
    }

    /**
     * 录像下载回调类
     */
    private static class RecordDownloadCallback implements NetSDKLib.fTimeDownLoadPosCallBack {
        private String savePath;
        private DahuaRecordDownloadResponse response;
        private boolean completed = false;
        private String error;
        private long totalSize = 0;
        private long downloadSize = 0;

        public void setSavePath(String savePath) {
            this.savePath = savePath;
        }

        public void setResponse(DahuaRecordDownloadResponse response) {
            this.response = response;
        }

        public boolean isCompleted() {
            return completed;
        }

        public String getError() {
            return error;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public long getDownloadSize() {
            return downloadSize;
        }

        @Override
        public void invoke(NetSDKLib.LLong lPlayHandle, int dwTotalSize, int dwDownLoadSize,
                          int index, NetSDKLib.NET_RECORDFILE_INFO.ByValue recordfileinfo, Pointer dwUser) {

            log.debug("录像下载回调, 总大小:{}, 已下载:{}", dwTotalSize, dwDownLoadSize);

            totalSize = dwTotalSize;
            downloadSize = dwDownLoadSize;

            if (dwDownLoadSize == -1) {
                // 🚀 关键修改：先不急着标记失败！检查一下文件是否已经下载成功了！
                if (savePath != null) {
                    java.io.File downloadFile = new java.io.File(savePath);
                    if (downloadFile.exists() && downloadFile.length() > 0) {
                        log.info("dwDownLoadSize == -1 但是文件已经下载成功，视为下载完成！文件大小:{}字节", downloadFile.length());
                        completed = true;
                        error = null; // 清除错误
                        return;
                    }
                }
                log.error("录像下载失败: 文件不存在");
                completed = true;
                error = "录像文件不存在";
            } else if (dwDownLoadSize == dwTotalSize) {
                log.info("录像下载完成");
                completed = true;
                error = null;
            }
        }
    }
}
