package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 *
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class NET_POINT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  主相机标定点
     */
    public NetSDKLib.DH_POINT stuMasterPoint = new NetSDKLib.DH_POINT();
    /**
     *  从相机(球机)标定点
     */
    public NetSDKLib.DH_POINT stuSlavePoint = new NetSDKLib.DH_POINT();
    /**
     *  保留字段
     */
    public byte             byReserved[] = new byte[256];
}

