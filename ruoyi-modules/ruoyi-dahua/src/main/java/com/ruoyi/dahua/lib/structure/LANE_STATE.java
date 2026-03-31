package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车道功能状态
 * @date 2021/09/01
 */
public class LANE_STATE extends NetSDKLib.SdkStructure {
	/**
     *  功能可变车道进口数量路口具有功能可变车道进口的数量
     */
    public int              nEnterNumber;
    /**
     *  进口车道功能状态信息个数
     */
    public int              nEnterLaneStateNum;
    /**
     *  进口车道功能状态信息（0~8）包含1到N（功能可变车道进口数量）个进口车道功能状态信息
     */
    public ENTER_LANE_STATE[] stuEnterLaneStateInfo = (ENTER_LANE_STATE[]) new ENTER_LANE_STATE().toArray(8);
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[128];
}

