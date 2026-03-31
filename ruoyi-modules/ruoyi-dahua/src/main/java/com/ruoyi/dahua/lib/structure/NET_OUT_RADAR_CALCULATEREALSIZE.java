package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 计算图片对应的地图实际长度出参(对应 EM_RADAR_OPERATE_TYPE_CALCULATEREALSIZE)
 * @date 2022/08/04 10:13:32
 */
public class NET_OUT_RADAR_CALCULATEREALSIZE extends NetSDKLib.SdkStructure {
    /**
     * 结构体大小
     */
    public int              dwSize;
    /**
     * 实际地图的高度，单位米（实际长度扩大100倍表示）
     */
    public int              nRealMapLength;
    /**
     * 实际地图的宽度，单位米（实际宽度扩大100倍表示）
     */
    public int              nRealMapWidth;

    public NET_OUT_RADAR_CALCULATEREALSIZE() {
        this.dwSize = this.size();
    }
}

