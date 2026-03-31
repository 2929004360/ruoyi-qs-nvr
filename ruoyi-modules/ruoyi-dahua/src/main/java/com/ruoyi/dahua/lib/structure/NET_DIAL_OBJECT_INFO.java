package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 检测到的仪表目标信息
*/
public class NET_DIAL_OBJECT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 仪表类型
    */
    public byte[]           szObjectType = new byte[32];
    /**
     * 检测结果
    */
    public byte[]           szObjectDialResult = new byte[2048];
    /**
     * 目标包围框 矩形类型，8192坐标系,参见结构体定义 {@link NET_RECT_EX}
    */
    public NET_RECT_EX      stuObjectBoundingBox = new NET_RECT_EX();
    /**
     * 目标置信度，0~100
    */
    public int              nObjectConfidence;
    /**
     * 预留字节
    */
    public byte[]           szReversed = new byte[1020];

    public NET_DIAL_OBJECT_INFO() {
    }
}

