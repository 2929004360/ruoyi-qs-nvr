package com.ruoyi.dahua.frame.scada;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.module.LoginModule;

import static com.ruoyi.dahua.module.LoginModule.m_hLoginHandle;

/**
 * className：SCADAAttachModule
 * description：
 * author：251589
 * createTime：2021/5/7 20:16
 *
 * @version v1.0
 */

public class SCADAAttachModule {
    // 订阅实时信息句柄
    public static NetSDKLib.LLong attachInfoHandle = new NetSDKLib.LLong(0);

    // 订阅报警句柄
    public static NetSDKLib.LLong alarmAttachInfoHandle = new NetSDKLib.LLong(0);

    public static boolean attachInfo(NetSDKLib.fSCADAAttachInfoCallBack callBack){
        // 入参
        NetSDKLib.NET_IN_SCADA_ATTACH_INFO stIn = new NetSDKLib.NET_IN_SCADA_ATTACH_INFO();
        stIn.cbCallBack = callBack;
        // 出参
        NetSDKLib.NET_OUT_SCADA_ATTACH_INFO stOut = new NetSDKLib.NET_OUT_SCADA_ATTACH_INFO();
        attachInfoHandle = LoginModule.netsdk.CLIENT_SCADAAttachInfo(m_hLoginHandle, stIn, stOut, 3000);
        if (attachInfoHandle.longValue() != 0){
            System.out.println("AttachInfo succeed!");
        } else {
            System.err.println("AttachInfo failed!");
            return false;
        }
        return true;
    }


}
