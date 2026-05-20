package com.ruoyi.haikang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.haikang.api.domain.PresetInfo;
import com.ruoyi.haikang.config.HaikangConfig;
import com.ruoyi.haikang.enums.HCPlayControlEnum;
import com.ruoyi.haikang.manager.StreamManager;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaiKangService;
import com.ruoyi.haikang.service.IHaikangMediaStreamService;
import com.ruoyi.haikang.utils.CommonUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.RemoteQsDeviceSnapshotService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.ruoyi.qs.api.domain.QsDeviceSnapshot;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.haikang.enums.HCCameraAuxEnum;
import com.ruoyi.haikang.enums.HCCruiseControlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

/**
 * @FileName HaiKangServiceImpl
 * @Description
 * @Author fengcheng
 * @date 2026-03-27
 **/
@Service
@Slf4j
public class HaiKangServiceImpl implements IHaiKangService {

    @Autowired
    private Client client;

    @Value("${file.path}")
    private String filePath;

    @Value("${file.domain}")
    private String fileDomain;

    @Value("${file.prefix}")
    private String filePrefix;

    /**
     * 设备信息
     */
    public static ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40> deviceInfoMap = new ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40>();

    /**
     * 海康登录用户ID
     */
    public static ConcurrentHashMap<String, Integer> userIdMap = new ConcurrentHashMap<String, Integer>();
    
    /**
     * 设备IP到QsDevice的映射
     */
    public static ConcurrentHashMap<String, QsDevice> qsDeviceMap = new ConcurrentHashMap<String, QsDevice>();
    
    /**
     * 获取qsDeviceMap
     */
    public static ConcurrentHashMap<String, QsDevice> getQsDeviceMap() {
        return qsDeviceMap;
    }

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IHaikangMediaStreamService mediaStreamService;

    @Autowired
    private RemoteQsDeviceSnapshotService remoteQsDeviceSnapshotService;

    @Autowired
    private HaikangConfig haikangConfig;

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    public int loginDevice(String ip, short port, String user, String psw) {
        log.info("开始登录海康设备, IP:{}, port:{}, user:{}", ip, port, user);
        
        // 检查是否已登录
        Integer existingUserId = userIdMap.get(ip);
        if (existingUserId != null && existingUserId >= 0) {
            log.warn("设备已登录，返回现有用户ID, IP:{}, userId:{}", ip, existingUserId);
            return existingUserId;
        }

        // 创建设备登录信息和设备信息对象
        HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        // 设置设备IP地址
        byte[] deviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        byte[] ipBytes = ip.getBytes();
        System.arraycopy(ipBytes, 0, deviceAddress, 0, Math.min(ipBytes.length, deviceAddress.length));
        loginInfo.sDeviceAddress = deviceAddress;

        // 设置用户名和密码
        byte[] userName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        byte[] password = psw.getBytes();
        System.arraycopy(user.getBytes(), 0, userName, 0, Math.min(user.length(), userName.length));
        System.arraycopy(password, 0, loginInfo.sPassword, 0, Math.min(password.length, loginInfo.sPassword.length));
        loginInfo.sUserName = userName;

        // 设置端口和登录模式
        loginInfo.wPort = port;
        // 同步登录
        loginInfo.bUseAsynLogin = false;
        // 使用SDK私有协议
        loginInfo.byLoginMode = 0;

        // 执行登录操作
        int userId = client.hCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
        if (userId == -1) {
            String errorMsg = "登录失败" + ip + "，错误码为: " + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        } else {
            log.info("海康设备登录成功, IP:{}, userId:{}", ip, userId);
        }

        userIdMap.put(ip, userId);
        deviceInfoMap.put(ip, deviceInfo);
        log.info("设备信息已保存, IP:{}, userId:{}", ip, userId);
        
        // 登录成功后自动布防（根据配置决定）
        if (Boolean.TRUE.equals(haikangConfig.getEnableAlarmListen())) {
            setupAlarm(ip);
        } else {
            log.info("报警监听已禁用，跳过设备 {} 的自动布防", ip);
        }
        
        return userId;
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip) {
        log.info("开始调用登出设备方法, IP:{}", ip);
        
        if (ObjectUtil.isNull(ip)) {
            log.error("登出设备失败，设备id不能为空");
            throw new ServiceException("设备id不能为空");
        }
        
        Integer userId = getUserId(ip);
        if (userId != null && userId >= 0) {
            log.debug("设备用户ID有效, IP:{}, userId:{}", ip, userId);
            try {
                log.info("开始登出海康设备, IP:{}", ip);
                
                // 先撤防（根据配置决定）
                if (Boolean.TRUE.equals(haikangConfig.getEnableAlarmListen())) {
                    closeAlarm(ip);
                }
                
                // 清理设备相关的流媒体资源
                cleanDeviceStreamResources(ip);
                
                if (!client.hCNetSDK.NET_DVR_Logout(userId)) {
                    log.error("注销失败, IP:{}, 错误码:{}", ip, client.hCNetSDK.NET_DVR_GetLastError());
                } else {
                    log.info("注销成功, IP:{}", ip);
                }
            } catch (Exception e) {
                log.error("海康设备登出异常, IP:{}", ip, e);
            } finally {
                userIdMap.remove(ip);
                deviceInfoMap.remove(ip);
                qsDeviceMap.remove(ip);
                log.info("设备信息已从缓存中移除, IP:{}", ip);
            }
        } else {
            log.warn("设备未登录，无需登出, IP:{}", ip);
        }
    }

    /**
     * 清理设备相关的流媒体资源
     */
    private void cleanDeviceStreamResources(String ip) {
        try {
            // 复制一份 key 列表，避免在遍历中修改集合
            List<String> streamKeysToClean = new ArrayList<>();
            StreamManager.streamKeyAndRealHandleMap.forEach((streamKey, handle) -> {
                if (streamKey.contains(ip)) {
                    streamKeysToClean.add(streamKey);
                }
            });
            
            // 逐个清理资源
            for (String streamKey : streamKeysToClean) {
                log.info("清理设备流媒体资源, streamKey:{}", streamKey);
                Long realHandle = StreamManager.streamKeyAndRealHandleMap.get(streamKey);
                FRealDataForRtpOverTcpCallback callback = StreamManager.streamKeyAndFRealDataForRtpOverTcpCallbackMap.get(streamKey);
                RtpServerParam rtpParam = StreamManager.streamKeyAndRtpServerParamMap.get(streamKey);
                
                mediaStreamService.cleanupResources(streamKey, rtpParam, realHandle, callback);
            }
            
        } catch (Exception e) {
            log.error("清理设备流媒体资源异常, IP:{}", ip, e);
        }
    }

