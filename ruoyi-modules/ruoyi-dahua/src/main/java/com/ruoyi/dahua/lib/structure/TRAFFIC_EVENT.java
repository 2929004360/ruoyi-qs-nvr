package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 交通事件信息
 * @date 2021/09/01
 */
public class TRAFFIC_EVENT extends NetSDKLib.SdkStructure {
	 /**
     *  机动车电子标识序列号
     */
    public byte[]           szCarId = new byte[128];
    /**
     *  车牌号码
     */
    public byte[]           szPlateNumber = new byte[128];
    /**
     *  行驶方向以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nDrivingDir;
    /**
     *  交通事件,参考{ @link EM_TRAFFIC_EVENT }
     */
    public int              emTrafficEvent;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

