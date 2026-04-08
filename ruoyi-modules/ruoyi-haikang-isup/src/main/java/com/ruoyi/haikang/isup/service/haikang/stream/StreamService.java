package com.ruoyi.haikang.isup.service.haikang.stream;

import com.ruoyi.haikang.isup.callBack.FPREVIEW_NEWLINK_CB_FILE;
import com.ruoyi.haikang.isup.config.HaikangIsupConfig;
import com.ruoyi.haikang.isup.service.haikang.cms.HCISUPCMS;
import com.ruoyi.haikang.isup.utils.OsSelect;
import com.sun.jna.Native;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 海康isup stream服务
 *
 * @FileName StreamService
 * @Description
 * @Author fengcheng
 * @date 2025-12-24
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class StreamService {
    public static HCISUPStream hCEhomeStream = null;

    public final FPREVIEW_NEWLINK_CB_FILE fpreview_newlink_cb_file;

//    private final PlaybackNewlinkCallbackCallBack playbackNewlinkCallbackCallBack = new PlaybackNewlinkCallbackCallBack();

    private final HaikangIsupConfig haikangIsupConfig;

    /**
     * 动态库加载
     *
     * @return
     */
    private static boolean CreateSDKInstance() {
        if (hCEhomeStream == null) {
            synchronized (HCISUPStream.class) {
                String strDllPath = "";
                try {
                    if (OsSelect.isWindows())
                    //win系统加载库路径
                    {
                        strDllPath = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\HCISUPStream.dll";
                    } else if (OsSelect.isLinux())
                    //Linux系统加载库路径
                    {
                        strDllPath = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libHCISUPStream.so";
                    }
                    hCEhomeStream = (HCISUPStream) Native.loadLibrary(strDllPath, HCISUPStream.class);
                } catch (Exception ex) {
                    log.error("加载库: " + strDllPath + " 错误: " + ex.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    public void eStream_Init() {
        if (hCEhomeStream == null) {
            if (!CreateSDKInstance()) {
                log.error("加载流SDK失败");
                return;
            }
        }
        if (OsSelect.isWindows()) {
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCrypto = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\libeay32.dll"; //Linux版本是libcrypto.so库文件的路径
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
            ptrByteArrayCrypto.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKInitCfg(0, ptrByteArrayCrypto.getPointer())) {
                log.error("NET_ESTREAM_SetSDKInitCfg 0 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }
            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathSsl = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\ssleay32.dll";    //Linux版本是libssl.so库文件的路径
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
            ptrByteArraySsl.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKInitCfg(1, ptrByteArraySsl.getPointer())) {
                log.error("NET_ESTREAM_SetSDKInitCfg 1 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }
            //流媒体初始化
            hCEhomeStream.NET_ESTREAM_Init();
            //设置HCAapSDKCom组件库文件夹所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCom = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCom = System.getProperty("user.dir") + "\\ruoyi-modules\\ruoyi-haikang-isup\\lib\\win\\HCAapSDKCom";      //只支持绝对路径，建议使用英文路径
            System.arraycopy(strPathCom.getBytes(), 0, ptrByteArrayCom.byValue, 0, strPathCom.length());
            ptrByteArrayCom.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKLocalCfg(5, ptrByteArrayCom.getPointer())) {
                log.error("NET_ESTREAM_SetSDKLocalCfg 5 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }

            Stream_StartListen();

            hCEhomeStream.NET_ESTREAM_SetLogToFile(3, System.getProperty("user.dir") + "/ruoyi-haikang-isup/EHomeSDKLog", false);
        } else if (OsSelect.isLinux()) {
            //设置libcrypto.so所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCrypto = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCrypto = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libcrypto.so"; //Linux版本是libcrypto.so库文件的路径
            System.arraycopy(strPathCrypto.getBytes(), 0, ptrByteArrayCrypto.byValue, 0, strPathCrypto.length());
            ptrByteArrayCrypto.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKInitCfg(0, ptrByteArrayCrypto.getPointer())) {
                log.error("NET_ESTREAM_SetSDKInitCfg 0 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }
            //设置libssl.so所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArraySsl = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathSsl = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/libssl.so";    //Linux版本是libssl.so库文件的路径
            System.arraycopy(strPathSsl.getBytes(), 0, ptrByteArraySsl.byValue, 0, strPathSsl.length());
            ptrByteArraySsl.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKInitCfg(1, ptrByteArraySsl.getPointer())) {
                log.error("NET_ESTREAM_SetSDKInitCfg 1 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }
            hCEhomeStream.NET_ESTREAM_Init();
            //设置HCAapSDKCom组件库文件夹所在路径
            HCISUPCMS.BYTE_ARRAY ptrByteArrayCom = new HCISUPCMS.BYTE_ARRAY(256);
            String strPathCom = System.getProperty("user.dir") + "/ruoyi-modules/ruoyi-haikang-isup/lib/linux/HCAapSDKCom/";      //只支持绝对路径，建议使用英文路径
            System.arraycopy(strPathCom.getBytes(), 0, ptrByteArrayCom.byValue, 0, strPathCom.length());
            ptrByteArrayCom.write();
            if (!hCEhomeStream.NET_ESTREAM_SetSDKLocalCfg(5, ptrByteArrayCom.getPointer())) {
                log.error("NET_ESTREAM_SetSDKLocalCfg 5 失败，错误:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            }

            Stream_StartListen();
            hCEhomeStream.NET_ESTREAM_SetLogToFile(3, System.getProperty("user.dir") + "/ruoyi-isup/EHomeSDKLog", false);
        }
    }

    public void Stream_StartListen() {
        HCISUPStream.NET_EHOME_LISTEN_PREVIEW_CFG struPreviewListen = new HCISUPStream.NET_EHOME_LISTEN_PREVIEW_CFG();

        struPreviewListen.struIPAdress.szIP = haikangIsupConfig.getSmsServer().getIp().getBytes();
        struPreviewListen.struIPAdress.wPort = (short) haikangIsupConfig.getSmsServer().getPort(); //流媒体服务器监听端口
        struPreviewListen.fnNewLinkCB = fpreview_newlink_cb_file; //预览连接请求回调函数
        struPreviewListen.pUser = null;
        struPreviewListen.byLinkMode = 0; //0- TCP方式，1- UDP方式
        struPreviewListen.write();

        int StreamHandle = hCEhomeStream.NET_ESTREAM_StartListenPreview(struPreviewListen);
        if (StreamHandle == -1) {

            log.error("NET_ESTREAM_StartListen检查失败，错误代码:" + hCEhomeStream.NET_ESTREAM_GetLastError());
            hCEhomeStream.NET_ESTREAM_Fini();
            return;
        } else {
            String StreamListenInfo = new String(struPreviewListen.struIPAdress.szIP).trim() + "_" + struPreviewListen.struIPAdress.wPort;
            log.info("流媒体服务：" + StreamListenInfo + ",NET_ESTREAM_StartListenPreview 成功");
        }


        HCISUPStream.NET_EHOME_PLAYBACK_LISTEN_PARAM struPlayBackListen = new HCISUPStream.NET_EHOME_PLAYBACK_LISTEN_PARAM();
        System.arraycopy(haikangIsupConfig.getSmsBackServer().getIp().getBytes(), 0, struPlayBackListen.struIPAdress.szIP, 0, haikangIsupConfig.getSmsBackServer().getIp().length());
        struPlayBackListen.struIPAdress.wPort = (short) haikangIsupConfig.getSmsBackServer().getPort(); //流媒体服务器监听端口
//        struPlayBackListen.fnNewLinkCB = playbackNewlinkCallbackCallBack;
        struPlayBackListen.byLinkMode = 0; //0- TCP方式，1- UDP方式

        int m_lPlayBackListenHandle = StreamService.hCEhomeStream.NET_ESTREAM_StartListenPlayBack(struPlayBackListen);
        if (m_lPlayBackListenHandle < -1) {
            log.error("NET_ESTREAM_StartListenPlayBack失败，错误代码:" + StreamService.hCEhomeStream.NET_ESTREAM_GetLastError());
            StreamService.hCEhomeStream.NET_ESTREAM_Fini();
            return;
        } else {
            String BackStreamListenInfo = new String(struPlayBackListen.struIPAdress.szIP).trim() + "_" + struPlayBackListen.struIPAdress.wPort;
            log.info("回放流媒体服务：" + BackStreamListenInfo + ",NET_ESTREAM_StartListenPlayBack succeed");
        }
    }
}
