package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.callback.securityCheck.fSecurityGateAttachAlarmStatistics;
import com.sun.jna.Pointer;

/**
 * @author ： 291189
 * @since ： Created in 2021/6/29 10:21
 */
public class NET_IN_SECURITYGATE_ATTACH_ALARM_STATISTICS extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 赋值为结构体大小
    public byte[]           szUUID = new byte[36];                // UUID
    public fSecurityGateAttachAlarmStatistics cbNotify;           // 回调函数
    public Pointer          dwUser;                               // 用户信息

    public NET_IN_SECURITYGATE_ATTACH_ALARM_STATISTICS() {
        this.dwSize = this.size();
    }
}