    /**
     * 获取设备登录的用户ID
     *
     * @param ip 设备ip
     * @return
     */
    public Integer getUserId(String ip) {
        log.info("开始获取设备用户ID, IP:{}", ip);
        
        if (ObjectUtil.isNull(ip)) {
            log.error("获取用户ID失败，IP不能为空");
            throw new ServiceException("设备id不能为空");
        }

        Integer userId = userIdMap.get(ip);
        if (userId == null) {
            log.warn("设备用户ID不存在, IP:{}", ip);
        } else {
            log.info("获取设备用户ID成功, IP:{}, userId:{}", ip, userId);
        }
        return userId;
    }

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    @Override
    public HaikangDeviceInfo getDeviceInfo(String ip) {
        log.info("开始获取设备基本参数, IP:{}", ip);
        
        Integer iUserID = getUserId(ip);
        if (iUserID == null || iUserID < 0) {
            log.error("获取设备信息失败，设备未登录, IP:{}", ip);
            throw new ServiceException("设备未登录, IP:" + ip);
        }
        
        log.debug("设备用户ID有效, IP:{}, userId:{}", ip, iUserID);
        
        HaikangDeviceInfo deviceInfo = new HaikangDeviceInfo();
        HCNetSDK.NET_DVR_DEVICECFG_V40 m_strDeviceCfg = new HCNetSDK.NET_DVR_DEVICECFG_V40();
        m_strDeviceCfg.dwSize = m_strDeviceCfg.size();
        m_strDeviceCfg.write();
        Pointer pStrDeviceCfg = m_strDeviceCfg.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean b_GetCfg = client.hCNetSDK.NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DEVICECFG_V40, 0Xffffffff, pStrDeviceCfg, m_strDeviceCfg.dwSize, pInt);
        if (!b_GetCfg) {
            log.error("获取设备参数失败, IP:{}, 错误码：{}", ip, client.hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.debug("获取设备参数成功, IP:{}", ip);
        }
        m_strDeviceCfg.read();
        parseVersion(m_strDeviceCfg.dwSoftwareVersion, deviceInfo);
        parseBuildTime(m_strDeviceCfg.dwSoftwareBuildDate, deviceInfo);
        parseDSPBuildDate(m_strDeviceCfg.dwDSPSoftwareBuildDate, deviceInfo);

        deviceInfo.setDeviceName(CommonUtil.parseHikvisionString(m_strDeviceCfg.sDVRName));
        deviceInfo.setDeviceSerial(CommonUtil.parseHikvisionString(m_strDeviceCfg.sSerialNumber));
        deviceInfo.setByChanNum(m_strDeviceCfg.byChanNum);

        log.info("获取设备基本参数成功, IP:{}, deviceName:{}, serial:{}", ip, deviceInfo.getDeviceName(), deviceInfo.getDeviceSerial());
        return deviceInfo;
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    @Override
    public void startPlay(RtpServerParam rtpServerParam) {
        log.info("开始调用播放接口, deviceId:{}", rtpServerParam.getId());
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", rtpServerParam.getId(), r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());

        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", rtpServerParam.getId(), lUserID);

        log.info("开始播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.startPlay(lUserID, device, streamKey, rtpServerParam);
        log.info("播放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @Override
    public void stopPlay(Long id) {
        log.info("开始调用停止播放接口, deviceId:{}", id);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());
        
        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.warn("海康设备未登录，无法停止播放, deviceId:{}, IP:{}", id, device.getIpAddress());
            return;
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", id, lUserID);

        log.info("停止播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.endPlay(device.getId(), device.getChannel(), streamKey);
        log.info("停止播放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    @Override
    public void startPlayback(RtpServerParam rtpServerParam) {
        log.info("开始调用回放接口, deviceId:{}", rtpServerParam.getId());
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", rtpServerParam.getId(), r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());

        String playbackKey = "haikang_playback_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", rtpServerParam.getId(), device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", rtpServerParam.getId(), lUserID);

        log.info("开始回放海康设备流, deviceId:{}, channel:{}, playbackKey:{}", device.getId(), device.getChannel(), playbackKey);
        mediaStreamService.startPlayback(lUserID, device, playbackKey, rtpServerParam);
        log.info("回放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    @Override
    public void stopPlayback(Long id) {
        log.info("开始调用停止回放接口, deviceId:{}", id);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());
        
        String playbackKey = "haikang_playback_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.warn("海康设备未登录，无法停止回放, deviceId:{}, IP:{}", id, device.getIpAddress());
            return;
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", id, lUserID);

        log.info("停止回放海康设备流, deviceId:{}, channel:{}, playbackKey:{}", device.getId(), device.getChannel(), playbackKey);
        mediaStreamService.stopPlayback(lUserID, device.getId(), device.getChannel(), playbackKey);
        log.info("停止回放接口调用完成, deviceId:{}, channel:{}", device.getId(), device.getChannel());
    }

    //设备版本解析
    public void parseVersion(int version, HaikangDeviceInfo deviceInfo) {
        int firstVersion = (version & 0XFF << 24) >> 24;
        int secondVersion = (version & 0XFF << 16) >> 16;
        int lowVersion = version & 0XFF;

        deviceInfo.setFirstVersion(firstVersion);
        deviceInfo.setSecondVersion(secondVersion);
        deviceInfo.setLowVersion(lowVersion);
    }

    public void parseBuildTime(int buildTime, HaikangDeviceInfo deviceInfo) {
        int year = ((buildTime & 0XFF << 16) >> 16) + 2000;
        int month = (buildTime & 0XFF << 8) >> 8;
        int data = buildTime & 0xFF;

        deviceInfo.setBuildTime(year + "-" + month + "-" + data);
    }

    public void parseDSPBuildDate(int DSPBuildDate, HaikangDeviceInfo deviceInfo) {
        int year = ((DSPBuildDate & 0XFF << 16) >> 16) + 2000;
        int month = (DSPBuildDate & 0XFF << 8) >> 8;
        int data = DSPBuildDate & 0xFF;

        deviceInfo.setDSPBuildDate(year + "-" + month + "-" + data);
    }

    @Override
    public void startPlayControl(Long deviceId, int channelId, String direction) {
        log.info("开始云台控制, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
        
        if (StringUtils.isEmpty(direction)) {
            log.error("云台控制失败，方向不能为空, deviceId:{}", deviceId);
            throw new ServiceException("方向不能为空!");
        }
        HCPlayControlEnum hcPlayControlEnum = HCPlayControlEnum.fromValue(direction);
        if (hcPlayControlEnum == null) {
            log.error("云台控制失败，无效的方向参数, deviceId:{}, direction:{}", deviceId, direction);
            throw new ServiceException("无效的方向参数: " + direction);
        }
        log.debug("方向参数验证成功, deviceId:{}, direction:{}", deviceId, direction);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
        
        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);
        
        boolean playHandle = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, hcPlayControlEnum.getCode(), 0);
        IntByReference m_err = new IntByReference(0);
        if (!playHandle) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("云台控制失败, deviceId:{}, errorCode:{}, error:{}", deviceId, m_err.getValue(), err);
            throw new ServiceException("控制失败!错误原因:" + m_err.getValue() + "," + err);
        }
        
        log.info("云台控制成功, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
    }

    @Override
    public void endPlayControl(Long deviceId, int channelId, String direction) {
        log.info("结束云台控制, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
        
        if (StringUtils.isEmpty(direction)) {
            log.error("云台控制停止失败，方向不能为空, deviceId:{}", deviceId);
            throw new ServiceException("方向不能为空!");
        }
        HCPlayControlEnum hcPlayControlEnum = HCPlayControlEnum.fromValue(direction);
        if (hcPlayControlEnum == null) {
            log.error("云台控制停止失败，无效的方向参数, deviceId:{}, direction:{}", deviceId, direction);
            throw new ServiceException("无效的方向参数: " + direction);
        }
        log.debug("方向参数验证成功, deviceId:{}, direction:{}", deviceId, direction);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
        
        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);
        
        IntByReference m_err = new IntByReference(0);
        boolean playHandle = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, hcPlayControlEnum.getCode(), 1);
        if (!playHandle) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("云台控制停止失败, deviceId:{}, errorCode:{}, error:{}", deviceId, m_err.getValue(), err);
            throw new ServiceException("(停止)控制失败!错误原因:" + m_err.getValue() + "," + err);
        }
        
        log.info("云台控制停止成功, deviceId:{}, channelId:{}, direction:{}", deviceId, channelId, direction);
    }

    /**
     * 重启设备
     *
     * @param deviceId
     */
    @Override
    public void restartDevice(Long deviceId) {
        log.info("开始重启设备, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean b = client.hCNetSDK.NET_DVR_RebootDVR(lUserID);
        if (!b) {
            String errorMsg = "重启设备失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("重启设备成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
    }

    /**
     * 关机
     *
     * @param deviceId
     */
    @Override
    public void shutDown(Long deviceId) {
        log.info("开始关闭设备, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean b = client.hCNetSDK.NET_DVR_ShutDownDVR(lUserID);
        if (!b) {
            String errorMsg = "关机失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("关闭设备成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
    }

    /**
     * 获取设备音频编码参数，确定转发音频参数
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @Override
    public HashMap<String, Object> getCurrentAudio(Long deviceId, int channelId) {
        log.info("开始获取设备音频编码参数, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HashMap<String, Object> map = new HashMap<>(16);
        //获取设备音频编码参数，确定转发音频参数
        HCNetSDK.NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio = getCurrentAudioCompress(lUserID);

        map.put("byAudioEncType", net_dvr_compression_audio.byAudioEncType);
        map.put("byAudioSamplingRate", net_dvr_compression_audio.byAudioSamplingRate);

        log.info("获取设备音频编码参数成功, deviceId:{}, encType:{}, sampleRate:{}", deviceId, net_dvr_compression_audio.byAudioEncType, net_dvr_compression_audio.byAudioSamplingRate);
        return map;
    }

    private HCNetSDK.NET_DVR_COMPRESSION_AUDIO getCurrentAudioCompress(int lUserID) {
        log.debug("开始获取音频编码格式参数, userId:{}", lUserID);
        
        HCNetSDK.NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio = new HCNetSDK.NET_DVR_COMPRESSION_AUDIO();
        boolean b_AudioCompress = client.hCNetSDK.NET_DVR_GetCurrentAudioCompress(lUserID, net_dvr_compression_audio);
        if (!b_AudioCompress) {
            String errorMsg = "获取音频编码格式参数失败，错误码为" + client.hCNetSDK.NET_DVR_GetLastError();
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        net_dvr_compression_audio.read();
        
        log.debug("获取音频编码格式参数成功, userId:{}", lUserID);
        return net_dvr_compression_audio;
    }

    /**
     * 获取预置点列表
     *
     * @param deviceId
     * @param channelId
     * @return
     */
    @Override
    public List<PresetInfo> getPresets(Long deviceId, int channelId) {
        log.info("开始获取预置点列表, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        List<PresetInfo> presetsList = new ArrayList<>();

        HCNetSDK.NET_DVR_PRESET_NAME[] presetNames = new HCNetSDK.NET_DVR_PRESET_NAME[HCNetSDK.MAX_PRESET_V30];
        for (int i = 0; i < presetNames.length; i++) {
            presetNames[i] = new HCNetSDK.NET_DVR_PRESET_NAME();
            presetNames[i].dwSize = presetNames[i].size();
            presetNames[i].write();
        }
        Pointer lpOutBuffer = new Memory(presetNames.length * presetNames[0].size());
        int dwOutBufferSize = presetNames.length * presetNames[0].size();
        IntByReference lpBytesReturned = new IntByReference(0);

        boolean presetList = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PRESET_NAME, channelId, lpOutBuffer, dwOutBufferSize, lpBytesReturned);
        if (!presetList) {
            log.error("获取预置位数据失败, deviceId:{}, channelId:{}, 错误码：{}", deviceId, channelId, client.hCNetSDK.NET_DVR_GetLastError());
        } else {
            log.debug("获取预置位数据成功, deviceId:{}, channelId:{}", deviceId, channelId);
            ByteBuffer byteBuffer = lpOutBuffer.getByteBuffer(0, lpBytesReturned.getValue());
            // 设置字节顺序
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0; i < HCNetSDK.MAX_PRESET_V30; i++) {
                HCNetSDK.NET_DVR_PRESET_NAME presetNameStruct = new HCNetSDK.NET_DVR_PRESET_NAME();

                presetNameStruct.dwSize = byteBuffer.getInt();
                presetNameStruct.wPresetNum = byteBuffer.getShort();
                byteBuffer.get(presetNameStruct.byRes1); // 读取保留字节
                byteBuffer.get(presetNameStruct.byName); // 读取名称
                presetNameStruct.wPanPos = byteBuffer.getShort();//水平参数
                presetNameStruct.wTiltPos = byteBuffer.getShort();//垂直参数
                presetNameStruct.wZoomPos = byteBuffer.getShort();//变倍参数
                byteBuffer.get(presetNameStruct.byRes); // 读取保留字节
                if (presetNameStruct.wPresetNum != 0)
                // 输出预置位数据
                {
                    if (Integer.valueOf(presetNameStruct.wPanPos) != 0 && Integer.valueOf(presetNameStruct.wTiltPos) != 0) {
                        presetsList.add(new PresetInfo(presetNameStruct.wPresetNum, CommonUtil.parseHikvisionString(presetNameStruct.byName), presetNameStruct.wPanPos, presetNameStruct.wTiltPos, presetNameStruct.wZoomPos));
                    }
                }
            }

        }

        log.info("获取预置点列表完成, deviceId:{}, channelId:{}, count:{}", deviceId, channelId, presetsList.size());
        return presetsList;
    }

    /**
     * 设置预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void setPresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始设置预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：设置预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.SET_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("设置预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    /**
     * 清除预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void delPresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始清除预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：清楚预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.CLE_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "清除预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("清除预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    /**
     * 调用预置点
     *
     * @param deviceId
     * @param channelId
     * @param presetIndex
     * @return
     */
    @Override
    public void invokePresets(Long deviceId, int channelId, int presetIndex) {
        log.info("开始调用预置点, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // PTZ 控制命令：调用预置点
        boolean success = client.hCNetSDK.NET_DVR_PTZPreset_Other(
                lUserID,
                channelId,
                HCNetSDK.GOTO_PRESET,
                presetIndex
        );

        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "调用预置点位置失败, 错误码: " + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        log.info("调用预置点成功, deviceId:{}, channelId:{}, presetIndex:{}", deviceId, channelId, presetIndex);
    }

    @Override
    public void cameraAuxControl(Long deviceId, int channelId, String operation, boolean isStart) {
        log.info("开始辅助设备控制, deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);

        if (StringUtils.isEmpty(operation)) {
            log.error("辅助设备控制失败，操作类型不能为空, deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCCameraAuxEnum auxEnum = HCCameraAuxEnum.fromValue(operation);
        if (auxEnum == null) {
            log.error("辅助设备控制失败，无效的操作类型, deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型: " + operation);
        }
        log.debug("操作类型验证成功, deviceId:{}, operation:{}, msg:{}", deviceId, operation, auxEnum.getMsg());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        int action = isStart ? 0 : 1;
        boolean success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, auxEnum.getCode(), action);
        IntByReference m_err = new IntByReference(0);

        if (!success) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("辅助设备控制失败, deviceId:{}, operation:{}, errorCode:{}, error:{}", deviceId, operation, m_err.getValue(), err);
            throw new ServiceException("辅助设备控制失败！错误原因:" + m_err.getValue() + "," + err);
        }

        log.info("辅助设备控制成功, deviceId:{}, channelId:{}, operation:{}, isStart:{}", deviceId, channelId, operation, isStart);
    }

    @Override
    public void cruiseControl(Long deviceId, int channelId, String operation, Integer param) {
        log.info("开始巡航控制, deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);

        if (StringUtils.isEmpty(operation)) {
            log.error("巡航控制失败，操作类型不能为空, deviceId:{}", deviceId);
            throw new ServiceException("操作类型不能为空");
        }

        HCCruiseControlEnum cruiseEnum = HCCruiseControlEnum.fromValue(operation);
        if (cruiseEnum == null) {
            log.error("巡航控制失败，无效的操作类型, deviceId:{}, operation:{}", deviceId, operation);
            throw new ServiceException("无效的操作类型: " + operation);
        }
        log.debug("操作类型验证成功, deviceId:{}, operation:{}, msg:{}", deviceId, operation, cruiseEnum.getMsg());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        boolean success;
        IntByReference m_err = new IntByReference(0);

        if (param != null) {
            success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, cruiseEnum.getCode(), param);
        } else {
            success = client.hCNetSDK.NET_DVR_PTZControl_Other(lUserID, channelId, cruiseEnum.getCode(), 0);
        }

        if (!success) {
            String err = client.hCNetSDK.NET_DVR_GetErrorMsg(m_err);
            log.error("巡航控制失败, deviceId:{}, operation:{}, errorCode:{}, error:{}", deviceId, operation, m_err.getValue(), err);
            throw new ServiceException("巡航控制失败！错误原因:" + m_err.getValue() + "," + err);
        }

        log.info("巡航控制成功, deviceId:{}, channelId:{}, operation:{}, param:{}", deviceId, channelId, operation, param);
    }

    @Override
    public HashMap<String, Object> getPTZcfg(Long deviceId, int channelId) {
        log.info("开始获取球机PTZ参数, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZPOS ptzPos = new HCNetSDK.NET_DVR_PTZPOS();
        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PTZPOS, channelId, ptzPos.getPointer(), ptzPos.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取球机PTZ参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        ptzPos.read();

        HashMap<String, Object> result = new HashMap<>();
        result.put("wAction", ptzPos.wAction);
        result.put("wPanPos", ptzPos.wPanPos);
        result.put("wTiltPos", ptzPos.wTiltPos);
        result.put("wZoomPos", ptzPos.wZoomPos);

        log.info("获取球机PTZ参数成功, deviceId:{}, channelId:{}, pan:{}, tilt:{}, zoom:{}", deviceId, channelId, ptzPos.wPanPos, ptzPos.wTiltPos, ptzPos.wZoomPos);
        return result;
    }

    @Override
    public void setPTZcfg(Long deviceId, int channelId, short p, short t, short z) {
        log.info("开始设置球机PTZ参数, deviceId:{}, channelId:{}, p:{}, t:{}, z:{}", deviceId, channelId, p, t, z);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZPOS ptzPos = new HCNetSDK.NET_DVR_PTZPOS();
        ptzPos.wAction = 1;
        ptzPos.wPanPos = p;
        ptzPos.wTiltPos = t;
        ptzPos.wZoomPos = z;
        ptzPos.write();

        boolean success = client.hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_PTZPOS, channelId, ptzPos.getPointer(), ptzPos.size());
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置球机PTZ参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        log.info("设置球机PTZ参数成功, deviceId:{}, channelId:{}", deviceId, channelId);
    }

    @Override
    public HashMap<String, Object> getPTZAbsoluteEx(Long deviceId, int channelId) {
        log.info("开始获取高精度PTZ绝对位置配置, deviceId:{}, channelId:{}", deviceId, channelId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_PTZABSOLUTEEX_CFG ptzAbsoluteExCfg = new HCNetSDK.NET_DVR_PTZABSOLUTEEX_CFG();
        ptzAbsoluteExCfg.dwSize = ptzAbsoluteExCfg.size();
        ptzAbsoluteExCfg.write();

        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_PTZABSOLUTEEX, channelId, ptzAbsoluteExCfg.getPointer(), ptzAbsoluteExCfg.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取高精度PTZ绝对位置配置失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        ptzAbsoluteExCfg.read();

        HashMap<String, Object> result = new HashMap<>();
        result.put("dwSize", ptzAbsoluteExCfg.dwSize);
        result.put("dwFocalLen", ptzAbsoluteExCfg.dwFocalLen);
        result.put("fHorizontalSpeed", ptzAbsoluteExCfg.fHorizontalSpeed);
        result.put("fVerticalSpeed", ptzAbsoluteExCfg.fVerticalSpeed);
        result.put("byZoomType", ptzAbsoluteExCfg.byZoomType);

        log.info("获取高精度PTZ绝对位置配置成功, deviceId:{}, channelId:{}", deviceId, channelId);
        return result;
    }

    @Override
    public String getDevTime(Long deviceId) {
        log.info("开始获取设备时间参数, deviceId:{}", deviceId);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_TIME m_Time = new HCNetSDK.NET_DVR_TIME();
        boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_TIMECFG, 0, m_Time.getPointer(), m_Time.size(), new IntByReference(0));
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "获取设备时间参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        m_Time.read();

        Pointer pTime = m_Time.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean b_GetTime = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_TIMECFG, 0xffffffff, pTime, m_Time.size(), pInt);
        if (!b_GetTime) {
            throw new ServiceException("获取时间参数失败，错误码：" + client.hCNetSDK.NET_DVR_GetLastError());
        }
        m_Time.read();

        String time = String.format("%04d-%02d-%02d %02d:%02d:%02d", m_Time.dwYear, m_Time.dwMonth, m_Time.dwDay, m_Time.dwHour, m_Time.dwMinute, m_Time.dwSecond);
        log.info("获取设备时间参数成功, deviceId:{}, 时间:{}", deviceId, time);
        return time;
    }

    @Override
    public void setDevTime(Long deviceId, String time) {
        log.info("开始设置设备时间参数, deviceId:{}, time:{}", deviceId, time);
        
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        HCNetSDK.NET_DVR_TIME devTime = new HCNetSDK.NET_DVR_TIME();
        if (StringUtils.isNotEmpty(time)) {
            try {
                String[] timeParts = time.split("-| |:");
                if (timeParts.length >= 6) {
                    devTime.dwYear = Integer.parseInt(timeParts[0]);
                    devTime.dwMonth = Integer.parseInt(timeParts[1]);
                    devTime.dwDay = Integer.parseInt(timeParts[2]);
                    devTime.dwHour = Integer.parseInt(timeParts[3]);
                    devTime.dwMinute = Integer.parseInt(timeParts[4]);
                    devTime.dwSecond = Integer.parseInt(timeParts[5]);
                }
            } catch (Exception e) {
                log.error("解析时间格式失败, deviceId:{}, time:{}, error:{}", deviceId, time, e.getMessage());
                throw new ServiceException("时间格式错误，请使用格式: yyyy-MM-dd HH:mm:ss");
            }
        }
        devTime.write();

        boolean success = client.hCNetSDK.NET_DVR_SetDVRConfig(lUserID, HCNetSDK.NET_DVR_SET_TIMECFG, 0, devTime.getPointer(), devTime.size());
        
        if (!success) {
            int error = client.hCNetSDK.NET_DVR_GetLastError();
            String errorMsg = "设置设备时间参数失败，错误码：" + error;
            log.error(errorMsg);
            throw new ServiceException(errorMsg);
        }

        log.info("设置设备时间参数成功, deviceId:{}", deviceId);
    }

    /**
     * 海康设备查询录像
     *
     * @param deviceId  设备id
     * @param channelId 通道id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryRecord(Long deviceId, int channelId, String startTime, String endTime) {
        log.info("开始查询海康设备录像, deviceId:{}, channelId:{}, startTime:{}, endTime:{}", deviceId, channelId, startTime, endTime);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", deviceId, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }
        log.debug("设备用户ID有效, deviceId:{}, userId:{}", deviceId, lUserID);

        // 解码 URL 编码的时间参数
        try {
            startTime = java.net.URLDecoder.decode(startTime, "UTF-8");
            endTime = java.net.URLDecoder.decode(endTime, "UTF-8");
            log.debug("URL解码后的时间, startTime:{}, endTime:{}", startTime, endTime);
        } catch (Exception e) {
            log.warn("URL解码失败，使用原始时间, error:{}", e.getMessage());
        }

        // 查询录像
        ArrayList<HashMap<String, Object>> recordList = tryFindFile(lUserID, channelId, startTime, endTime, deviceId);

        if (recordList.isEmpty()) {
            log.warn("未查询到录像文件, deviceId:{}, channelId:{}", deviceId, channelId);
            throw new ServiceException("未查询到录像文件");
        }

        log.info("查询海康设备录像完成, deviceId:{}, channelId:{}, 共查询到 {} 条录像记录", deviceId, channelId, recordList.size());
        return recordList;
    }

    /**
     * 尝试使用旧版 API 查询录像
     */
    private ArrayList<HashMap<String, Object>> tryFindFile(int lUserID, int channelId, String startTime, String endTime, Long deviceId) {
        ArrayList<HashMap<String, Object>> recordList = new ArrayList<>();

        HCNetSDK.NET_DVR_FILECOND_V40 fileCondition = new HCNetSDK.NET_DVR_FILECOND_V40();
        fileCondition.read(); // 先读取初始值

        // 解析时间
        try {
            String[] dateStartByFile = startTime.split(" ");
            String[] dateStart1 = dateStartByFile[0].split("-");
            String[] dateStart2 = dateStartByFile[1].split(":");

            fileCondition.struStartTime.dwYear = Integer.parseInt(dateStart1[0]);
            fileCondition.struStartTime.dwMonth = Integer.parseInt(dateStart1[1]);
            fileCondition.struStartTime.dwDay = Integer.parseInt(dateStart1[2]);
            fileCondition.struStartTime.dwHour = Integer.parseInt(dateStart2[0]);
            fileCondition.struStartTime.dwMinute = Integer.parseInt(dateStart2[1]);
            fileCondition.struStartTime.dwSecond = Integer.parseInt(dateStart2[2]);

            String[] dateEndByFile = endTime.split(" ");
            String[] dateEnd1 = dateEndByFile[0].split("-");
            String[] dateEnd2 = dateEndByFile[1].split(":");

            fileCondition.struStopTime.dwYear = Integer.parseInt(dateEnd1[0]);
            fileCondition.struStopTime.dwMonth = Integer.parseInt(dateEnd1[1]);
            fileCondition.struStopTime.dwDay = Integer.parseInt(dateEnd1[2]);
            fileCondition.struStopTime.dwHour = Integer.parseInt(dateEnd2[0]);
            fileCondition.struStopTime.dwMinute = Integer.parseInt(dateEnd2[1]);
            fileCondition.struStopTime.dwSecond = Integer.parseInt(dateEnd2[2]);
        } catch (Exception e) {
            log.error("时间参数解析失败, startTime:{}, endTime:{}, error:{}", startTime, endTime, e.getMessage(), e);
            return recordList;
        }

        // 设置其他查询条件
        fileCondition.lChannel = channelId;
        fileCondition.dwFileType = 0xff; // 全部类型
        fileCondition.dwIsLocked = 0xff; // 所有文件
        fileCondition.byStreamType = 0; // 主码流
        fileCondition.write(); // 重要：写入内存！

        log.debug("开始查询, 通道号:{}, 时间范围:{}-{}", channelId, startTime, endTime);

        // 先尝试旧版 API
        int findHandle = client.hCNetSDK.NET_DVR_FindFile_V40(lUserID, fileCondition);
        
        if (findHandle == -1) {
            int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
            log.warn("NET_DVR_FindFile_V40 失败, 通道号:{}, 错误码:{}", channelId, errorCode);
            return recordList;
        }

        try {
            HCNetSDK.NET_DVR_FINDDATA_V40 findData = new HCNetSDK.NET_DVR_FINDDATA_V40();
            int findNextResult;

            while (true) {
                findNextResult = client.hCNetSDK.NET_DVR_FindNextFile_V40(findHandle, findData);
                if (findNextResult == 1000) { // NET_DVR_FILE_SUCCESS - 找到文件
                    findData.read(); // 读取数据

                    String fileName = CommonUtil.parseHikvisionString(findData.sFileName);
                    String start = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStartTime.dwYear, findData.struStartTime.dwMonth, findData.struStartTime.dwDay,
                            findData.struStartTime.dwHour, findData.struStartTime.dwMinute, findData.struStartTime.dwSecond);
                    String stop = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                            findData.struStopTime.dwYear, findData.struStopTime.dwMonth, findData.struStopTime.dwDay,
                            findData.struStopTime.dwHour, findData.struStopTime.dwMinute, findData.struStopTime.dwSecond);

                    HashMap<String, Object> record = new HashMap<>(16);
                    record.put("channel", String.valueOf(channelId));
                    record.put("type", getRecordTypeString(findData.byFileType));
                    record.put("start", start);
                    record.put("end", stop);
                    record.put("fileName", fileName);
                    record.put("fileSize", findData.dwFileSize);
                    recordList.add(record);
                    
                    log.debug("找到录像: channel={}, fileName={}, start={}, end={}", channelId, fileName, start, stop);
                } else if (findNextResult == 1003) { // NET_DVR_NOMOREFILE - 没有更多文件，查找结束
                    log.debug("查询结束, 共找到 {} 条记录", recordList.size());
                    break;
                } else if (findNextResult == 1001) { // NET_DVR_FILE_NOFIND - 未查找到文件
                    log.warn("未查找到文件");
                    break;
                } else if (findNextResult == 1002) { // NET_DVR_ISFINDING - 正在查找请等待
                    log.debug("正在查找，请等待...");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else if (findNextResult == 1004) { // NET_DVR_FILE_EXCEPTION - 查找文件时异常
                    log.warn("查找文件时异常");
                    break;
                } else if (findNextResult == 1005) { // NET_DVR_FIND_TIMEOUT - 查找文件超时
                    log.warn("查找文件超时");
                    break;
                } else { // 其他错误
                    int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                    log.warn("查找下一个文件失败, 返回值:{}, 错误码:{}", findNextResult, errorCode);
                    break;
                }
            }
        } finally {
            client.hCNetSDK.NET_DVR_FindClose_V30(findHandle);
        }

        return recordList;
    }

    /**
     * 将海康SDK错误码转换为可读字符串
     *
     * @param errorCode 海康SDK错误码
     * @return 可读的错误信息
     */
    private String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case 0:
                return "没有错误";
            case 1:
                return "用户名密码错误";
            case 3:
                return "SDK未初始化";
            case 5:
                return "连接到设备的用户个数超过最大";
            case 7:
                return "连接设备失败，设备不在线或网络原因引起的连接超时等";
            case 8:
                return "向设备发送失败";
            case 9:
                return "从设备接收数据失败";
            case 10:
                return "从设备接收数据超时";
            case 14:
                return "设备命令执行超时";
            case 17:
                return "参数错误，SDK接口中给入的输入或输出参数为空";
            case 34:
                return "创建文件出错，本地录像、保存图片、获取配置文件和远程下载录像时创建文件失败";
            case 41:
                return "SDK资源分配错误";
            case 43:
                return "缓冲区太小，接收设备数据的缓冲区或存放图片缓冲区不足";
            case 44:
                return "创建SOCKET出错";
            case 47:
                return "用户不存在，注册的用户ID已注销或不可用";
            case 72:
                return "绑定套接字失败";
            case 73:
                return "socket连接中断，此错误通常是由于连接中断或目的地不可达";
            default:
                return "未知错误(" + errorCode + ")";
        }
    }

    /**
     * 将海康设备的录像类型转换为可读字符串
     *
     * @param fileType 海康设备返回的文件类型
     * @return 可读的录像类型字符串
     */
    private String getRecordTypeString(byte fileType) {
        switch (fileType) {
            case 0:
                return "定时录像";
            case 1:
                return "移动侦测";
            case 2:
                return "报警触发";
            case 3:
                return "报警|移动侦测";
            case 4:
                return "报警&移动侦测";
            case 5:
                return "命令触发";
            case 6:
                return "手动录像";
            case 7:
                return "震动报警";
            case 8:
                return "环境报警";
            case 9:
                return "智能报警";
            case 10:
                return "PIR报警";
            case 11:
                return "无线报警";
            case 12:
                return "呼救报警";
            case 13:
                return "移动侦测/PIR/无线/呼救等报警";
            case 14:
                return "智能交通事件";
            case 15:
                return "越界侦测";
            case 16:
                return "区域入侵侦测";
            case 17:
                return "音频异常侦测";
            case 18:
                return "场景变更侦测";
            case 19:
                return "智能侦测";
            case 20:
                return "人脸侦测";
            case 21:
                return "信号量/POS录像";
            case 22:
                return "回传";
            case 23:
                return "回迁录像";
            case 24:
                return "遮挡";
            case 26:
                return "进入区域侦测";
            case 27:
                return "离开区域侦测";
            case 28:
                return "徘徊侦测";
            case 29:
                return "人员聚集侦测";
            case 30:
                return "快速运动侦测";
            case 31:
                return "停车侦测";
            case 32:
                return "物品遗留侦测";
            case 33:
                return "物品拿取侦测";
            case 34:
                return "火点检测";
            case 36:
                return "船只检测";
            case 37:
                return "测温预警";
            case 38:
                return "测温报警";
            case 42:
                return "温差报警";
            case 43:
                return "离线测温报警";
            case 44:
                return "防区报警";
            case 45:
                return "紧急求助";
            case 46:
                return "业务咨询";
            case 47:
                return "起身检测";
            case 48:
                return "折线攀高";
            case 49:
                return "目标区域滞留超时";
            default:
                return "未知类型(" + fileType + ")";
        }
    }

    /**
     * 海康设备录像下载（保存到后端）
     *
     * @param request 下载请求
     * @return 下载结果
     */
    @Override
    public com.ruoyi.haikang.api.domain.HaikangRecordDownloadResponse downloadRecord(com.ruoyi.haikang.api.domain.HaikangRecordDownloadRequest request) {
        log.info("开始下载海康设备录像, deviceId:{}, channelId:{}, 开始时间:{}, 结束时间:{}", 
            request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());
        
        com.ruoyi.haikang.api.domain.HaikangRecordDownloadResponse response = new com.ruoyi.haikang.api.domain.HaikangRecordDownloadResponse();
        
        try {
            // 下载文件
            File file = downloadRecordFile(request);
            
            response.setSuccess(true);
            response.setFilePath(file.getAbsolutePath());
            response.setFileSize(file.length());
            response.setProgress(100);
            
            log.info("海康设备录像下载成功, deviceId:{}, 路径:{}, 大小:{}字节", request.getId(), file.getAbsolutePath(), file.length());
        } catch (Exception e) {
            log.error("海康设备录像下载失败, deviceId:{}, error:{}", request.getId(), e.getMessage(), e);
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        
        return response;
    }

    /**
     * 海康设备录像下载（保存到临时文件）
     *
     * @param request 下载请求
     * @return 临时文件
     */
    @Override
    public File downloadRecordFile(com.ruoyi.haikang.api.domain.HaikangRecordDownloadRequest request) throws Exception {
        log.info("开始下载海康设备录像(直接返回文件), deviceId:{}, channelId:{}, 开始时间:{}, 结束时间:{}",
                request.getId(), request.getChannelId(), request.getStartTime(), request.getEndTime());

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(request.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", request.getId(), r.getCode(), r.getMsg());
            throw new ServiceException(r.getMsg());
        }
        QsDevice device = r.getData();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", request.getId(), device.getIpAddress());
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }

        // 解析时间
        LocalDateTime startTimeDt = LocalDateTime.parse(request.getStartTime().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTimeDt = LocalDateTime.parse(request.getEndTime().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 定义尝试策略
        class DownloadStrategy {
            boolean useV40;
            int streamType; // 0-主码流, 1-子码流
            String description;

            DownloadStrategy(boolean useV40, int streamType, String description) {
                this.useV40 = useV40;
                this.streamType = streamType;
                this.description = description;
            }
        }

        DownloadStrategy[] strategies = {
                new DownloadStrategy(true, 0, "V40+主码流"),
                new DownloadStrategy(true, 1, "V40+子码流"),
                new DownloadStrategy(false, 0, "旧版API+主码流"),
                new DownloadStrategy(false, 1, "旧版API+子码流")
        };

        for (DownloadStrategy strategy : strategies) {
            log.info("尝试下载策略: {}", strategy.description);

            // 创建保存目录
            String saveDir = filePath + "/haikang/record/" + request.getId() + "/" + System.currentTimeMillis();
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 文件名
            String fileName = "device_" + request.getId() + "_channel_" + request.getChannelId() +
                    "_" + request.getStartTime().replace(":", "-").replace(" ", "_") + ".mp4";
            String savePath = saveDir + "/" + fileName;

            int downloadHandle = -1;
            Timer downloadTimer = null;
            try {
                if (strategy.useV40) {
                    // 准备下载条件
                    HCNetSDK.NET_DVR_PLAYCOND playCond = new HCNetSDK.NET_DVR_PLAYCOND();
                    playCond.read();
                    playCond.dwChannel = request.getChannelId();
                    playCond.byStreamType = (byte) strategy.streamType;
                    
                    //开始时间
                    playCond.struStartTime.dwYear = startTimeDt.getYear();
                    playCond.struStartTime.dwMonth = startTimeDt.getMonthValue();
                    playCond.struStartTime.dwDay = startTimeDt.getDayOfMonth();
                    playCond.struStartTime.dwHour = startTimeDt.getHour();
                    playCond.struStartTime.dwMinute = startTimeDt.getMinute();
                    playCond.struStartTime.dwSecond = startTimeDt.getSecond();

                    //停止时间
                    playCond.struStopTime.dwYear = endTimeDt.getYear();
                    playCond.struStopTime.dwMonth = endTimeDt.getMonthValue();
                    playCond.struStopTime.dwDay = endTimeDt.getDayOfMonth();
                    playCond.struStopTime.dwHour = endTimeDt.getHour();
                    playCond.struStopTime.dwMinute = endTimeDt.getMinute();
                    playCond.struStopTime.dwSecond = endTimeDt.getSecond();
                    
                    playCond.write();

                    downloadHandle = client.hCNetSDK.NET_DVR_GetFileByTime_V40(lUserID, savePath, playCond);
                } else {
                    // 旧版API
                    HCNetSDK.NET_DVR_TIME startTimeStruct = new HCNetSDK.NET_DVR_TIME();
                    HCNetSDK.NET_DVR_TIME endTimeStruct = new HCNetSDK.NET_DVR_TIME();
                    
                    startTimeStruct.dwYear = startTimeDt.getYear();
                    startTimeStruct.dwMonth = startTimeDt.getMonthValue();
                    startTimeStruct.dwDay = startTimeDt.getDayOfMonth();
                    startTimeStruct.dwHour = startTimeDt.getHour();
                    startTimeStruct.dwMinute = startTimeDt.getMinute();
                    startTimeStruct.dwSecond = startTimeDt.getSecond();
                    
                    endTimeStruct.dwYear = endTimeDt.getYear();
                    endTimeStruct.dwMonth = endTimeDt.getMonthValue();
                    endTimeStruct.dwDay = endTimeDt.getDayOfMonth();
                    endTimeStruct.dwHour = endTimeDt.getHour();
                    endTimeStruct.dwMinute = endTimeDt.getMinute();
                    endTimeStruct.dwSecond = endTimeDt.getSecond();
                    
                    downloadHandle = client.hCNetSDK.NET_DVR_GetFileByTime(lUserID, request.getChannelId(), startTimeStruct, endTimeStruct, savePath);
                }

                if (downloadHandle < 0) {
                    int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                    log.warn("策略 {} 失败，句柄无效: {}, 错误码:{}", strategy.description, downloadHandle, errorCode);
                    continue;
                }

                CompletableFuture<String> future = new CompletableFuture<>();

                // 开始下载
                client.hCNetSDK.NET_DVR_PlayBackControl(downloadHandle, HCNetSDK.NET_DVR_PLAYSTART, 0, null);
                
                // 设置下载速度
                IntByReference intP = new IntByReference(5 * 8 * 1024);
                IntByReference intInlen = new IntByReference(0);
                boolean b_PlayBack = client.hCNetSDK.NET_DVR_PlayBackControl_V40(downloadHandle, 24, intP.getPointer(), 4, Pointer.NULL, intInlen);
                if (!b_PlayBack) {
                    log.warn("策略 {} 设置下载速度失败，错误码为: {}", strategy.description, client.hCNetSDK.NET_DVR_GetLastError());
                }

                log.info("策略 {} 开始下载...", strategy.description);
                downloadTimer = new Timer();
                downloadTimer.schedule(new DownloadTask(downloadHandle, downloadTimer, future, savePath, client, strategy.description), 0, 1000);

                // 等待下载完成（最多10分钟）
                future.get(60 * 10, TimeUnit.SECONDS);

                File file = new File(savePath);
                if (file.exists() && file.length() > 0) {
                    log.info("策略 {} 下载成功! 文件大小: {}字节", strategy.description, file.length());
                    return file;
                }

            } catch (Exception e) {
                log.error("策略 {} 下载异常: {}", strategy.description, e.getMessage());
            } finally {
                // 停止下载
                if (downloadHandle >= 0) {
                    client.hCNetSDK.NET_DVR_StopGetFile(downloadHandle);
                }
                if (downloadTimer != null) {
                    downloadTimer.cancel();
                }
            }
        }

        throw new ServiceException("所有下载策略都失败了");
    }

    /**
     * 下载任务类
     */
    class DownloadTask extends java.util.TimerTask {
        int downloadHandle;
        Timer downloadTimer;
        CompletableFuture<String> future;
        String savePath;
        com.ruoyi.haikang.net.Client client;
        String strategyDesc;
        int zeroFileSizeCount = 0;
        long lastFileSize = 0;
        int noProgressCount = 0;

        public DownloadTask(int downloadHandle, Timer downloadTimer, CompletableFuture<String> future, String savePath, com.ruoyi.haikang.net.Client client, String strategyDesc) {
            this.downloadHandle = downloadHandle;
            this.downloadTimer = downloadTimer;
            this.future = future;
            this.savePath = savePath;
            this.client = client;
            this.strategyDesc = strategyDesc;
        }

        @Override
        public void run() {
            IntByReference nPos = new IntByReference(0);
            client.hCNetSDK.NET_DVR_PlayBackControl(downloadHandle, HCNetSDK.NET_DVR_PLAYGETPOS, 0, nPos);
            
            // 检查文件大小
            File file = new File(savePath);
            long fileSize = file.exists() ? file.length() : 0;
            
            // 快速失败：文件大小长时间为0
            if (fileSize == 0) {
                zeroFileSizeCount++;
                if (zeroFileSizeCount > 15) { // 15秒文件大小还是0，放弃当前策略
                    log.warn("策略 {} 文件大小长时间为0，放弃当前策略", strategyDesc);
                    client.hCNetSDK.NET_DVR_StopGetFile(downloadHandle);
                    downloadTimer.cancel();
                    future.completeExceptionally(new RuntimeException("文件大小长时间为0"));
                    return;
                }
            } else {
                zeroFileSizeCount = 0;
            }
            
            // 检查文件是否在增长
            if (fileSize > lastFileSize) {
                noProgressCount = 0;
                lastFileSize = fileSize;
            } else {
                noProgressCount++;
            }
            
            log.info("策略 {} 下载进度: {}%, 文件大小: {}字节", strategyDesc, nPos.getValue(), fileSize);

            if (nPos.getValue() > 100) {
                log.warn("策略 {} 由于网络原因或DVR忙,下载异常终止!", strategyDesc);
                client.hCNetSDK.NET_DVR_StopGetFile(downloadHandle);
                downloadTimer.cancel();
                future.completeExceptionally(new RuntimeException("下载异常终止"));
            } else if (nPos.getValue() == 100) {
                log.info("策略 {} 下载完成!", strategyDesc);
                client.hCNetSDK.NET_DVR_StopGetFile(downloadHandle);
                downloadTimer.cancel();
                future.complete("SUCCESS");
            }
        }
    }

    @Override
    public Long captureAndSave(Long id, int channelId, String snapshotType) throws IOException {
        log.info("开始海康设备抓图, deviceId:{}, channelId:{}, snapshotType:{}", id, channelId, snapshotType);

        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (R.isError(r)) {
            log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", id, r.getCode(), r.getMsg());
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        log.debug("获取设备信息成功, deviceId:{}, IP:{}", id, device.getIpAddress());

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.error("海康设备未登录, deviceId:{}, IP:{}", id, device.getIpAddress());
            throw new RuntimeException("海康设备未登录, IP:" + device.getIpAddress());
        }

        // 准备需要尝试的通道号列表
        int[] channelIdsToTry = {channelId};
        // 如果传入的通道号不是0，也尝试0
        if (channelId != 0) {
            channelIdsToTry = new int[]{channelId, 0};
        }

        try {
            // 创建目录
            File dir = new File(filePath + "/haikang_snapshot/");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            byte[] imageData = null;
            int successChannelId = channelId;

            // 遍历通道号尝试抓图
            for (int currentChannelId : channelIdsToTry) {
                // 设置抓图参数
                HCNetSDK.NET_DVR_JPEGPARA jpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
                jpegPara.wPicQuality = 1; // 图片质量 0-最好，1-较好，2-一般，3-较差，4-最差
                jpegPara.wPicSize = 0xff; // 0xff表示按设备默认或者原来的分辨率
                jpegPara.write();

                // 分配内存（假设最大2MB）
                int bufferSize = 2 * 1024 * 1024;
                com.sun.jna.Memory jpegBuffer = new com.sun.jna.Memory(bufferSize);
                com.sun.jna.ptr.IntByReference sizeReturned = new com.sun.jna.ptr.IntByReference(0);

                log.info("发送抓图命令(内存), deviceId:{}, channelId:{}", id, currentChannelId);
                boolean success = client.hCNetSDK.NET_DVR_CaptureJPEGPicture_NEW(
                        lUserID,
                        currentChannelId,
                        jpegPara,
                        jpegBuffer,
                        bufferSize,
                        sizeReturned
                );

                if (!success) {
                    int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                    String errorMsg = "抓图失败(通道" + currentChannelId + "): 错误码=" + errorCode + ", 说明=" + getErrorMessage(errorCode);
                    log.error(errorMsg);
                    continue;
                }

                // 获取返回的图片大小
                int actualSize = sizeReturned.getValue();
                if (actualSize > 0) {
                    imageData = jpegBuffer.getByteArray(0, actualSize);
                    successChannelId = currentChannelId;
                    log.info("通道{}抓图成功! 图片大小:{} bytes", currentChannelId, actualSize);
                    break;
                } else {
                    log.warn("通道{}返回图片大小为0，尝试下一个通道", currentChannelId);
                }
            }

            if (imageData == null) {
                String errorMsg = "所有通道尝试抓图均失败";
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

            // 生成文件名并保存到文件
            String fileName = generateFileName(id, successChannelId);
            String localFilePath = filePath + "/haikang_snapshot/" + fileName;
            String fileUrl = fileDomain + filePrefix + "/haikang_snapshot/" + fileName;

            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                fos.write(imageData);
            }

            File savedFile = new File(localFilePath);
            log.info("图片保存成功, deviceId:{}, channelId:{}, filePath:{}, fileUrl:{}, fileSize:{}",
                    id, successChannelId, localFilePath, fileUrl, savedFile.length());

            // 构造抓图记录
            QsDeviceSnapshot snapshot = new QsDeviceSnapshot();
            snapshot.setDeviceId(device.getId());
            snapshot.setDeviceCode(device.getDeviceCode());
            snapshot.setDeviceName(device.getDeviceName());
            snapshot.setFileUrl(fileUrl);
            snapshot.setFilePath(localFilePath);
            snapshot.setFileSize(savedFile.length());
            snapshot.setFileName(fileName);
            snapshot.setFileType("jpg");
            snapshot.setSnapshotType(snapshotType);
            snapshot.setSdkType("hik");
            snapshot.setChannel(successChannelId);
            snapshot.setCaptureTime(new Date());

            // 保存到数据库
            R<Long> result = remoteQsDeviceSnapshotService.add(snapshot, SecurityConstants.INNER);

            if (R.isSuccess(result)) {
                log.info("抓图记录保存成功, snapshotId:{}", result.getData());
                return result.getData();
            } else {
                String errorMsg = "保存抓图记录失败: " + result.getMsg();
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

        } catch (Exception e) {
            log.error("海康设备抓图异常", e);
            throw e;
        }
    }

    @Override
    public HashMap<String, Object> getHaiKangDeviceInfo(Long deviceId) {
        log.info("开始获取海康设备信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }
            
            // 使用NET_DVR_DEVICECFG_V40获取设备配置信息
            HCNetSDK.NET_DVR_DEVICECFG_V40 deviceCfg = new HCNetSDK.NET_DVR_DEVICECFG_V40();
            deviceCfg.dwSize = deviceCfg.size();
            deviceCfg.write();
            Pointer pDeviceCfg = deviceCfg.getPointer();
            IntByReference pInt = new IntByReference(0);
            
            boolean bGetCfg = client.hCNetSDK.NET_DVR_GetDVRConfig(
                lUserID, 
                HCNetSDK.NET_DVR_GET_DEVICECFG_V40, 
                0Xffffffff, 
                pDeviceCfg, 
                deviceCfg.dwSize, 
                pInt
            );
            
            if (!bGetCfg) {
                log.error("获取设备配置失败, IP:{}, 错误码:{}", device.getIpAddress(), client.hCNetSDK.NET_DVR_GetLastError());
                result.put("message", "获取设备配置失败");
                return result;
            }
            
            deviceCfg.read();
            
            // 填充返回结果
            result.put("deviceName", new String(deviceCfg.sDVRName).trim());
            result.put("deviceType", new String(deviceCfg.byDevTypeName).trim());
            result.put("serialNumber", new String(deviceCfg.sSerialNumber).trim());
            result.put("ipAddress", device.getIpAddress());
            
            // 计算总通道数（模拟通道 + 数字通道）
            int analogChanNum = deviceCfg.byChanNum & 0xFF;
            int ipChanNum = ((deviceCfg.byHighIPChanNum & 0xFF) << 8) | (deviceCfg.byIPChanNum & 0xFF);
            int totalChanNum = analogChanNum + ipChanNum;
            result.put("channelNum", totalChanNum);
            result.put("analogChanNum", analogChanNum);
            result.put("ipChanNum", ipChanNum);
            
            // 其他信息
            result.put("dvrType", deviceCfg.byDVRType & 0xFF);
            result.put("devType", deviceCfg.wDevType);
            result.put("devClass", deviceCfg.wDevClass);
            result.put("alarmInPortNum", deviceCfg.byAlarmInPortNum & 0xFF);
            result.put("alarmOutPortNum", deviceCfg.byAlarmOutPortNum & 0xFF);
            result.put("diskNum", deviceCfg.byDiskNum & 0xFF);
            
            result.put("success", true);
            log.info("获取海康设备信息成功, deviceId:{}, deviceName:{}, serialNumber:{}", deviceId, new String(deviceCfg.sDVRName).trim(), new String(deviceCfg.sSerialNumber).trim());
        } catch (Exception e) {
            log.error("获取海康设备信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangStorageInfo(Long deviceId) {
        log.info("开始获取海康存储信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_HDCFG hdCfg = new HCNetSDK.NET_DVR_HDCFG();
            hdCfg.dwSize = hdCfg.size();
            hdCfg.write();
            IntByReference lpBytesReturned = new IntByReference(0);
            
            boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_HDCFG, 0, hdCfg.getPointer(), hdCfg.size(), lpBytesReturned);
            
            if (success) {
                hdCfg.read();
                List<HashMap<String, Object>> diskList = new ArrayList<>();
                
                for (int i = 0; i < hdCfg.dwHDCount; i++) {
                    HCNetSDK.NET_DVR_SINGLE_HD singleHd = hdCfg.struHDInfo[i];
                    HashMap<String, Object> diskInfo = new HashMap<>();
                    diskInfo.put("diskNo", singleHd.dwHDNo);
                    diskInfo.put("capacity", singleHd.dwCapacity);
                    diskInfo.put("freeSpace", singleHd.dwFreeSpace);
                    diskInfo.put("usedSpace", singleHd.dwCapacity - singleHd.dwFreeSpace);
                    diskInfo.put("status", singleHd.dwHdStatus);
                    diskInfo.put("statusDesc", getHdStatusDesc(singleHd.dwHdStatus));
                    diskInfo.put("attr", singleHd.byHDAttr);
                    diskInfo.put("attrDesc", getHdAttrDesc(singleHd.byHDAttr));
                    diskInfo.put("groupNo", singleHd.dwHdGroup);
                    diskList.add(diskInfo);
                }
                
                result.put("diskList", diskList);
                result.put("diskCount", hdCfg.dwHDCount);
                result.put("success", true);
                log.info("获取海康存储信息成功, deviceId:{}, diskCount:{}", deviceId, hdCfg.dwHDCount);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取硬盘配置失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康存储信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康存储信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }
    
    private String getHdStatusDesc(int status) {
        switch (status) {
            case 0: return "正常";
            case 1: return "未格式化";
            case 2: return "错误";
            case 3: return "SMART状态";
            case 4: return "不匹配";
            case 5: return "休眠";
            default: return "未知(" + status + ")";
        }
    }
    
    private String getHdAttrDesc(int attr) {
        switch (attr) {
            case 0: return "默认";
            case 1: return "冗余";
            case 2: return "只读";
            default: return "未知(" + attr + ")";
        }
    }

    @Override
    public HashMap<String, Object> getHaiKangSDCardInfo(Long deviceId) {
        log.info("开始获取海康SD卡信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_HDCFG hdCfg = new HCNetSDK.NET_DVR_HDCFG();
            hdCfg.dwSize = hdCfg.size();
            hdCfg.write();
            IntByReference lpBytesReturned = new IntByReference(0);
            
            boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_HDCFG, 0, hdCfg.getPointer(), hdCfg.size(), lpBytesReturned);
            
            if (success) {
                hdCfg.read();
                List<HashMap<String, Object>> sdCardList = new ArrayList<>();
                
                for (int i = 0; i < hdCfg.dwHDCount; i++) {
                    HCNetSDK.NET_DVR_SINGLE_HD singleHd = hdCfg.struHDInfo[i];
                    HashMap<String, Object> sdCardInfo = new HashMap<>();
                    sdCardInfo.put("cardNo", singleHd.dwHDNo);
                    sdCardInfo.put("capacity", singleHd.dwCapacity);
                    sdCardInfo.put("freeSpace", singleHd.dwFreeSpace);
                    sdCardInfo.put("usedSpace", singleHd.dwCapacity - singleHd.dwFreeSpace);
                    sdCardInfo.put("status", singleHd.dwHdStatus);
                    sdCardInfo.put("statusDesc", getHdStatusDesc(singleHd.dwHdStatus));
                    sdCardInfo.put("attr", singleHd.byHDAttr);
                    sdCardInfo.put("attrDesc", getHdAttrDesc(singleHd.byHDAttr));
                    sdCardList.add(sdCardInfo);
                }
                
                result.put("sdCardList", sdCardList);
                result.put("sdCardCount", hdCfg.dwHDCount);
                result.put("success", true);
                log.info("获取海康SD卡信息成功, deviceId:{}, cardCount:{}", deviceId, hdCfg.dwHDCount);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取SD卡配置失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康SD卡信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康SD卡信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangBitrateInfo(Long deviceId) {
        log.info("开始获取海康码率信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            int channelId = (device.getChannel() != null && device.getChannel() > 0) ? device.getChannel() : 1;
            
            log.info("使用通道: {}", channelId);
            
            HCNetSDK.NET_DVR_COMPRESSIONCFG_V30 compCfg = new HCNetSDK.NET_DVR_COMPRESSIONCFG_V30();
            compCfg.dwSize = compCfg.size();
            compCfg.write();
            IntByReference lpBytesReturned = new IntByReference(0);
            
            log.info("调用 NET_DVR_GET_COMPRESSCFG_V30, lUserID:{}, 通道:{}", lUserID, channelId);
            boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_COMPRESSCFG_V30, 
                channelId, compCfg.getPointer(), compCfg.size(), lpBytesReturned);
            
            if (success) {
                compCfg.read();
                List<HashMap<String, Object>> streamList = new ArrayList<>();
                
                streamList.add(createStreamInfo("主码流(录像)", compCfg.struNormHighRecordPara));
                streamList.add(createStreamInfo("事件码流", compCfg.struEventRecordPara));
                streamList.add(createStreamInfo("子码流(网传)", compCfg.struNetPara));
                
                result.put("streamList", streamList);
                result.put("success", true);
                log.info("获取海康码率信息成功, deviceId:{}, channelId:{}", deviceId, channelId);
                return result;
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                log.warn("获取码率配置失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
            
            result.put("message", "无法获取码率信息");
            result.put("success", true);
            result.put("streamList", new ArrayList<>());
            log.warn("无法获取码率信息, deviceId:{}", deviceId);
            
        } catch (Exception e) {
            log.error("获取海康码率信息异常", e);
            result.put("message", "异常: " + e.getMessage());
            result.put("success", true);
            result.put("streamList", new ArrayList<>());
        }
        return result;
    }
    
    private HashMap<String, Object> createStreamInfo(String streamName, HCNetSDK.NET_DVR_COMPRESSION_INFO_V30 info) {
        HashMap<String, Object> streamInfo = new HashMap<>();
        streamInfo.put("streamName", streamName);
        streamInfo.put("streamType", info.byStreamType);
        streamInfo.put("streamTypeDesc", info.byStreamType == 0 ? "视频流" : "复合流");
        streamInfo.put("resolution", info.byResolution);
        streamInfo.put("resolutionDesc", getResolutionDesc(info.byResolution));
        streamInfo.put("bitrateType", info.byBitrateType);
        streamInfo.put("bitrateTypeDesc", info.byBitrateType == 0 ? "定码率" : "变码率");
        streamInfo.put("picQuality", info.byPicQuality);
        streamInfo.put("picQualityDesc", getPicQualityDesc(info.byPicQuality));
        streamInfo.put("videoBitrate", getActualBitrate(info.dwVideoBitrate));
        streamInfo.put("videoFrameRate", info.dwVideoFrameRate);
        streamInfo.put("videoFrameRateDesc", getFrameRateDesc(info.dwVideoFrameRate));
        streamInfo.put("intervalFrameI", info.wIntervalFrameI);
        streamInfo.put("videoEncType", info.byVideoEncType);
        streamInfo.put("videoEncTypeDesc", getVideoEncTypeDesc(info.byVideoEncType));
        return streamInfo;
    }
    
    private String getResolutionDesc(int resolution) {
        switch (resolution) {
            case 0: return "DCIF";
            case 1: return "CIF";
            case 2: return "QCIF";
            case 3: return "4CIF";
            case 4: return "2CIF";
            case 16: return "VGA(640*480)";
            case 17: return "UXGA(1600*1200)";
            case 18: return "SVGA(800*600)";
            case 19: return "HD720p(1280*720)";
            case 20: return "XVGA";
            case 21: return "HD900p";
            default: return "未知(" + resolution + ")";
        }
    }
    
    private String getPicQualityDesc(int quality) {
        switch (quality) {
            case 0: return "最好";
            case 1: return "次好";
            case 2: return "较好";
            case 3: return "一般";
            case 4: return "较差";
            case 5: return "差";
            default: return "未知(" + quality + ")";
        }
    }
    
    private int getActualBitrate(int bitrateValue) {
        boolean isCustom = (bitrateValue & 0x80000000) != 0;
        if (isCustom) {
            return bitrateValue & 0x7FFFFFFF;
        }
        int[] bitrateTable = {0, 16, 32, 48, 64, 80, 96, 128, 160, 192, 224, 256, 320, 384, 448, 512, 640, 768, 896, 1024, 1280, 1536, 1792, 2048};
        if (bitrateValue >= 0 && bitrateValue < bitrateTable.length) {
            return bitrateTable[bitrateValue];
        }
        return bitrateValue;
    }
    
    private String getFrameRateDesc(int frameRate) {
        switch (frameRate) {
            case 0: return "全部";
            case 1: return "1/16 fps";
            case 2: return "1/8 fps";
            case 3: return "1/4 fps";
            case 4: return "1/2 fps";
            case 5: return "1 fps";
            case 6: return "2 fps";
            case 7: return "4 fps";
            case 8: return "6 fps";
            case 9: return "8 fps";
            case 10: return "10 fps";
            case 11: return "12 fps";
            case 12: return "16 fps";
            case 13: return "20 fps";
            case 14: return "15 fps";
            case 15: return "18 fps";
            case 16: return "22 fps";
            default: return "未知(" + frameRate + ")";
        }
    }
    
    private String getVideoEncTypeDesc(int encType) {
        switch (encType) {
            case 0: return "Hik264";
            case 1: return "标准H264";
            case 2: return "标准MPEG4";
            default: return "未知(" + encType + ")";
        }
    }

    @Override
    public HashMap<String, Object> getHaiKangNetworkStatusInfo(Long deviceId) {
        log.info("开始获取海康网络状态信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                int channelId = (device.getChannel() != null && device.getChannel() > 0) ? device.getChannel() - 1 : 0;
                
                if (channelId >= 0 && channelId < workState.struChanStatic.length) {
                    HCNetSDK.NET_DVR_CHANNELSTATE_V30 chanState = workState.struChanStatic[channelId];
                    List<HashMap<String, Object>> clientList = new ArrayList<>();
                    
                    for (int i = 0; i < chanState.dwLinkNum && i < chanState.struClientIP.length; i++) {
                        HashMap<String, Object> clientInfo = new HashMap<>();
                        HCNetSDK.NET_DVR_IPADDR ipAddr = chanState.struClientIP[i];
                        clientInfo.put("clientNo", i + 1);
                        clientInfo.put("ip", String.format("%d.%d.%d.%d", 
                            ipAddr.sIpV4[0] & 0xFF, ipAddr.sIpV4[1] & 0xFF, 
                            ipAddr.sIpV4[2] & 0xFF, ipAddr.sIpV4[3] & 0xFF));
                        clientList.add(clientInfo);
                    }
                    
                    result.put("clientList", clientList);
                    result.put("clientCount", chanState.dwLinkNum);
                    result.put("bitRate", chanState.dwBitRate);
                    result.put("allBitRate", chanState.dwAllBitRate);
                    result.put("ipLinkNum", chanState.dwIPLinkNum);
                    result.put("exceedMaxLink", chanState.byExceedMaxLink);
                    result.put("success", true);
                    log.info("获取海康网络状态信息成功, deviceId:{}", deviceId);
                } else {
                    result.put("message", "通道号超出范围");
                    result.put("success", true);
                }
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康网络状态信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康网络状态信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangSoftwareVersionInfo(Long deviceId) {
        log.info("开始获取海康软件版本信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                result.put("deviceStatic", workState.dwDeviceStatic);
                result.put("deviceStaticDesc", getDeviceStaticDesc(workState.dwDeviceStatic));
                result.put("localDisplay", workState.dwLocalDisplay);
                result.put("localDisplayDesc", workState.dwLocalDisplay == 0 ? "正常" : "不正常");
                result.put("success", true);
                log.info("获取海康软件版本信息成功, deviceId:{}", deviceId);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康软件版本信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康软件版本信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    private String getDeviceStaticDesc(int staticCode) {
        switch (staticCode) {
            case 0: return "正常";
            case 1: return "CPU占用率太高，超过85%";
            case 2: return "硬件错误，例如串口死掉";
            default: return "未知状态(" + staticCode + ")";
        }
    }

    @Override
    public HashMap<String, Object> getHaiKangRecordStateInfo(Long deviceId) {
        log.info("开始获取海康录像状态信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                List<HashMap<String, Object>> channelRecordList = new ArrayList<>();
                
                for (int i = 0; i < workState.struChanStatic.length; i++) {
                    HCNetSDK.NET_DVR_CHANNELSTATE_V30 chanState = workState.struChanStatic[i];
                    HashMap<String, Object> recordInfo = new HashMap<>();
                    recordInfo.put("channelId", i + 1);
                    recordInfo.put("isRecording", chanState.byRecordStatic == 1);
                    recordInfo.put("recordingDesc", chanState.byRecordStatic == 1 ? "录像中" : "未录像");
                    recordInfo.put("signalStatic", chanState.bySignalStatic);
                    recordInfo.put("signalDesc", chanState.bySignalStatic == 0 ? "信号正常" : "信号丢失");
                    recordInfo.put("hardwareStatic", chanState.byHardwareStatic);
                    recordInfo.put("hardwareDesc", chanState.byHardwareStatic == 0 ? "硬件正常" : "硬件异常");
                    recordInfo.put("bitRate", chanState.dwBitRate);
                    channelRecordList.add(recordInfo);
                }
                
                result.put("channelRecordList", channelRecordList);
                result.put("success", true);
                log.info("获取海康录像状态信息成功, deviceId:{}", deviceId);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康录像状态信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康录像状态信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangPowerStateInfo(Long deviceId) {
        log.info("开始获取海康电源状态信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                result.put("deviceStatic", workState.dwDeviceStatic);
                result.put("deviceStaticDesc", getDeviceStaticDesc(workState.dwDeviceStatic));
                result.put("devicePowerStatus", 0); // 海康SDK中没有直接电源状态，用0表示正常
                result.put("localDisplay", workState.dwLocalDisplay);
                result.put("success", true);
                log.info("获取海康电源状态信息成功, deviceId:{}", deviceId);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康电源状态信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康电源状态信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangAlarmArmInfo(Long deviceId) {
        log.info("开始获取海康报警布撤防信息, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                List<HashMap<String, Object>> alarmInList = new ArrayList<>();
                List<HashMap<String, Object>> alarmOutList = new ArrayList<>();
                
                for (int i = 0; i < workState.byAlarmInStatic.length; i++) {
                    HashMap<String, Object> alarmIn = new HashMap<>();
                    alarmIn.put("alarmInNo", i + 1);
                    alarmIn.put("alarmInStatus", workState.byAlarmInStatic[i] == 0 ? "无报警" : "有报警");
                    alarmInList.add(alarmIn);
                }
                
                for (int i = 0; i < workState.byAlarmOutStatic.length; i++) {
                    HashMap<String, Object> alarmOut = new HashMap<>();
                    alarmOut.put("alarmOutNo", i + 1);
                    alarmOut.put("alarmOutStatus", workState.byAlarmOutStatic[i] == 0 ? "无输出" : "有报警输出");
                    alarmOutList.add(alarmOut);
                }
                
                result.put("alarmInList", alarmInList);
                result.put("alarmOutList", alarmOutList);
                result.put("success", true);
                log.info("获取海康报警布撤防信息成功, deviceId:{}", deviceId);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康报警布撤防信息失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康报警布撤防信息异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public com.ruoyi.haikang.api.domain.HaiKangCameraInfo getHaiKangCameraInfo(Long deviceId) {
        log.info("开始获取海康摄像头属性信息, deviceId:{}", deviceId);
        com.ruoyi.haikang.api.domain.HaiKangCameraInfo result = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo();
        result.setSuccess(false);

        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                log.error("获取设备信息失败, deviceId:{}, code:{}, msg:{}", deviceId, r.getCode(), r.getMsg());
                result.setErrorMessage(r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                log.error("海康设备未登录, deviceId:{}, IP:{}", deviceId, device.getIpAddress());
                result.setErrorMessage("设备未登录");
                return result;
            }

            // 获取设备工作状态
            try {
                HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
                workState.write();
                
                boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
                
                if (success) {
                    workState.read();
                    List<com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                    
                    // 遍历通道状态
                    int channelCount = workState.struChanStatic.length;
                    for (int i = 0; i < channelCount; i++) {
                        HCNetSDK.NET_DVR_CHANNELSTATE_V30 chanState = workState.struChanStatic[i];
                        
                        com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo info = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo();
                        info.setChannelId(i + 1); // 通道从1开始
                        info.setCameraName("通道" + (i + 1));
                        info.setCameraType("本地");
                        
                        // 信号状态: 0-正常, 1-信号丢失
                        boolean online = chanState.bySignalStatic == 0;
                        info.setOnline(online);
                        
                        // 状态描述
                        String statusDesc;
                        if (chanState.byRecordStatic == 1) {
                            statusDesc = "录像中";
                        } else if (chanState.bySignalStatic == 1) {
                            statusDesc = "信号丢失";
                        } else {
                            statusDesc = "正常";
                        }
                        info.setStatusDesc(statusDesc);
                        
                        // 如果设备有通道号，则匹配
                        if (device.getChannel() != null && device.getChannel() == (i + 1)) {
                            info.setCameraName(device.getDeviceName());
                        }
                        
                        cameraList.add(info);
                    }
                    
                    result.setCameraCount(cameraList.size());
                    result.setCameraList(cameraList);
                    log.info("获取海康摄像头属性成功, 共 {} 个通道", cameraList.size());
                } else {
                    log.warn("获取设备工作状态失败, 错误码: {}", client.hCNetSDK.NET_DVR_GetLastError());
                    // 如果获取工作状态失败，尝试用 V30 之前的版本
                    try {
                        HCNetSDK.NET_DVR_WORKSTATE workStateOld = new HCNetSDK.NET_DVR_WORKSTATE();
                        workStateOld.write();
                        boolean successOld = client.hCNetSDK.NET_DVR_GetDVRWorkState(lUserID, workStateOld);
                        
                        if (successOld) {
                            workStateOld.read();
                            List<com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                            int channelCount = workStateOld.struChanStatic.length;
                            for (int i = 0; i < channelCount; i++) {
                                HCNetSDK.NET_DVR_CHANNELSTATE chanState = workStateOld.struChanStatic[i];
                                
                                com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo info = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo();
                                info.setChannelId(i + 1);
                                info.setCameraName("通道" + (i + 1));
                                info.setCameraType("本地");
                                
                                boolean online = chanState.bySignalStatic == 0;
                                info.setOnline(online);
                                
                                String statusDesc;
                                if (chanState.byRecordStatic == 1) {
                                    statusDesc = "录像中";
                                } else if (chanState.bySignalStatic == 1) {
                                    statusDesc = "信号丢失";
                                } else {
                                    statusDesc = "正常";
                                }
                                info.setStatusDesc(statusDesc);
                                
                                if (device.getChannel() != null && device.getChannel() == (i + 1)) {
                                    info.setCameraName(device.getDeviceName());
                                }
                                
                                cameraList.add(info);
                            }
                            
                            result.setCameraCount(cameraList.size());
                            result.setCameraList(cameraList);
                            log.info("获取海康摄像头属性成功(旧版), 共 {} 个通道", cameraList.size());
                        } else {
                            log.warn("获取旧版设备工作状态也失败, 错误码: {}", client.hCNetSDK.NET_DVR_GetLastError());
                            // 都失败的话，至少返回默认通道
                            List<com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                            com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo info = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo();
                            info.setChannelId(device.getChannel() != null && device.getChannel() > 0 ? device.getChannel() : 1);
                            info.setCameraName(device.getDeviceName());
                            info.setCameraType("本地");
                            info.setOnline(true);
                            info.setStatusDesc("正常");
                            cameraList.add(info);
                            result.setCameraCount(cameraList.size());
                            result.setCameraList(cameraList);
                        }
                    } catch (Exception e2) {
                        log.warn("获取旧版设备工作状态异常: {}", e2.getMessage());
                        // 异常时至少返回默认通道
                        List<com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                        com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo info = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo();
                        info.setChannelId(device.getChannel() != null && device.getChannel() > 0 ? device.getChannel() : 1);
                        info.setCameraName(device.getDeviceName());
                        info.setCameraType("本地");
                        info.setOnline(true);
                        info.setStatusDesc("正常");
                        cameraList.add(info);
                        result.setCameraCount(cameraList.size());
                        result.setCameraList(cameraList);
                    }
                }
            } catch (Exception e) {
                log.warn("获取设备工作状态异常: {}", e.getMessage());
                // 异常时至少返回默认通道
                List<com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo> cameraList = new ArrayList<>();
                com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo info = new com.ruoyi.haikang.api.domain.HaiKangCameraInfo.CameraInfo();
                info.setChannelId(device.getChannel() != null && device.getChannel() > 0 ? device.getChannel() : 1);
                info.setCameraName(device.getDeviceName());
                info.setCameraType("本地");
                info.setOnline(true);
                info.setStatusDesc("正常");
                cameraList.add(info);
                result.setCameraCount(cameraList.size());
                result.setCameraList(cameraList);
            }

            result.setSuccess(true);
            log.info("获取海康摄像头属性信息完成, deviceId:{}", deviceId);
        } catch (Exception e) {
            log.error("获取海康摄像头属性异常, deviceId:{}, error:{}", deviceId, e.getMessage(), e);
            result.setErrorMessage(e.getMessage());
            result.setSuccess(true); // 尽量返回成功，即使有异常
        }

        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangRtspUrlInfo(Long deviceId) {
        log.info("开始获取海康RTSP URL, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            int channelId = (device.getChannel() != null && device.getChannel() > 0) ? device.getChannel() : 1;
            String rtspUrl = String.format("rtsp://%s:%s@%s:554/Streaming/Channels/%02d01", 
                device.getUserName(), device.getPassword(), device.getIpAddress(), channelId);
            result.put("rtspUrl", rtspUrl);
            result.put("success", true);
            log.info("获取海康RTSP URL成功, deviceId:{}, channelId:{}, rtspUrl:{}", deviceId, channelId, rtspUrl);
        } catch (Exception e) {
            log.error("获取海康RTSP URL异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getHaiKangSystemParam(Long deviceId) {
        log.info("开始获取海康系统参数, deviceId:{}", deviceId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            HCNetSDK.NET_DVR_WORKSTATE_V30 workState = new HCNetSDK.NET_DVR_WORKSTATE_V30();
            boolean success = client.hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, workState);
            
            if (success) {
                workState.read();
                result.put("deviceStatic", workState.dwDeviceStatic);
                result.put("deviceStaticDesc", getDeviceStaticDesc(workState.dwDeviceStatic));
                result.put("localDisplay", workState.dwLocalDisplay);
                result.put("localDisplayDesc", workState.dwLocalDisplay == 0 ? "正常" : "不正常");
                result.put("audioChanStatus", byteArrayToIntList(workState.byAudioChanStatus));
                
                List<HashMap<String, Object>> diskList = new ArrayList<>();
                for (int i = 0; i < workState.struHardDiskStatic.length; i++) {
                    HCNetSDK.NET_DVR_DISKSTATE disk = workState.struHardDiskStatic[i];
                    HashMap<String, Object> diskInfo = new HashMap<>();
                    diskInfo.put("diskNo", i + 1);
                    diskInfo.put("volume", disk.dwVolume);
                    diskInfo.put("freeSpace", disk.dwFreeSpace);
                    diskInfo.put("hardDiskStatic", disk.dwHardDiskStatic);
                    diskInfo.put("diskStaticDesc", getDiskStaticDesc(disk.dwHardDiskStatic));
                    diskList.add(diskInfo);
                }
                result.put("diskList", diskList);
                
                result.put("success", true);
                log.info("获取海康系统参数成功, deviceId:{}", deviceId);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取工作状态失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康系统参数失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康系统参数异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    private List<Integer> byteArrayToIntList(byte[] byteArray) {
        List<Integer> intList = new ArrayList<>();
        for (byte b : byteArray) {
            intList.add((int) b);
        }
        return intList;
    }

    private String getDiskStaticDesc(int staticCode) {
        if ((staticCode & 1) != 0) {
            return "休眠";
        } else if ((staticCode & 2) != 0) {
            return "不正常";
        } else if ((staticCode & 4) != 0) {
            return "休眠硬盘出错";
        } else {
            return "正常";
        }
    }

    @Override
    public HashMap<String, Object> getHaiKangVideoParam(Long deviceId, int channelId, String streamType) {
        log.info("开始获取海康视频参数, deviceId:{}, channelId:{}, streamType:{}", deviceId, channelId, streamType);
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(deviceId, SecurityConstants.INNER);
            if (R.isError(r)) {
                result.put("message", "获取设备信息失败: " + r.getMsg());
                return result;
            }
            QsDevice device = r.getData();
            Integer lUserID = userIdMap.get(device.getIpAddress());
            if (lUserID == null || lUserID < 0) {
                result.put("message", "设备未登录");
                return result;
            }

            int actualChannelId = (channelId > 0) ? channelId - 1 : 0;
            
            HCNetSDK.NET_DVR_COMPRESSIONCFG_V30 compCfg = new HCNetSDK.NET_DVR_COMPRESSIONCFG_V30();
            compCfg.dwSize = compCfg.size();
            compCfg.write();
            IntByReference lpBytesReturned = new IntByReference(0);
            
            boolean success = client.hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_COMPRESSCFG_V30, actualChannelId, compCfg.getPointer(), compCfg.size(), lpBytesReturned);
            
            if (success) {
                compCfg.read();
                HCNetSDK.NET_DVR_COMPRESSION_INFO_V30 selectedStream;
                
                if ("sub".equalsIgnoreCase(streamType)) {
                    selectedStream = compCfg.struNetPara;
                    result.put("streamType", "子码流");
                } else if ("event".equalsIgnoreCase(streamType)) {
                    selectedStream = compCfg.struEventRecordPara;
                    result.put("streamType", "事件码流");
                } else {
                    selectedStream = compCfg.struNormHighRecordPara;
                    result.put("streamType", "主码流");
                }
                
                result.put("channelId", channelId);
                result.put("videoEncType", selectedStream.byVideoEncType);
                result.put("videoEncTypeDesc", getVideoEncTypeDesc(selectedStream.byVideoEncType));
                result.put("resolution", selectedStream.byResolution);
                result.put("resolutionDesc", getResolutionDesc(selectedStream.byResolution));
                result.put("bitrateType", selectedStream.byBitrateType);
                result.put("bitrateTypeDesc", selectedStream.byBitrateType == 0 ? "定码率" : "变码率");
                result.put("picQuality", selectedStream.byPicQuality);
                result.put("picQualityDesc", getPicQualityDesc(selectedStream.byPicQuality));
                result.put("videoBitrate", getActualBitrate(selectedStream.dwVideoBitrate));
                result.put("videoFrameRate", selectedStream.dwVideoFrameRate);
                result.put("videoFrameRateDesc", getFrameRateDesc(selectedStream.dwVideoFrameRate));
                result.put("intervalFrameI", selectedStream.wIntervalFrameI);
                result.put("streamTypeSetting", selectedStream.byStreamType);
                result.put("streamTypeSettingDesc", selectedStream.byStreamType == 0 ? "视频流" : "复合流");
                result.put("audioEncType", selectedStream.byAudioEncType);
                
                result.put("success", true);
                log.info("获取海康视频参数成功, deviceId:{}, channelId:{}, streamType:{}", deviceId, channelId, streamType);
            } else {
                int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
                result.put("message", "获取压缩配置失败, 错误码: " + errorCode);
                result.put("success", true);
                log.warn("获取海康视频参数失败, deviceId:{}, errorCode:{}", deviceId, errorCode);
            }
        } catch (Exception e) {
            log.error("获取海康视频参数异常", e);
            result.put("message", "异常: " + e.getMessage());
        }
        return result;
    }

    // 生成文件名
    private String generateFileName(Long deviceId, int channelId) {
        String timeStr = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return "haikang_" + deviceId + "_" + channelId + "_" + timeStr + ".jpg";
    }

    /**
     * 报警布防句柄映射
     */
    private static ConcurrentHashMap<String, Integer> alarmHandleMap = new ConcurrentHashMap<>();

    @Override
    public int setupAlarm(String ip) {
        log.info("开始报警布防, IP:{}", ip);
        Integer lUserID = userIdMap.get(ip);
        if (lUserID == null || lUserID < 0) {
            log.error("设备未登录, IP:{}", ip);
            return -1;
        }

        // 检查是否已布防
        if (alarmHandleMap.containsKey(ip)) {
            log.info("设备已布防, IP:{}, 句柄:{}", ip, alarmHandleMap.get(ip));
            return alarmHandleMap.get(ip);
        }

        // 使用 NET_DVR_SetupAlarmChan_V41 进行布防
        HCNetSDK.NET_DVR_SETUPALARM_PARAM setupAlarmParam = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        setupAlarmParam.dwSize = setupAlarmParam.size();
        setupAlarmParam.byLevel = 1; // 布防优先级，0-低，1-中，2-高
        setupAlarmParam.byAlarmInfoType = 1; // 报警信息类型，0-老报警信息，1-新报警信息
        setupAlarmParam.byFaceAlarmDetection = 0; // 人脸检测报警，0-不订阅，1-订阅
        setupAlarmParam.write();

        int lAlarmHandle = client.hCNetSDK.NET_DVR_SetupAlarmChan_V41(lUserID, setupAlarmParam);
        if (lAlarmHandle < 0) {
            int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
            log.error("报警布防失败, IP:{}, 错误码:{}, 错误信息:{}", ip, errorCode, getErrorMessage(errorCode));
            return -1;
        }

        alarmHandleMap.put(ip, lAlarmHandle);
        log.info("报警布防成功, IP:{}, 句柄:{}", ip, lAlarmHandle);
        return lAlarmHandle;
    }

    @Override
    public boolean closeAlarm(String ip) {
        log.info("开始报警撤防, IP:{}", ip);
        Integer lAlarmHandle = alarmHandleMap.get(ip);
        if (lAlarmHandle == null || lAlarmHandle < 0) {
            log.warn("设备未布防, IP:{}", ip);
            return true;
        }

        boolean success = client.hCNetSDK.NET_DVR_CloseAlarmChan_V30(lAlarmHandle);
        if (success) {
            alarmHandleMap.remove(ip);
            log.info("报警撤防成功, IP:{}", ip);
        } else {
            int errorCode = client.hCNetSDK.NET_DVR_GetLastError();
            log.error("报警撤防失败, IP:{}, 错误码:{}, 错误信息:{}", ip, errorCode, getErrorMessage(errorCode));
        }

        return success;
    }

    /**
     * 获取所有布防的设备
     */
    public static ConcurrentHashMap<String, Integer> getAlarmHandleMap() {
        return alarmHandleMap;
    }

}
