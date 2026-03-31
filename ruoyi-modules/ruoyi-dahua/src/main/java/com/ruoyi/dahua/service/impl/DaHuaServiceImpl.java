package com.ruoyi.dahua.service.impl;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.common.ErrorCode;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.module.LoginModule;
import com.ruoyi.dahua.runner.DahuaCommandLineRunnerImpl;
import com.ruoyi.dahua.service.IDaHuaService;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
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

    public static NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;

    public static Map<String, NetSDKLib.LLong> loginHandleHandleMap = new ConcurrentHashMap();

    // 设备信息
    private final Map<String, NetSDKLib.NET_DEVICEINFO_Ex> deviceInfoMap = new ConcurrentHashMap<>();

    /**
     * 大华设备登录
     *
     * @param m_strIp       设备ip
     * @param m_nPort       设备端口
     * @param m_strUser     设备用户名
     * @param m_strPassword 设备密码
     * @param deviceId      设备id
     * @param onlineType    上线类型(1=主动添加, 2=主动注册)
     */
    @Override
    public NetSDKLib.LLong loginDevice(String m_strIp, int m_nPort, String m_strUser, String m_strPassword, String deviceId, String onlineType) {
        if ("2".equals(onlineType)) {
            final int tcpSpecCap = 2;// 主动注册方式
            final IntByReference errorReference = new IntByReference(0);
            final NetSDKLib.NET_DEVICEINFO_Ex deviceinfo = new NetSDKLib.NET_DEVICEINFO_Ex();

            // 将 序列号 转化为 pointer 类型
            com.ruoyi.dahua.lib.NativeString serial = new com.ruoyi.dahua.lib.NativeString(deviceId);
            NetSDKLib.LLong m_hLoginHandle = netsdk.CLIENT_LoginEx2(m_strIp, m_nPort, m_strUser, m_strPassword, tcpSpecCap, serial.getPointer(), deviceinfo, errorReference);
            if (0 == m_hLoginHandle.longValue()) {
                System.err.printf("大华设备登录[%s] 端口[%d]失败. %s\n", m_strIp, m_nPort, getErrorCodePrint());
                throw new RuntimeException(ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError()));
            }
            loginHandleHandleMap.put("login:handle:" + m_strIp, m_hLoginHandle);
            deviceInfoMap.put("device:info:" + m_strIp, deviceinfo);
            return m_hLoginHandle;
        } else {
            //入参
            NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY pstInParam = new NetSDKLib.NET_IN_LOGIN_WITH_HIGHLEVEL_SECURITY();
            pstInParam.nPort = m_nPort;
            pstInParam.szIP = m_strIp.getBytes();
            pstInParam.szPassword = m_strPassword.getBytes();
            pstInParam.szUserName = m_strUser.getBytes();
            //出参
            NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY pstOutParam = new NetSDKLib.NET_OUT_LOGIN_WITH_HIGHLEVEL_SECURITY();
            NetSDKLib.NET_DEVICEINFO_Ex m_stDeviceInfo = new NetSDKLib.NET_DEVICEINFO_Ex();
            pstOutParam.stuDeviceInfo = m_stDeviceInfo;

            NetSDKLib.LLong m_hLoginHandle = netsdk.CLIENT_LoginWithHighLevelSecurity(pstInParam, pstOutParam);
            if (m_hLoginHandle.longValue() == 0) {
                System.err.printf("大华设备登录[%s] 端口[%d]失败. %s\n", m_strIp, m_nPort, getErrorCodePrint());
                throw new RuntimeException(ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError()));
            }
            loginHandleHandleMap.put("login:handle:" + m_strIp, m_hLoginHandle);
            deviceInfoMap.put("device:info:" + m_strIp, pstOutParam.stuDeviceInfo);
            return m_hLoginHandle;
        }
    }

    /**
     * 查询是否登录
     *
     * @param ip 设备ip
     */
    @Override
    public Boolean isUserId(String ip) {
        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + ip);
        if (lLong == null || lLong.longValue() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 大华设备获取时间
     *
     * @param ip 设备ip
     * @return
     */
    @Override
    public String getTime(String ip) {
        NetSDKLib.LLong m_hLoginHandle = loginHandleHandleMap.get("login:handle:" + ip);
        if (m_hLoginHandle == null || m_hLoginHandle.longValue() == 0) {
            throw new RuntimeException("大华设备未登录");
        }
        NetSDKLib.NET_TIME deviceTime = new NetSDKLib.NET_TIME();

        if (!netsdk.CLIENT_QueryDeviceTime(m_hLoginHandle, deviceTime, 3000)) {
            System.err.println("客户端查询设备时间失败!" + ToolKits.getErrorCodePrint());
            throw new RuntimeException("客户端查询设备时间失败");
        }

        String date = deviceTime.toStringTime();
        date = date.replace("/", "-");

        if (date == null) {
            throw new ServiceException("获取大华设备时间失败");
        }
        return date;
    }

    /**
     * 获取大华主动上线设备
     *
     * @param ip 设备ip
     */
    @Override
    public DahuaDevice getDahuaDevice(String ip) {
        DahuaDevice dahuaDevice = DahuaCommandLineRunnerImpl.deviceMap.get(ip);
        if (dahuaDevice == null) {
            throw new RuntimeException("大华设备未注册");
        }
        return dahuaDevice;
    }

    /**
     * 大华设备登出
     *
     * @param ip 设备ip
     * @return
     */
    @Override
    public Boolean logoutDevice(String ip) {
        NetSDKLib.LLong lLong = loginHandleHandleMap.get("login:handle:" + ip);
        if (lLong == null || lLong.longValue() == 0) {
            throw new RuntimeException("大华设备未登录");
        }
        boolean bRet = false;
        if (lLong.longValue() != 0) {
            bRet = LoginModule.netsdk.CLIENT_Logout(lLong);
            lLong.setValue(0);
        }

        return bRet;
    }

    /**
     * 获取接口错误码和错误信息，用于打印
     *
     * @return
     */
    public static String getErrorCodePrint() {
        return "\n{error code: (0x80000000|" + (netsdk.CLIENT_GetLastError() & 0x7fffffff) + ").参考  NetSDKLib.java }" + " - {error info:" + ErrorCode.getErrorCode(netsdk.CLIENT_GetLastError()) + "}\n";
    }
}
