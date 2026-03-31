package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 获取补光灯能力入参(对应 : NET_LIGHTINGCONTROL_CAPS)
 * @origin autoTool
 * @date 2023/10/19 17:05:46
 */
public class NET_IN_LIGHTINGCONTROL_CAPS extends NetSDKLib.SdkStructure {
    /**
     * /结构体大小
     */
    public int              dwSize;
    /**
     * / 通道号
     */
    public int              nChannel;

    public NET_IN_LIGHTINGCONTROL_CAPS() {
        this.dwSize = this.size();
    }
}

