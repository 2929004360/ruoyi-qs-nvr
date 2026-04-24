package com.ruoyi.haikang.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.haikang.api.domain.HaikangDeviceInfo;
import com.ruoyi.haikang.callback.FRealDataForRtpOverTcpCallback;
import com.ruoyi.haikang.manager.StreamManager;
import com.ruoyi.haikang.net.Client;
import com.ruoyi.haikang.net.HCNetSDK;
import com.ruoyi.haikang.service.IHaiKangService;
import com.ruoyi.haikang.service.IHaikangMediaStreamService;
import com.ruoyi.haikang.utils.CommonUtil;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 设备信息
     */
    public static ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40> deviceInfoMap = new ConcurrentHashMap<String, HCNetSDK.NET_DVR_DEVICEINFO_V40>();

    /**
     * 海康登录用户ID
     */
    public static ConcurrentHashMap<String, Integer> userIdMap = new ConcurrentHashMap<String, Integer>();

    @Autowired
    private RemoteQsDeviceService remoteQsDeviceService;

    @Autowired
    private IHaikangMediaStreamService mediaStreamService;

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
            log.info(ip + " 设备登录成功！");
        }

        userIdMap.put(ip, userId);
        deviceInfoMap.put(ip, deviceInfo);
        return userId;
    }

    /**
     * 设备注销
     *
     * @param ip 设备ip
     */
    public void logoutDevice(String ip) {
        if (ObjectUtil.isNull(ip)) {
            throw new ServiceException("设备id不能为空");
        }
        Integer userId = getUserId(ip);
        if (userId != null && userId >= 0) {
            try {
                log.info("开始登出海康设备, IP:{}", ip);
                
                // 清理设备相关的流媒体资源
                cleanDeviceStreamResources(ip);
                
                if (!client.hCNetSDK.NET_DVR_Logout(userId)) {
                    log.error("注销失败，错误码为" + client.hCNetSDK.NET_DVR_GetLastError());
                } else {
                    log.info("注销成功");
                }
            } catch (Exception e) {
                log.error("海康设备登出异常, IP:{}", ip, e);
            } finally {
                userIdMap.remove(ip);
                deviceInfoMap.remove(ip);
            }
        } else {
            log.error("设备未登录");
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
        if (ObjectUtil.isNull(ip)) {
            throw new ServiceException("设备id不能为空");
        }

        return userIdMap.get(ip);
    }

    /**
     * 获取设备的基本参数
     *
     * @param ip 设备ip
     * @return
     */
    @Override
    public HaikangDeviceInfo getDeviceInfo(String ip) {
        Integer iUserID = getUserId(ip);
        if (iUserID == null || iUserID < 0) {
            throw new ServiceException("设备未登录, IP:" + ip);
        }
        
        HaikangDeviceInfo deviceInfo = new HaikangDeviceInfo();
        HCNetSDK.NET_DVR_DEVICECFG_V40 m_strDeviceCfg = new HCNetSDK.NET_DVR_DEVICECFG_V40();
        m_strDeviceCfg.dwSize = m_strDeviceCfg.size();
        m_strDeviceCfg.write();
        Pointer pStrDeviceCfg = m_strDeviceCfg.getPointer();
        IntByReference pInt = new IntByReference(0);
        boolean b_GetCfg = client.hCNetSDK.NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_DEVICECFG_V40, 0Xffffffff, pStrDeviceCfg, m_strDeviceCfg.dwSize, pInt);
        if (!b_GetCfg) {
            log.error("获取参数失败  错误码：" + client.hCNetSDK.NET_DVR_GetLastError());
        }
        m_strDeviceCfg.read();
        parseVersion(m_strDeviceCfg.dwSoftwareVersion, deviceInfo);
        parseBuildTime(m_strDeviceCfg.dwSoftwareBuildDate, deviceInfo);
        parseDSPBuildDate(m_strDeviceCfg.dwDSPSoftwareBuildDate, deviceInfo);

        deviceInfo.setDeviceName(CommonUtil.parseHikvisionString(m_strDeviceCfg.sDVRName));
        deviceInfo.setDeviceSerial(CommonUtil.parseHikvisionString(m_strDeviceCfg.sSerialNumber));
        deviceInfo.setByChanNum(m_strDeviceCfg.byChanNum);

        return deviceInfo;
    }

    /**
     * 开始播放
     *
     * @param rtpServerParam
     */
    @Override
    public void startPlay(RtpServerParam rtpServerParam) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();

        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            throw new ServiceException("海康设备未登录, IP:" + device.getIpAddress());
        }

        log.info("开始播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.startPlay(lUserID, device, streamKey,rtpServerParam);
    }

    /**
     * 停止播放
     *
     * @param id 设备id
     */
    @Override
    public void stopPlay(Long id) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        String streamKey = "haikang_play_" + device.getId() + "_" + device.getChannel();

        Integer lUserID = userIdMap.get(device.getIpAddress());
        if (lUserID == null || lUserID < 0) {
            log.warn("海康设备未登录，无法停止播放, IP:{}", device.getIpAddress());
            return;
        }

        log.info("停止播放海康设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.endPlay(device.getId(), device.getChannel(), streamKey);
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
}
