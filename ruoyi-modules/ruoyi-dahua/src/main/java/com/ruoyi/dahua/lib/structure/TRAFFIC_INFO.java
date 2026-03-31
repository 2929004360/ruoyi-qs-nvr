package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 交通流信息
 * @date 2021/09/01
 */
public class TRAFFIC_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  进口数量
     */
    public int              nEnterNumber;
    /**
     *  进口方向交通流信息个数
     */
    public int              nEnterTrafficInfoNum;
    /**
     *  进口方向交通流信息, 包含1到N（进口数量）个进口方向交通流信息
     */
    public ENTER_TRAFFIC_INFO[] stuEnterTrafficInfo = (ENTER_TRAFFIC_INFO[]) new ENTER_TRAFFIC_INFO().toArray(8);
    /**
     *  预留字节 
     */
    public byte[]           byReserved = new byte[128];
}

