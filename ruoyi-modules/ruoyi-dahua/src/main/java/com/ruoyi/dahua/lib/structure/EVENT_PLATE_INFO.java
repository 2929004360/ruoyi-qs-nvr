package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 车牌颜色
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 18:23
 */
public class EVENT_PLATE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  车头车牌号码
     */
    public byte             szFrontPlateNumber[] = new byte[64];
    /**
     *  车头车牌颜色 EM_PLATE_COLOR_TYPE
     */
    public int              emFrontPlateColor;
    /**
     *  车尾车牌号码
     */
    public byte             szBackPlateNumber[] = new byte[64];
    /**
     *  车尾车牌颜色 EM_PLATE_COLOR_TYPE
     */
    public int              emBackPlateColor;
    /**
     *  保留
     */
    public byte             reversed[] = new byte[128];
}

