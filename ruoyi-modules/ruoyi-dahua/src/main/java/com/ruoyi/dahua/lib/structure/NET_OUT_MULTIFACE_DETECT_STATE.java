package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_AttachDetectMultiFaceState 接口输出参数
 * @date 2022/11/21 17:53:13
 */
public class NET_OUT_MULTIFACE_DETECT_STATE extends NetSDKLib.SdkStructure {
    /**
     * 此结构体大小
     */
    public int              dwSize;

    public NET_OUT_MULTIFACE_DETECT_STATE() {
        this.dwSize = this.size();
    }
}

