package com.ruoyi.haikang.isup.service.haikang.cms;

import com.ruoyi.haikang.isup.callBack.FRegisterCallBack;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.utils.OsSelect;
import com.sun.jna.Native;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 海康isup cms服务
 *
 * @FileName ICmsService
 * @Description
 * @Author fengcheng
 * @date 2025-12-24
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class CmsService {

    public static HCISUPCMS hCEhomeCMS = null;

    //注册回调函数实现
    private final FRegisterCallBack fRegisterCallBack;

    HCISUPCMS.NET_EHOME_CMS_LISTEN_PARAM struCMSListenPara = new HCISUPCMS.NET_EHOME_CMS_LISTEN_PARAM();

    // CMS监听句柄
    public int CmsHandle = -1;

    private final HaikangIsupConfig haikangIsupConfig;

    /**
     * 根据不同操作系统选择不同的库文件和库路径
     *
     * @return
     */
    private boolean CreateSDKInstance() {
        if (hCEhomeCMS == null) {
            synchronized (HCISUPCMS.class) {
                String strDllPath = "";
                try {
                    //System.setProperty("jna.debug_load", "true");
                    if (OsSelect.isWindows())
                    //win系统加载库路径(路径不要带中文)
                    {
                        strDllPath = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\HCISUPCMS.dll";
                    } else if (OsSelect.isLinux())
                    //Linux系统加载库路径(路径不要带中文)
                    {
                        strDllPath = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libHCISUPCMS.so";
                    }
                    hCEhomeCMS = (HCISUPCMS) Native.loadLibrary(strDllPath, HCISUPCMS.class);
                } catch (Exception ex) {
                    log.info("加载库: " + strDllPath + " 错误: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * cms服务初始化，开启监听
     *
     * @throws IOException
     */
    public void cMS_Init() throws IOException {

        if (hCEhomeCMS == null) {
            if (!CreateSDKInstance()) {
                log.info("加载CMS SDK失败");
                return;
            }
        }
        if (OsSelect.isWindows()) {
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCrypto = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\libeay32.dll"; //Linux版本是libcrypto.so库文件的路径
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
            ptrByteArrayCrypto.write();
            hCEhomeCMS.NET_ECMS_SetSDKInitCfg(0, ptrByteArrayCrypto.getPointer());

            //设置libssl.so所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathSsl = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\ssleay32.dll";    //Linux版本是libssl.so库文件的路径
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
            ptrByteArraySsl.write();
            hCEhomeCMS.NET_ECMS_SetSDKInitCfg(1, ptrByteArraySsl.getPointer());
            //注册服务初始化
            boolean binit = hCEhomeCMS.NET_ECMS_Init();
            //设置HCAapSDKCom组件库文件夹所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCom = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCom = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\HCAapSDKCom";        //只支持绝对路径，建议使用英文路径
            System.arraycopy(strPathCom.getBytes(), 0, ptrByteArrayCom.byValue, 0, strPathCom.length());
            ptrByteArrayCom.write();
            hCEhomeCMS.NET_ECMS_SetSDKLocalCfg(5, ptrByteArrayCom.getPointer());
        } else if (OsSelect.isLinux()) {
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCrypto = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libcrypto.so"; //Linux版本是libcrypto.so库文件的路径
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
            ptrByteArrayCrypto.write();
            hCEhomeCMS.NET_ECMS_SetSDKInitCfg(0, ptrByteArrayCrypto.getPointer());

            //设置libssl.so所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathSsl = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libssl.so";    //Linux版本是libssl.so库文件的路径
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
            ptrByteArraySsl.write();
            hCEhomeCMS.NET_ECMS_SetSDKInitCfg(1, ptrByteArraySsl.getPointer());
            //注册服务初始化
            boolean binit = hCEhomeCMS.NET_ECMS_Init();
            //设置HCAapSDKCom组件库文件夹所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCom = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCom = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/HCAapSDKCom/";        //只支持绝对路径，建议使用英文路径
            System.arraycopy(strPathCom.getBytes(), 0, ptrByteArrayCom.byValue, 0, strPathCom.length());
            ptrByteArrayCom.write();
            hCEhomeCMS.NET_ECMS_SetSDKLocalCfg(5, ptrByteArrayCom.getPointer());

        }
        hCEhomeCMS.NET_ECMS_SetLogToFile(3, System.getProperty("user.dir") + "/ruoyi-haikang-isup/EHomeSDKLog", false);
    }

    public void startCmsListen() {
        System.arraycopy(haikangIsupConfig.getCmsServer().getIp().getBytes(), 0, struCMSListenPara.struAddress.szIP, 0, haikangIsupConfig.getCmsServer().getIp().length());
        struCMSListenPara.struAddress.wPort = (short) haikangIsupConfig.getCmsServer().getPort();
//        struCMSListenPara.dwKeepAliveSec = 10;
//        struCMSListenPara.dwTimeOutCount = 3;
        struCMSListenPara.fnCB = fRegisterCallBack;
        struCMSListenPara.write();
        //启动监听，接收设备注册信息
        CmsHandle = hCEhomeCMS.NET_ECMS_StartListen(struCMSListenPara);
        if (CmsHandle < 0) {
            log.error("NET_ECMS_StartListen失败，错误代码:" + hCEhomeCMS.NET_ECMS_GetLastError());
            hCEhomeCMS.NET_ECMS_Fini();
            return;
        }
        String CmsListenInfo = new String(struCMSListenPara.struAddress.szIP).trim() + "_" + struCMSListenPara.struAddress.wPort;
        log.info("注册服务器:" + CmsListenInfo + ",NET_ECMS_StartListen 成功!");
    }
}
