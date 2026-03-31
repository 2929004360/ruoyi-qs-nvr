package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警区域列表
 * @date 2022/01/07
 */
public class NET_GETSUMMARY_REGION_LIST_INFO extends NetSDKLib.SdkStructure {
    /**
     *  区域ID
     */
    public int              nRegionID;
    /**
     *  区域内人数统计值
     */
    public int              nPeopleNum;
    /**
     *  区域坐标个数
     */
    public int              nRegionPointNum;
    /**
     *  区域坐标
     */
    public NetSDKLib.NET_POINT[] stuRegionPoint = (NetSDKLib.NET_POINT[]) new NetSDKLib.NET_POINT().toArray(20);
    /**
     *  保留字节
     */
    public byte[]           szReserved = new byte[1024];
}

