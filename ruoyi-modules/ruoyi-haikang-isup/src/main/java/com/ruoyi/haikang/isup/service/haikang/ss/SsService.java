package com.ruoyi.haikang.isup.service.haikang.ss;

import com.ruoyi.haikang.isup.callBack.CbEHomeSSRWCallBackEx;
import com.ruoyi.haikang.isup.callBack.PSS_Message_Callback;
import com.ruoyi.haikang.isup.callBack.PSS_Storage_Callback;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.utils.OsSelect;
import com.sun.jna.Native;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SsService {

    public static HCISUPSS hCEhomeSS = null;

    private final HaikangIsupConfig haikangIsupConfig;

    static HCISUPSS.NET_EHOME_SS_LISTEN_PARAM pSSListenParam = new HCISUPSS.NET_EHOME_SS_LISTEN_PARAM();

    static PSS_Message_Callback pSS_Message_Callback;

    private final PSS_Storage_Callback pSS_Storage_Callback;

    static CbEHomeSSRWCallBackEx fEHomeSSRWCallBackEx;

    public static int SsHandle = -1;

    private static boolean CreateSDKInstance() {
        if (hCEhomeSS == null) {
            synchronized (HCISUPSS.class) {
                String strDllPath = "";
                try {
                    if (OsSelect.isWindows()) {
                        strDllPath = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\HCISUPSS.dll";
                    } else if (OsSelect.isLinux()) {
                        strDllPath = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libHCISUPSS.so";
                    }
                    hCEhomeSS = (HCISUPSS) Native.loadLibrary(strDllPath, HCISUPSS.class);
                } catch (Exception e) {
                    log.error("loadLibrary: " + strDllPath + " Error: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    public void eSS_Init() {
        if (hCEhomeSS == null) {
            if (!CreateSDKInstance()) {
                log.error("加载SS SDK失败");
                return;
            }
        }
        if (OsSelect.isWindows()) {
            String strPathCrypto = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\libeay32.dll";
            int iPathCryptoLen = strPathCrypto.getBytes().length;
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(iPathCryptoLen + 1);
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, iPathCryptoLen);
            ptrByteArrayCrypto.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(4, ptrByteArrayCrypto.getPointer());

            String strPathSsl = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\ssleay32.dll";
            int iPathSslLen = strPathSsl.getBytes().length;
            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(iPathSslLen + 1);
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, iPathSslLen);
            ptrByteArraySsl.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(5, ptrByteArraySsl.getPointer());

            String strPathSqlite = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\sqlite3.dll";
            int iPathSqliteLen = strPathSqlite.getBytes().length;
            HCISUPCMS.BYTE_ARRAY ptrByteArraySqlite = new HCISUPCMS.BYTE_ARRAY(iPathSqliteLen + 1);
            System.arraycopy(strPathSqlite.getBytes(), 0, ptrByteArraySqlite.byValue, 0, iPathSqliteLen);
            ptrByteArraySqlite.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(6, ptrByteArraySqlite.getPointer());
            boolean sinit = hCEhomeSS.NET_ESS_Init();
            if (!sinit) {
                log.error("NET_ESS_Init失败，错误码：" + hCEhomeSS.NET_ESS_GetLastError());
            }
            HCISUPCMS.NET_EHOME_IPADDRESS ipaddress = new HCISUPCMS.NET_EHOME_IPADDRESS();
            String ServerIP = haikangIsupConfig.getPicServer().getIp();
            System.arraycopy(ServerIP.getBytes(), 0, ipaddress.szIP, 0, ServerIP.length());
            ipaddress.wPort = (short) haikangIsupConfig.getPicServer().getPort();
            ipaddress.write();
            boolean b = hCEhomeSS.NET_ESS_SetSDKInitCfg(3, ipaddress.getPointer());
            if (!b) {
                log.error("NET_ESS_SetSDKInitCfg失败，错误码：" + hCEhomeSS.NET_ESS_GetLastError());
            }
        } else if (OsSelect.isLinux()) {
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCrypto = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libcrypto.so";
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
            ptrByteArrayCrypto.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(4, ptrByteArrayCrypto.getPointer());

            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathSsl = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libssl.so";
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
            ptrByteArraySsl.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(5, ptrByteArraySsl.getPointer());

            HCISUPCMS.BYTE_ARRAY ptrByteArraysplite = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathsplite = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libsqlite3.so";
            System.arraycopy(strPathsplite.getBytes(), 0, ptrByteArraysplite.byValue, 0, strPathsplite.length());
            ptrByteArraysplite.write();
            hCEhomeSS.NET_ESS_SetSDKInitCfg(6, ptrByteArraysplite.getPointer());
            boolean sinit = hCEhomeSS.NET_ESS_Init();
            if (!sinit) {
                log.error("NET_ESS_Init失败，错误码：" + hCEhomeSS.NET_ESS_GetLastError());
            }
            HCISUPCMS.NET_EHOME_IPADDRESS ipaddress = new HCISUPCMS.NET_EHOME_IPADDRESS();
            String ServerIP = haikangIsupConfig.getPicServer().getIp();
            System.arraycopy(ServerIP.getBytes(), 0, ipaddress.szIP, 0, ServerIP.length());
            ipaddress.wPort = (short) haikangIsupConfig.getPicServer().getPort();
            ipaddress.write();
            boolean b = hCEhomeSS.NET_ESS_SetSDKInitCfg(3, ipaddress.getPointer());
            if (!b) {
                log.error("NET_ESS_SetSDKInitCfg失败，错误码：" + hCEhomeSS.NET_ESS_GetLastError());
            }
        }
        boolean logToFile = hCEhomeSS.NET_ESS_SetLogToFile(3, System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/EHomeSDKLog", false);
    }

    public void startSsListen() {
        String SSIP = haikangIsupConfig.getPicServer().getIp();
        System.arraycopy(SSIP.getBytes(), 0, pSSListenParam.struAddress.szIP, 0, SSIP.length());
        pSSListenParam.struAddress.wPort = (short) haikangIsupConfig.getPicServer().getPort();
        String strKMS_UserName = "test";
        System.arraycopy(strKMS_UserName.getBytes(), 0, pSSListenParam.szKMS_UserName, 0, strKMS_UserName.length());
        String strKMS_Password = "12345";
        System.arraycopy(strKMS_Password.getBytes(), 0, pSSListenParam.szKMS_Password, 0, strKMS_Password.length());
        String strAccessKey = "test";
        System.arraycopy(strAccessKey.getBytes(), 0, pSSListenParam.szAccessKey, 0, strAccessKey.length());
        String strSecretKey = "12345";
        System.arraycopy(strSecretKey.getBytes(), 0, pSSListenParam.szSecretKey, 0, strSecretKey.length());
        pSSListenParam.byHttps = 0;
        if (pSS_Message_Callback == null) {
            pSS_Message_Callback = new PSS_Message_Callback();
        }
        pSSListenParam.fnSSMsgCb = pSS_Message_Callback;
        pSSListenParam.fnSStorageCb = pSS_Storage_Callback;
        if (fEHomeSSRWCallBackEx == null) {
            fEHomeSSRWCallBackEx = new CbEHomeSSRWCallBackEx();
        }
        pSSListenParam.fnSSRWCbEx = fEHomeSSRWCallBackEx;
        pSSListenParam.bySecurityMode = 1;
        pSSListenParam.write();
        SsHandle = hCEhomeSS.NET_ESS_StartListen(pSSListenParam);
        if (SsHandle == -1) {
            int err = hCEhomeSS.NET_ESS_GetLastError();
            log.error("NET_ESS_StartListen失败，错误：" + err);
            hCEhomeSS.NET_ESS_Fini();
        } else {
            String SsListenInfo = new String(pSSListenParam.struAddress.szIP).trim() + "_" + pSSListenParam.struAddress.wPort;
            log.info("存储服务器：" + SsListenInfo + ",NET_ESS_StartListen 成功！");
        }
    }
}
