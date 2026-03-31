package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * AIS融合目标信息
*/
public class NET_AIS_FUSE_OBJECT extends NetSDKLib.SdkStructure
{
    /**
     * 融合ID，由算法产生
    */
    public int              nId;
    /**
     * 目标类型置信度1-100，数值越大表示可信度越高,0表示未知
    */
    public int              nConfidence;
    /**
     * 目标经度，采用"正负经度，单位：度,正为东经，负为西经，取值范围[-180,180]
    */
    public double           dbLongitude;
    /**
     * 纬度，单位：度,正为北纬，负为南纬，取值范围[-90,90]
    */
    public double           dbLatitude;
    /**
     * 航向角 以正北方向为基准输出船舶运动方向同正北方向的角度； 范围 0~360，顺时针正,单位为度；
    */
    public double           dbHeadingAngle;
    /**
     * 融合目标对应的标定后的设备坐标系,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuObjectPosition = new NET_POINT_EX();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_AIS_FUSE_OBJECT() {
    }
}

