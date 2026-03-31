package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_SMOKEDETECTION(烟雾报警事件)对应的数据块描述信息
 * @date 2021/12/15
 */
public class NET_GPS_INFO_EX extends NetSDKLib.SdkStructure {
	 /**
     *  经度(单位是百万分之一度)
     *  // 西经：0 - 180000000				实际值应为: 180*1000000 – dwLongitude
     *  // 东经：180000000 - 360000000		实际值应为: dwLongitude – 180*1000000
	    // 如: 300168866应为（300168866 - 180*1000000）/1000000 即东经120.168866度
     */
    public int              nLongitude;
    /**
     *  纬度(单位是百万分之一度)
     *  // 南纬：0 - 90000000				实际值应为: 90*1000000 – dwLatidude
	    // 北纬：90000000 – 180000000		实际值应为: dwLatidude – 90*1000000
	    // 如: 120186268应为 (120186268 - 90*1000000)/1000000 即北纬30. 186268度
     */
    public int              nLatidude;
    /**
     *  高度,单位为米
     */
    public int              nAltitude;
    /**
     *  保留字段
     */
    public byte[]           szReserved = new byte[52];
}

