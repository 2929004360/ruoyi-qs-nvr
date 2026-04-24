package com.ruoyi.dahua.runner;

import com.ruoyi.dahua.config.DahuaConfig;
import com.ruoyi.dahua.api.domain.DahuaDevice;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.ToolKits;
import com.ruoyi.dahua.service.IDaHuaService;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    private IDaHuaService daHuaService;

    /**
     * 设备断线通知回调
     */
    private static final DisConnect disConnect = new DisConnect();

    /**
     * 网络连接恢复
     */
    private static final HaveReConnect haveReConnect = new HaveReConnect();

    private static final NetSDKLib netsdk = NetSDKLib.NETSDK_INSTANCE;

    private NetSDKLib.LLong mServerHandler = new NetSDKLib.LLong(0);

    private volatile boolean bInit = false;

    public static final Map<String, DahuaDevice> deviceMap = new ConcurrentHashMap<>();

    /**
     * 侦听服务器回调函数
     */
    private final ServiceCB servicCallback = new ServiceCB();

    @Override
    public void run(String... args) {
        log.info("=========================  开启大华sdk服务  =========================");
        try {
            init(disConnect, haveReConnect);
            String serverIp;
            if (dahuaConfig.isPublicNetwork()) {
                InetAddress address = InetAddress.getLocalHost();
                serverIp = address.getHostAddress();
            } else {
                serverIp = dahuaConfig.getIp();
            }
            mServerHandler = startServer(serverIp, dahuaConfig.getPort(), servicCallback);
        } catch (Exception e) {
            log.error("启动大华SDK服务失败", e);
        }
    }

    @Override
    public void destroy() {
        log.info("=========================  停止大华sdk服务  =========================");
        // 登出所有设备
        try {
            log.info("开始登出所有大华设备...");
            // 从登录句柄 Map 获取所有 IP
            com.ruoyi.dahua.service.impl.DaHuaServiceImpl.loginHandleHandleMap.forEach((loginKey, handle) -> {
                if (loginKey.startsWith("login:handle:")) {
                    String ip = loginKey.substring("login:handle:".length());
                    try {
                        daHuaService.logoutDevice(ip);
                    } catch (Exception e) {
                        log.error("登出设备失败, IP:{}", ip, e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("登出设备异常", e);
        }
        stopServer();
        cleanup();
    }

    // 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
    private static class DisConnect implements NetSDKLib.fDisConnect {
        @Override
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            log.warn("Device[{}] Port[{}] DisConnect!", pchDVRIP, nDVRPort);
        }
    }

    // 网络连接恢复，设备重连成功回调
    // 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
    private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
        @Override
        public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            log.info("ReConnect Device[{}] Port[{}]", pchDVRIP, nDVRPort);
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
        NetSDKLib.LLong serverHandler = netsdk.CLIENT_ListenServer(address, port, 1000, callback, null);
        if (0 == serverHandler.longValue()) {
            log.error("启动服务器失败, {}", ToolKits.getErrorCodePrint());
        } else {
            log.info("启动服务器成功, [服务器地址 {}][服务器端口 {}]", address, port);
        }
        return serverHandler;
    }

    /**
     * 初始化
     */
    public boolean init(NetSDKLib.fDisConnect disConnect, NetSDKLib.fHaveReConnect haveReConnect) {
        bInit = netsdk.CLIENT_Init(disConnect, null);
        if (!bInit) {
            log.error("SDK初始化失败");
            return false;
        }
        log.info("SDK初始化成功");
        
        netsdk.CLIENT_SetAutoReconnect(haveReConnect, null);

        int waitTime = 5000;
        int tryTimes = 1;
        netsdk.CLIENT_SetConnectTime(waitTime, tryTimes);

        NetSDKLib.NET_PARAM netParam = new NetSDKLib.NET_PARAM();
        netParam.nConnectTime = 10000;
        netParam.nGetConnInfoTime = 3000;
        netParam.nGetDevInfoTime = 3000;
        netsdk.CLIENT_SetNetworkParam(netParam);

        return true;
    }

    /**
     * 清除环境
     */
    public void cleanup() {
        if (bInit) {
            netsdk.CLIENT_Cleanup();
            bInit = false;
            log.info("SDK资源已清理");
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
            log.info("停止监听服务成功");
        }
        return bRet;
    }

    /**
     * 侦听服务器回调函数
     */
    public class ServiceCB implements NetSDKLib.fServiceCallBack {
        @Override
        public int invoke(NetSDKLib.LLong lHandle, final String pIp, final int wPort, int lCommand, Pointer pParam, int dwParamLen, Pointer dwUserData) {
            byte[] buffer = new byte[dwParamLen];
            pParam.read(0, buffer, 0, dwParamLen);
            String deviceId = "";
            try {
                deviceId = new String(buffer, "GBK").trim();
            } catch (UnsupportedEncodingException e) {
                log.error("解析设备ID编码失败", e);
            }

            log.info("设备主动注册, IP:{}, Port:{}, DeviceId:{}", pIp, wPort, deviceId);
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
        log.info("设备信息已更新, IP:{}, DeviceId:{}", ip, deviceId);
    }

    /**
     * 获取设备列表
     *
     * @return
     */
    public List<DahuaDevice> getRegisterDeviceList() {
        List<DahuaDevice> deviceList = new ArrayList<>(deviceMap.values());
        log.info("获取主动注册设备列表, 数量:{}", deviceList.size());
        return deviceList;
    }

    /**
     * 删除设备
     *
     * @return
     */
    public void delRegisterDevice(String[] ips) {
        for (String ip : ips) {
            DahuaDevice removed = deviceMap.remove(ip);
            if (removed != null) {
                log.info("删除主动注册设备, IP:{}", ip);
            }
        }
    }
}
