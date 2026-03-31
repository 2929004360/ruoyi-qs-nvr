package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 设置逻辑通道号和真实通道号的绑定关系出参 ，对应接口{@link NetSDKLib#CLIENT_SetBlindRealChannel}
 *
 * @author ： 47040
 * @since ： Created in 2020/9/28 15:12
 */
public class NET_OUT_SET_BLIND_REAL_CHANNEL extends NetSDKLib.SdkStructure {
    public int              dwSize;

    public NET_OUT_SET_BLIND_REAL_CHANNEL() {
        dwSize = this.size();
    }
}

