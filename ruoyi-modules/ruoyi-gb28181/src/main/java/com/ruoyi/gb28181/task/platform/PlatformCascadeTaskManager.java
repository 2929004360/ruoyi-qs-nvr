package com.ruoyi.gb28181.task.platform;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.gb28181.api.domain.CatalogRequest;
import com.ruoyi.gb28181.api.domain.Gb28181Platform;
import com.ruoyi.gb28181.service.IPlatformSIPCommander;
import com.ruoyi.gb28181.util.PlatformConvertUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsGb28181PlatformService;
import com.ruoyi.qs.api.RemoteQsGroupService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsGb28181Platform;
import com.ruoyi.qs.api.domain.QsGb28181PlatformChannel;
import com.ruoyi.qs.api.domain.SimpleDeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 平台级联任务管理器
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class PlatformCascadeTaskManager {

    @Autowired
    private RemoteQsGb28181PlatformService remoteQsPlatformService;

    @Autowired
    @Lazy
    private IPlatformSIPCommander platformSIPCommander;

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private RemoteQsGroupService remoteQsGroupService;

    private final Map<Long, PlatformCascadeTaskInfo> taskMap = new ConcurrentHashMap<>();

    /**
     * 定时检查并执行任务，每500ms检查一次
     */
    @Scheduled(fixedDelay = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void executeTasks() {
        long currentTime = System.currentTimeMillis();
        for (PlatformCascadeTaskInfo taskInfo : taskMap.values()) {
            try {
                // 检查注册超时
                if (taskInfo.isRegistered()) {
                    checkRegisterTimeout(taskInfo, currentTime);
                }

                // 执行心跳或注册
                long timeSinceLastExecute = currentTime - taskInfo.getLastExecuteTime();
                if (timeSinceLastExecute >= taskInfo.getHeartbeatInterval() * 1000L) {
                    executeTask(taskInfo, currentTime);
                    taskInfo.setLastExecuteTime(currentTime);
                }
            } catch (Exception e) {
                log.error("[平台级联] 平台 {} 任务执行异常", taskInfo.getPlatform().getName(), e);
            }
        }
    }

    /**
     * 检查注册超时
     */
    private void checkRegisterTimeout(PlatformCascadeTaskInfo taskInfo, long currentTime) {
        if (taskInfo.getRegisterExpires() > 0) {
            long timeSinceRegister = currentTime - taskInfo.getLastRegisterTime();
            if (timeSinceRegister >= taskInfo.getRegisterExpires() * 1000L) {
                log.warn("[平台级联] 平台 {} 注册超时({}秒)，标记为离线", 
                    taskInfo.getPlatform().getName(), taskInfo.getRegisterExpires());
                taskInfo.setRegistered(false);
                updatePlatformStatus(taskInfo.getPlatform().getId(), 0);
            }
        }
    }

    /**
     * 更新平台状态
     */
    public void updatePlatformStatus(Long platformId, Integer status) {
        try {
            QsGb28181Platform platform = remoteQsPlatformService.selectPlatformById(platformId, SecurityConstants.INNER).getData();
            if (platform != null) {
                platform.setStatus(status);
                remoteQsPlatformService.updatePlatform(platform, SecurityConstants.INNER);
            }
        } catch (Exception e) {
            log.error("[平台级联] 更新平台状态失败", e);
        }
    }

    /**
     * 记录注册失败
     */
    public void onRegisterFailure(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.get(platformId);
        if (taskInfo != null) {
            taskInfo.setRegistered(false);
            updatePlatformStatus(platformId, 0);
        }
    }

    /**
     * 启动所有启用的平台
     */
    public void startAll() {
        QsGb28181Platform queryPlatform = new QsGb28181Platform();
        queryPlatform.setEnable(1);
        List<QsGb28181Platform> platformList = remoteQsPlatformService.selectPlatformList(queryPlatform, SecurityConstants.INNER).getData();

        if (platformList != null) {
            for (QsGb28181Platform qsPlatform : platformList) {
                Gb28181Platform platform = PlatformConvertUtil.convertToGbPlatform(qsPlatform);
                startPlatform(platform);
            }
        }
    }

    /**
     * 启动单个平台
     */
    public void startPlatform(Gb28181Platform platform) {
        if (taskMap.containsKey(platform.getId())) {
            log.warn("[平台级联] 平台 {} 任务已存在，先停止", platform.getName());
            stopPlatform(platform.getId());
        }

        try {
            PlatformCascadeTaskInfo taskInfo = new PlatformCascadeTaskInfo();
            taskInfo.setPlatform(platform);
            taskInfo.setRegistered(false);
            taskInfo.setLastExecuteTime(0);
            taskInfo.setLastHeartbeatTime(0);
            taskInfo.setLastRegisterTime(0);

            // 心跳间隔设置为60秒
            int heartbeatInterval = 60;
            try {
                String keepTimeout = platform.getKeepTimeout();
                if (keepTimeout != null && !keepTimeout.isEmpty()) {
                    int timeout = Integer.parseInt(keepTimeout);
                    heartbeatInterval = timeout / 2;
                    if (heartbeatInterval < 5) {
                        heartbeatInterval = 5;
                    }
                }
            } catch (NumberFormatException e) {
                log.warn("[平台级联] 平台 {} 心跳间隔解析失败，使用默认值 60s", platform.getName());
            }
            taskInfo.setHeartbeatInterval(heartbeatInterval);

            // 注册有效期设置为3600秒
            int registerExpires = 3600;
            try {
                String expires = platform.getExpires();
                if (expires != null && !expires.isEmpty()) {
                    registerExpires = Integer.parseInt(expires);
                }
            } catch (NumberFormatException e) {
                log.warn("[平台级联] 平台 {} 注册有效期解析失败，使用默认值 3600s", platform.getName());
            }
            taskInfo.setRegisterExpires(registerExpires);

            taskMap.put(platform.getId(), taskInfo);
            log.info("[平台级联] 平台 {} 任务启动成功，心跳间隔: {}s，注册有效期: {}s", 
                platform.getName(), heartbeatInterval, registerExpires);
        } catch (Exception e) {
            log.error("[平台级联] 平台 {} 任务启动失败", platform.getName(), e);
        }
    }

    /**
     * 停止单个平台
     */
    public void stopPlatform(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.remove(platformId);

        if (taskInfo != null) {
            try {
                platformSIPCommander.unregister(taskInfo.getPlatform());
            } catch (Exception e) {
                log.error("[平台级联] 平台 {} 注销失败", taskInfo.getPlatform().getName(), e);
            }
            // 更新状态为离线
            updatePlatformStatus(platformId, 0);
        }

        log.info("[平台级联] 平台 {} 任务已停止", platformId);
    }

    /**
     * 停止所有平台
     */
    public void stopAll() {
        log.info("[平台级联] 开始停止所有平台级联任务");
        
        // 先停止所有任务
        for (Long platformId : taskMap.keySet()) {
            stopPlatform(platformId);
        }
        
        // 查询所有启用的平台，全部更新为离线状态
        try {
            QsGb28181Platform queryPlatform = new QsGb28181Platform();
            queryPlatform.setEnable(1);
            List<QsGb28181Platform> platformList = remoteQsPlatformService.selectPlatformList(queryPlatform, SecurityConstants.INNER).getData();
            
            if (platformList != null) {
                for (QsGb28181Platform platform : platformList) {
                    if (!Integer.valueOf(0).equals(platform.getStatus())) {
                        updatePlatformStatus(platform.getId(), 0);
                        log.info("[平台级联] 平台 {} 已更新为离线状态", platform.getName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("[平台级联] 更新所有平台离线状态失败", e);
        }
        
        log.info("[平台级联] 所有平台任务已停止，所有平台状态已更新为离线");
    }

    /**
     * 重启单个平台
     */
    public void restartPlatform(Long platformId) {
        QsGb28181Platform qsPlatform = remoteQsPlatformService.selectPlatformById(platformId, SecurityConstants.INNER).getData();
        if (qsPlatform != null) {
            Gb28181Platform platform = PlatformConvertUtil.convertToGbPlatform(qsPlatform);
            stopPlatform(platformId);
            if (platform.getEnable() != null && platform.getEnable() == 1) {
                startPlatform(platform);
            }
        }
    }

    /**
     * 手动注销单个平台
     */
    public void unregisterPlatform(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.get(platformId);
        if (taskInfo != null) {
            try {
                platformSIPCommander.unregister(taskInfo.getPlatform());
                taskInfo.setRegistered(false);
                updatePlatformStatus(platformId, 0);
                log.info("[平台级联] 平台 {} 手动注销成功", taskInfo.getPlatform().getName());
            } catch (Exception e) {
                log.error("[平台级联] 平台 {} 手动注销失败", taskInfo.getPlatform().getName(), e);
            }
        }
    }

    /**
     * 手动推送通道
     */
    public void pushCatalog(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.get(platformId);
        if (taskInfo != null && taskInfo.isRegistered()) {
            try {
                sendCatalog(taskInfo);
                log.info("[平台级联] 平台 {} 手动推送目录成功", taskInfo.getPlatform().getName());
            } catch (Exception e) {
                log.error("[平台级联] 平台 {} 手动推送目录失败", taskInfo.getPlatform().getName(), e);
            }
        } else {
            log.warn("[平台级联] 平台 {} 未注册，无法推送目录", platformId);
        }
    }

    /**
     * 记录注册成功
     */
    public void onRegisterSuccess(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.get(platformId);
        if (taskInfo != null) {
            taskInfo.setRegistered(true);
            taskInfo.setLastRegisterTime(System.currentTimeMillis());
            updatePlatformStatus(platformId, 1);
            log.info("[平台级联] 平台 {} 注册成功", taskInfo.getPlatform().getName());
        }
    }

    /**
     * 记录心跳成功
     */
    public void onHeartbeatSuccess(Long platformId) {
        PlatformCascadeTaskInfo taskInfo = taskMap.get(platformId);
        if (taskInfo != null) {
            taskInfo.setLastHeartbeatTime(System.currentTimeMillis());
        }
    }

    /**
     * 执行单个任务
     */
    private void executeTask(PlatformCascadeTaskInfo taskInfo, long currentTime) {
        Gb28181Platform platform = taskInfo.getPlatform();
        try {
            if (!taskInfo.isRegistered()) {
                platformSIPCommander.register(platform);
                log.info("[平台级联] 平台 {} 注册请求已发送", platform.getName());
            } else {
                try {
                    platformSIPCommander.keepAlive(platform);
                    taskInfo.setLastHeartbeatTime(currentTime);
                } catch (Exception e) {
                    log.error("[平台级联] 平台 {} 发送心跳失败", platform.getName(), e);
                    taskInfo.setRegistered(false);
                    updatePlatformStatus(platform.getId(), 0);
                }
            }
        } catch (Exception e) {
            log.error("[平台级联] 平台 {} 任务执行失败", platform.getName(), e);
            taskInfo.setRegistered(false);
            updatePlatformStatus(platform.getId(), 0);
        }
    }

    /**
     * 发送目录
     */
    private void sendCatalog(PlatformCascadeTaskInfo taskInfo) throws InvalidArgumentException, ParseException, SipException {
        Gb28181Platform platform = taskInfo.getPlatform();
        List<SimpleDeviceInfo> deviceList = getDeviceList(platform);

        try {
            platformSIPCommander.sendCatalog(platform, deviceList);
            log.info("[平台级联] 发送目录到平台: {}, 设备数: {}", platform.getName(), deviceList.size());
        } catch (Exception e) {
            log.error("[平台级联] 平台 {} 发送目录异常", platform.getName(), e);
        }
    }

    /**
     * 获取设备列表（公共方法）
     */
    public List<SimpleDeviceInfo> getDeviceList(Gb28181Platform platform) {
        List<SimpleDeviceInfo> deviceList = new ArrayList<>();
        try {
            if (platform.getAutoPushChannel() != null && platform.getAutoPushChannel() == 1) {
                QsGb28181PlatformChannel queryChannel = new QsGb28181PlatformChannel();
                queryChannel.setPlatformId(platform.getId());
                log.info("[平台级联] 查询指定通道列表, platformId: {}", platform.getId());
                List<QsGb28181PlatformChannel> channelList = remoteQsPlatformService.selectPlatformChannelList(queryChannel, SecurityConstants.INNER).getData();
                if (channelList != null) {
                    log.info("[平台级联] 找到 {} 条指定通道", channelList.size());
                    for (QsGb28181PlatformChannel channel : channelList) {
                        QsDevice device = remoteQsDeviceService.getQsDeviceInfo(channel.getDeviceId(), SecurityConstants.INNER).getData();
                        if (device != null) {
                            log.info("[平台级联] 添加通道: {}", device.getDeviceName());
                            deviceList.add(convertToSimpleDeviceInfo(device));
                        }
                    }
                }
            } else {
                QsDevice queryDevice = new QsDevice();
                queryDevice.setStatus("ENABLE");
                // 不限制设备状态，查询所有启用的设备
                log.info("[平台级联] 查询全部启用的通道");
                List<QsDevice> allDeviceList = remoteQsDeviceService.list(queryDevice, SecurityConstants.INNER).getData();
                if (allDeviceList != null) {
                    log.info("[平台级联] 找到 {} 条通道", allDeviceList.size());
                    for (QsDevice device : allDeviceList) {
                        log.info("[平台级联] 添加通道: {}, 状态: {}", device.getDeviceName(), device.getDeviceStatus());
                        deviceList.add(convertToSimpleDeviceInfo(device));
                    }
                }
            }
        } catch (Exception e) {
            log.error("[平台级联] 查询设备列表失败", e);
        }
        log.info("[平台级联] 最终获取到 {} 条通道", deviceList.size());
        return deviceList;
    }

    private SimpleDeviceInfo convertToSimpleDeviceInfo(QsDevice device) {
        SimpleDeviceInfo simpleDeviceInfo = new SimpleDeviceInfo();
        simpleDeviceInfo.setGbCode(device.getGbCode());
        simpleDeviceInfo.setGbDeviceId(device.getGbDeviceId());
        simpleDeviceInfo.setDeviceName(device.getDeviceName());
        simpleDeviceInfo.setManufacturer(device.getManufacturer());
        simpleDeviceInfo.setAddress(device.getAddress());
        simpleDeviceInfo.setDeviceStatus(device.getDeviceStatus());
        simpleDeviceInfo.setChannel(device.getChannel());
        simpleDeviceInfo.setIpAddress(device.getIpAddress());
        simpleDeviceInfo.setPort(device.getPort());
        simpleDeviceInfo.setPtzType(device.getPtzType());
        simpleDeviceInfo.setLongitude(device.getLongitude());
        simpleDeviceInfo.setLatitude(device.getLatitude());
        simpleDeviceInfo.setGbParentId(device.getGbParentId());
        return simpleDeviceInfo;
    }

    /**
     * 获取分组树列表
     */
    public List<com.ruoyi.qs.api.domain.QsGroupTree> getGroupTreeList() {
        try {
            com.ruoyi.common.core.domain.R<List<com.ruoyi.qs.api.domain.QsGroupTree>> result = 
                remoteQsGroupService.queryAllGroups(null, SecurityConstants.INNER);
            if (result != null && result.getData() != null) {
                return result.getData();
            }
        } catch (Exception e) {
            log.error("[平台级联] 获取分组树失败", e);
        }
        return new ArrayList<>();
    }
}
