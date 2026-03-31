package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 接口 CLIENT_AddSoftTourPoint 的输入参数
 * @origin autoTool
 * @date 2023/10/16 20:15:22
 */
public class NET_IN_ADD_SOFT_TOUR_POINT_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 此结构体大小,必须赋值
     */
    public int              dwSize;
    /**
     * /云台通道号
     */
    public int              nChannel;
    /**
     * /巡航线路编号
     */
    public int              nIndex;
    /**
     * / 线路上点的序号，从0开始, 如果为 -1, 追加到线路上最后位置，否则为指定序号
     */
    public int              nPoint;
    /**
     * / 预置点编号,编号从1开始
     */
    public int              nPresetIndex;
    /**
     * / 在该预置点停留时间,时间单位为秒
     */
    public int              nDuration;
    /**
     * / 转动到预置点的速度, 归一化到0~1
     */
    public float            fSpeed;

    public NET_IN_ADD_SOFT_TOUR_POINT_INFO() {
        this.dwSize = this.size();
    }
}

