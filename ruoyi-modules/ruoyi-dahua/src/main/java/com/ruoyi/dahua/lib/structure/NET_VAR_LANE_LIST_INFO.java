package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 可变车道列表信息
 * @date 2021/09/28
 */
public class NET_VAR_LANE_LIST_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  可变车道指示牌号
     */
    public int              nVarlaneId;
    /**
     *  可变车道编号（车道配置页面编号）
     */
    public int              nVarlaneNumber;
    /**
     *  该指示牌对应可变属性的对应的通道的闪光时长 单位秒,0-255 S
     */
    public int              nVarlaneFlashTime;
    /**
     *  可变车道属性个数
     */
    public int              nPropertyListNum;
    /**
     *  可变车道属性
     */
    public NET_PROPERTY_LIST_INFO[] stuPropertyInfo = (NET_PROPERTY_LIST_INFO[]) new NET_PROPERTY_LIST_INFO().toArray(7);
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[32];
}

