package com.ruoyi.dahua.auto;

import com.ruoyi.dahua.common.SavePath;
import com.ruoyi.dahua.lib.NetSDKLib.*;
import com.ruoyi.dahua.module.AutoRegisterModule;
import com.ruoyi.dahua.module.LoginModule;
import com.sun.jna.Pointer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

/**
 * @FileName AutoRegister
 * @Description
 * @Author fengcheng
 * @date 2025-06-06
 **/
public class AutoRegister {

    /**
     * 设备断线通知回调
     */
    private DisConnect disConnectCallback = new DisConnect();


    /**
     * 抓图回调
     */
    public CaptureReceiveCB captureCallback = new CaptureReceiveCB();


    private ServiceCB servicCallback = new ServiceCB();

    private DefaultMutableTreeNode rootNode;

    public AutoRegister() {
        // 打开工程，初始化，设置断线回调
        LoginModule.init(disConnectCallback, null);

        // 设置抓图回调
        AutoRegisterModule.setSnapRevCallBack(captureCallback);



        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        AutoRegisterModule.startServer("192.168.1.5", 9500, servicCallback);
    }

    /**
     * 抓图回调函数
     */
    public class CaptureReceiveCB implements fSnapRev{
        BufferedImage bufferedImage = null;
        public void invoke( LLong lLoginID, Pointer pBuf, int RevLen, int EncodeType, int CmdSerial, Pointer dwUser) {
            if(pBuf != null && RevLen > 0) {
                String strFileName = SavePath.getSavePath().getSaveCapturePath();

                System.out.println("strFileName = " + strFileName);
            }
        }
    }

    /**
     * 设备断线回调: 通过 CLIENT_Init 设置该回调函数，当设备出现断线时，SDK会调用该函数
     */
    private class DisConnect implements fDisConnect {
        public void invoke(LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
            System.out.printf("Device[%s] Port[%d] DisConnect!\n", pchDVRIP, nDVRPort);

        }
    }

    /**
     * 侦听服务器回调函数
     */
    public class ServiceCB implements fServiceCallBack {
        @Override
        public int invoke(LLong lHandle, final String pIp, final int wPort,
                          int lCommand, Pointer pParam, int dwParamLen,
                          Pointer dwUserData) {

            // 将 pParam 转化为序列号
            byte[] buffer = new byte[dwParamLen];
            pParam.read(0, buffer, 0, dwParamLen);
            String deviceId = "";
            try {
                deviceId = new String(buffer, "GBK").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            System.out.printf("Register Device Info [Device address %s][port %s][DeviceID %s] \n", pIp, wPort, deviceId);
            switch(lCommand) {
                case EM_LISTEN_TYPE.NET_DVR_DISCONNECT: {  // 验证期间设备断线回调
                    for(int i = 0; i < rootNode.getChildCount(); i++) {

                    }

                    break;
                }
                case EM_LISTEN_TYPE.NET_DVR_SERIAL_RETURN: { // 设备注册携带序列号
                    for(int i = 0; i < rootNode.getChildCount(); i++) {

                    }

                    break;
                }
                default:
                    break;

            }

            return 0;
        }
    }


    public static void main(String[] args) {
        System.out.println("自动注册");
        AutoRegister demo = new AutoRegister();
    }
}
