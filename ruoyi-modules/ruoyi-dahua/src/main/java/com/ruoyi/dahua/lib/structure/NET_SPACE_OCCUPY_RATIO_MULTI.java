package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 空间占有率统计信息
 * @origin autoTool
 * @date 2023/11/23 20:57:47
 */
public class NET_SPACE_OCCUPY_RATIO_MULTI extends NetSDKLib.SdkStructure {
    /**
     * / 空间占有率统计区域的ID
     */
    public int              nID;
    /**
     * / 对应ID的空间占有率，即按百分率计量的车辆长度总和除以时间间隔内车辆平均行驶距离
     */
    public float            fSpaceOccupyRatio;
    /**
     * / 保留字节
     */
    public byte[]           szReserverd = new byte[8];

    public NET_SPACE_OCCUPY_RATIO_MULTI() {
    }
}

