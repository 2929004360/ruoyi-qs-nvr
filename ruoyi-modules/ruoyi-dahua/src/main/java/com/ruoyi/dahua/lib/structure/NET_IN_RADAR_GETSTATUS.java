package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 获取雷达状态入参(对应 EM_RADAR_OPERATE_TYPE_GETSTATUS)
 * @date 2022/08/04 10:13:32
 */
public class NET_IN_RADAR_GETSTATUS extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 通道号
     */
    public int              nChannel;

    public NET_IN_RADAR_GETSTATUS() {
        this.dwSize = this.size();
    }
}

