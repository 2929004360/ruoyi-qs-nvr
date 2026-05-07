package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.RtpServerParam;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.common.ErrorCode;
import com.ruoyi.dahua.common.Res;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.manager.StreamManager;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;
import com.ruoyi.dahua.service.IDahuaMediaStreamService;
import com.ruoyi.qs.api.RemoteQsDeviceService;
import com.ruoyi.qs.api.domain.QsDevice;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
        if (r.getCode() != Constants.SUCCESS) {
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
}
