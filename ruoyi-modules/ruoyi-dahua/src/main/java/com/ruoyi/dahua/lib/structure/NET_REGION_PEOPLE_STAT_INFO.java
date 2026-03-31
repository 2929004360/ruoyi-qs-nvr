package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 检测区区域人数统计信息
 * @date 2022/01/07
 */
public class NET_REGION_PEOPLE_STAT_INFO extends NetSDKLib.SdkStructure {
    /**
     *  区域ID
     */
    public int              nRegionID;
    /**
     *  区域名称
     */
    public byte[]           szRegionName = new byte[128];
    /**
     *  区域顶点个数
     */
    public int              nRegionPointNum;
    /**
     *  区域顶点坐标
     */
    public NetSDKLib.NET_POINT[] stuRegionPoint = (NetSDKLib.NET_POINT[]) new NetSDKLib.NET_POINT().toArray(20);
    /**
     *  区域内人数
     */
    public int              nPeopleNum;
    /**
     *  保留字节
     */
    public byte[]           byReserved = new byte[1024];
}

