package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.common.ErrorCode;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.manager.StreamManager;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;
import com.ruoyi.dahua.service.IDahuaMediaStreamService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    public static final NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;

    public static final Map<String, NetSDKLib.LLong> loginHandleHandleMap = new ConcurrentHashMap<>();

    private final Map<String, NetSDKLib.NET_DEVICEINFO_Ex> deviceInfoMap = new ConcurrentHashMap<>();

    /**
     * 大华设备登录
     *
     * @param m_strIp       设备IP
     * @param m_nPort       设备端口
     * @param m_strUser     设备用户名
     * @param m_strPassword  设备密码
     * @param deviceId      设备ID
     * @param onlineType    上线类型(1=主动添加, 2=主动注册)
     */
    @Override
    public NetSDKLib.LLong loginDevice(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceId, String onlineType) {
        String loginKey = "login:handle:" + m_strIp;
        
        NetSDKLib.LLong existingHandle = loginHandleHandleMap.get(loginKey);
        if (existingHandle != null && existingHandle.longValue() != 0) {
            log.warn("设备已登录，返回现有登录句柄, IP:{}, deviceId:{}", m_strIp, deviceId);
            return existingHandle;
        }

        NetSDKLib.LLong m_hLoginHandle;
        try {
            if ("2".equals(onlineType)) {
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
            } else {
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
        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + ip);
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            throw new RuntimeException("大华设备未登录, IP:" + ip);
        }
        NetSDKLib.NET_TIME deviceTime = new NetSDKLib.NET_TIME();

        if (!netsdk.CLIENT_QueryDeviceTime(m_hLoginHandle, deviceTime, 3000)) {
            String errorMsg = "客户端查询设备时间失败, " + ToolKits.getErrorCodePrint();
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        String date = deviceTime.toStringTime();
        if (date == null) {
            throw new ServiceException("获取大华设备时间失败");
        }
        date = date.replace("/", "-");
        log.debug("获取大华设备时间成功, IP:{}, time:{}", ip, date);
        return date;
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备IP
     */
    @Override
    public DahuaDevice getDahuaDevice(String ip) {
        DahuaDevice dahuaDevice = DahuaCommandLineRunnerImpl.deviceMap.get(ip);
        if (dahuaDevice == null) {
            log.warn("大华设备未注册, IP:{}", ip);
            throw new RuntimeException("大华设备未注册");
        }
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
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(rtpServerParam.getId(), SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();

        String streamKey = "dahua:play:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            throw new RuntimeException("大华设备未登录, IP:" + device.getIpAddress());
        }
        log.info("开始播放大华设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.startPlay(lLong, device, streamKey, rtpServerParam);
    }

    /**
     * 停止播放
     *
     * @param id 设备ID
     */
    @Override
    public void stopPlay(Long id) {
        R<QsDevice> r = remoteQsDeviceService.getQsDeviceInfo(id, SecurityConstants.INNER);
        if (r.getCode() != Constants.SUCCESS) {
            throw new SecurityException(r.getMsg());
        }
        QsDevice device = r.getData();
        String streamKey = "dahua:play:" + device.getId() + ":" + device.getChannel();

        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + device.getIpAddress());

        if (lLong == null || lLong.longValue() == 0) {
            log.warn("大华设备未登录，无法停止播放, IP:{}", device.getIpAddress());
            return;
        }
        log.info("停止播放大华设备流, deviceId:{}, channel:{}, streamKey:{}", device.getId(), device.getChannel(), streamKey);
        mediaStreamService.stopPlay(lLong, device.getId(), device.getChannel(), streamKey);
    }

    /**
     * 获取接口错误码和错误信息，用于打印
     *
     * @return
     */
    public static String getErrorCodePrint() {
        return "error code: (0x80000000|" + (netsdk.CLIENT_GetLastError() & 0x7fffffff) + "), error info:" + ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError());
    }
}
