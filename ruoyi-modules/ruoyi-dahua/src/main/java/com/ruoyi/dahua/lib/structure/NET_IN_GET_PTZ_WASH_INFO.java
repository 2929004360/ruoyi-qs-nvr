package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author ： 260611
 * @description ： 获取标定后的冲洗信息, 入参
 * @since ： Created in 2021/11/29 10:47
 */

public class NET_IN_GET_PTZ_WASH_INFO extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道ID
     */
    public int              nChannelID;

    public NET_IN_GET_PTZ_WASH_INFO() {
        this.dwSize = this.size();
    }
}

