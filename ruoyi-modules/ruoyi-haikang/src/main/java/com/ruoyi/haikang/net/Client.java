package com.ruoyi.haikang.net;

import com.ruoyi.haikang.utils.OsSelect;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 海康sdk客户端
 *
 * @author: fengcheng
 */
@Component
@Slf4j
public class Client {

    public HCNetSDK hCNetSDK = null;

    public FExceptionCallBack_Imp fExceptionCallBack;

    public class FExceptionCallBack_Imp implements HCNetSDK.FExceptionCallBack {
        public void invoke(int dwType, int lUserID, int lHandle, Pointer pUser) {
            log.error("异常事件类型:" + dwType);
            log.error("异常事件类型:" + hCNetSDK.NET_DVR_GetLastError());
            return;
        }
    }

    /**
     * 动态库加载
     *
     * @return
     */
    public boolean createSDKInstance() {
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                String strDllPath = "";
                try {
                    if (OsSelect.isWindows())
                        //win系统加载库路径
                        strDllPath = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang\\lib\\win\\HCNetSDK.dll";

                    else if (OsSelect.isLinux())
                        //Linux系统加载库路径
                        strDllPath = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang/lib/linux/libhcnetsdk.so";
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
                } catch (Exception ex) {
                    log.info("加载库: " + strDllPath + " 错误: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 加载SDK
     */
    public void start() {
        if (hCNetSDK == null) {
            if (!createSDKInstance()) {
                log.error("加载SDK失败");
                return;
            }
        }

        //linux系统建议调用以下接口加载组件库
        if (OsSelect.isLinux()) {
            HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
            HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
            //这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
            String strPath1 = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang/lib/linux/libcrypto.so.1.1";
            String strPath2 = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang/lib/linux/libssl.so.1.1";
            System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
            ptrByteArray1.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_LIBEAY_PATH, ptrByteArray1.getPointer());
            System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
            ptrByteArray2.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SSLEAY_PATH, ptrByteArray2.getPointer());
            String strPathCom = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang/lib/linux/";
            HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
            System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
            struComPath.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SDK_PATH, struComPath.getPointer());
        }

        //SDK初始化，一个程序只需要调用一次
        boolean initSuc = hCNetSDK.NET_DVR_Init();

        if (initSuc) {
            log.info("海康SDK初始化成功");
        } else {
            log.error("海康SDK初始化失败");
            return;
        }

        //异常消息回调
        if (fExceptionCallBack == null) {
            fExceptionCallBack = new FExceptionCallBack_Imp();
        }
        Pointer pUser = null;
        if (!hCNetSDK.NET_DVR_SetExceptionCallBack_V30(0, 0, fExceptionCallBack, pUser)) {
            return;
        }
        log.info("设置异常消息回调成功");
        //启动SDK写日志
        hCNetSDK.NET_DVR_SetLogToFile(3, "./haikangSdkLog", false);
    }
}



