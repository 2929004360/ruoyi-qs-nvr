package com.ruoyi.dahua.runner;

import com.ruoyi.dahua.config.DahuaConfig;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开启大华主动注册监听
 *
 * @FileName DahuaCommandLineRunnerImpl
 * @Description
 * @Author fengcheng
 * @date 2025-06-07
 **/
@Slf4j
@Component
public class DahuaCommandLineRunnerImpl implements CommandLineRunner, DisposableBean {

    @Autowired
    private DahuaConfig dahuaConfig;

    /**
     * 设备断线通知回调
     */
    private static DisConnect disConnect = new DisConnect();

    /**
     * 网络连接恢复
     */
    private static HaveReConnect haveReConnect = new HaveReConnect();

    public static NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;

    private NetSDKLib.LLong mServerHandler = new NetSDKLib.LLong(0);

    private boolean bInit = false;

    public static Map<String, DahuaDevice> deviceMap = new HashMap<>();

    /**
     * 侦听服务器回调函数
     */
    private ServiceCB servicCallback = new ServiceCB();

    @Override
    public void run(String... args) throws Exception {
        log.info("=========================  开启大华sdk服务  =========================");
        // 打开工程，初始化
        init(disConnect, haveReConnect);
        if (dahuaConfig.isPublicNetwork()) {
            InetAddress address = InetAddress.getLocalHost();
            mServerHandler = startServer(address.getHostAddress(), dahuaConfig.getPort(), servicCallback);
        } else {
            mServerHandler = startServer(dahuaConfig.getIp(), dahuaConfig.getPort(), servicCallback);
        }
    }

    @Override
    public void destroy() {
        log.info("=========================  停止大华sdk服务  =========================");
        stopServer();
    }


    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private static class DisConnect implements NetSDKLib.fDisConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            log.info("Device[{}] Port[{}] DisConnect!\n", pchDVRIP, nDVRPort);
            System.out.println("设备断线");
        }
    }

    // 网络连接恢复，设备重连成功回调
    // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            log.info("ReConnect Device[{}] Port[{}]\n", pchDVRIP, nDVRPort);
            System.out.println("网络连接恢复，设备重连成功");
        }
    }

    /**
     * 开启服务
     *
     * @param address  本地IP地址
     * @param port     本地端口, 可以任意
     * @param callback 回调函数
     */
    public static NetSDKLib.LLong startServer(String address, int port, NetSDKLib.fServiceCallBack callback) {
        NetSDKLib.LLong mServerHandler = netsdk.CLIENT_ListenServer(address, port, 1000, callback, null);
        if (0 == mServerHandler.longValue()) {
            log.error("启动服务器失败." + ToolKits.getErrorCodePrint());
        } else {
            log.info("启动服务器, [服务器地址 {}][服务器端口 {}]\n", address, port);
        }
        return mServerHandler;
    }

    /**
     * \if ENGLISH_LANG
     * Init
     * \else
     * 初始化
     * \endif
     */
    public boolean init(NetSDKLib.fDisConnect disConnect, NetSDKLib.fHaveReConnect haveReConnect) {
        bInit = netsdk.CLIENT_Init(disConnect, null);
        if (!bInit) {
            System.out.println("SDK初始化失败");
            return false;
        }
        // 设置断线重连回调接口，设置过断线重连成功回调函数后，当设备出现断线情况，SDK内部会自动进行重连操作
        // 此操作为可选操作，但建议用户进行设置
        netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);

        //设置登录超时时间和尝试次数，可选
        int waitTime = 5000; //登录请求响应超时时间设置为5S
        int tryTimes = 1;    //登录时尝试建立链接1次
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

        // 设置更多网络参数，NET_PARAM的nWaittime，nConnectTryNum成员与CLIENT_SetConnectTime
        // 接口设置的登录设备超时时间和尝试次数意义相同,可选
        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        netParam.nConnectTime = 10000;      // 登录时尝试建立链接的超时时间
        netParam.nGetConnInfoTime = 3000;   // 设置子连接的超时时间
        netParam.nGetDevInfoTime = 3000;//获取设备信息超时时间，为0默认1000ms
        netsdk.CLIENT_SetNetworkParam(netParam);

        return true;
    }

    /**
     * \if ENGLISH_LANG
     * CleanUp
     * \else
     * 清除环境
     * \endif
     */
    public void cleanup() {
        if (bInit) {
            netsdk.CLIENT_Cleanup();
        }
    }

    /**
     * 结束服务
     */
    public boolean stopServer() {
        boolean bRet = false;
        if (mServerHandler.longValue() != 0) {
            bRet = netsdk.CLIENT_StopListenServer(mServerHandler);
            mServerHandler.setValue(0);
            log.info("停止成功!");
            cleanup();
        }
        return bRet;
    }

    /**
     * 侦听服务器回调函数
     */
    public class ServiceCB implements NetSDKLib.fServiceCallBack {
        @Override
        public int invoke(NetSDKLib.LLong lHandle, final String pIp, final int wPort, int lCommand, Pointer pParam, int dwParamLen, Pointer dwUserData) {
            // 将 pParam 转化为序列号
            byte[] buffer = new byte[dwParamLen];
            pParam.read(0, buffer, 0, dwParamLen);
            String deviceId = "";
            try {
                deviceId = new String(buffer, "GBK").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            addOrUpdateDevice(deviceMap, pIp, deviceId, wPort);
            return 0;
        }
    }

    /**
     * 添加或更新 设备
     *
     * @param deviceMap
     * @param ip
     * @param deviceId
     * @param port
     */
    public static void addOrUpdateDevice(Map<String, DahuaDevice> deviceMap, String ip, String deviceId, int port) {
        DahuaDevice device = new DahuaDevice();
        device.setIp(ip);
        device.setDeviceId(deviceId);
        device.setPort(String.valueOf(port));
        deviceMap.put(ip, device);
    }

    /**
     * 获取设备列表
     *
     * @return
     */
    public List<DahuaDevice> getRegisterDeviceList() {
        // 最终转成 list
        List<DahuaDevice> deviceList = new ArrayList<>(deviceMap.values());
        return deviceList;
    }

    /**
     * 删除设备
     *
     * @return
     */
    public void delRegisterDevice(String[] ips) {
        for (String ip : ips) {
            deviceMap.remove(ip);
        }
    }
}
