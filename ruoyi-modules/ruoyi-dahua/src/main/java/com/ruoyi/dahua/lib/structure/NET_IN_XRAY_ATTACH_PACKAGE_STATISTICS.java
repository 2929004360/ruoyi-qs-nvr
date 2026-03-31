package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.callback.securityCheck.fXRayAttachPackageStatistics;
import com.sun.jna.Pointer;

/**
 *@author ： 291189
 *@since ： Created in 2021/7/1 9:47
 * CLIENT_XRayAttachPackageStatistics 输入结构体
 */
public class NET_IN_XRAY_ATTACH_PACKAGE_STATISTICS extends NetSDKLib.SdkStructure {
    public int              dwSize;                               // 赋值为结构体大小
    public byte[]           szUUID = new byte[36];                // UUID
    public fXRayAttachPackageStatistics cbNotify;                 // 回调函数
    public Pointer          dwUser;                               // 用户信息

    public NET_IN_XRAY_ATTACH_PACKAGE_STATISTICS() {
        this.dwSize = this.size();
    }
}

