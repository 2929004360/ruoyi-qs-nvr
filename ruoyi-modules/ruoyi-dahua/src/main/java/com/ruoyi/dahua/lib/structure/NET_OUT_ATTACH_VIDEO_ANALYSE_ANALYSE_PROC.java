package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description CLIENT_AttachVideoAnalyseAnalyseProc 输出参数
 * @date 2023/02/21 14:39:21
 */
public class NET_OUT_ATTACH_VIDEO_ANALYSE_ANALYSE_PROC extends NetSDKLib.SdkStructure {
    /**
     * 此结构体大小,必须赋值
     */
    public int              dwSize;

    public NET_OUT_ATTACH_VIDEO_ANALYSE_ANALYSE_PROC() {
        this.dwSize = this.size();
    }
}

