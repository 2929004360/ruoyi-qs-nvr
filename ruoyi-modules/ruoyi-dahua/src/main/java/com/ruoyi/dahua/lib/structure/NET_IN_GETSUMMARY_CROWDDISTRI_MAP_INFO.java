package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description CLIENT_GetSummaryCrowdDistriMap 接口输入参数
 * @date 2022/01/07
 */
public class NET_IN_GETSUMMARY_CROWDDISTRI_MAP_INFO extends NetSDKLib.SdkStructure {
    /**
     *  结构体大小
     */
    public int              dwSize;
    /**
     *  通道号
     */
    public int              nChannelID;

    public NET_IN_GETSUMMARY_CROWDDISTRI_MAP_INFO(){
        this.dwSize = this.size();
    }
}

