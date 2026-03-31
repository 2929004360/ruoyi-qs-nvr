package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 船只物体信息扩展
*/
public class NET_BOAT_OBJECT_EXTERN extends NetSDKLib.SdkStructure
{
    /**
     * AIS融合目标信息,参见结构体定义 {@link NET_AIS_FUSE_OBJECT}
    */
    public NET_AIS_FUSE_OBJECT stuAISFuseObject = new NET_AIS_FUSE_OBJECT();
    /**
     * AIS融合目标信息扩展,参见结构体定义 {@link NET_AIS_FUSE_OBJECT_EX}
    */
    public NET_AIS_FUSE_OBJECT_EX stuAISFuseObjectEx = new NET_AIS_FUSE_OBJECT_EX();
    /**
     * 抓拍图序号
    */
    public int[]            nIndexInDataByBoatImage = new int[8];
    /**
     * 船只在检测过程中的抓拍图
    */
    public int              nBoatImageNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[44];

    public NET_BOAT_OBJECT_EXTERN() {
    }
}

