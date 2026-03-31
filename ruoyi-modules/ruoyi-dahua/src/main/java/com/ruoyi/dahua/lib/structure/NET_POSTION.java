package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * 坐标与放大倍数
 * 
 * @author ： 251823
 * @since ： Created in 2021/11/07 
 */
public class NET_POSTION extends NetSDKLib.SdkStructure {
	/**
     *  水平坐标, 范围[0, 3599]，表示0度到359.9度，度数扩大10倍表示。
     */
    public int              nHorizontalCoordinate;
    /**
     *  垂直坐标，范围[-1800,1800]，表示-180.0度到+180.0度，度数扩大10倍表示。
     */
    public int              nVerticalCoordinate;
    /**
     *  放大倍数, 范围[0,127]，表示最小倍到最大倍的变倍位置
     */
    public int              nMagnification;
}

