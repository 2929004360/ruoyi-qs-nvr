package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 水速信息
*/
public class NET_WATER_SPEED_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 水流速，单位m/s
    */
    public float            fSpeed;
    /**
     * 测速点距河岸距离，单位m
    */
    public float            fDistance;
    /**
     * 流速向量的像素坐标起始点，归一化到 1024,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuStartPoint = new NET_POINT_EX();
    /**
     * 流速向量的像素坐标终点，归一化到 1024,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX     stuEndPoint = new NET_POINT_EX();
    /**
     * 测速点的物理坐标，单位m,参见结构体定义 {@link NET_3DFLOAT_POINT}
    */
    public NET_3DFLOAT_POINT stuPhysicalCoordinates = new NET_3DFLOAT_POINT();
    /**
     * 保留字节,留待扩展
    */
    public byte[]           szReserved = new byte[80];

    public NET_WATER_SPEED_INFO() {
    }
}

