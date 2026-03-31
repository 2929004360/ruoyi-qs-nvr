package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 单目标小图分割结果
*/
public class NET_GROUND_THING_DETECTION_MASK_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 宽度
    */
    public int              nWidth;
    /**
     * 高度
    */
    public int              nHeight;
    /**
     * 每个像素数值的位数，为8的倍数，用于将遮罩数据转换为具体的无符号整数
    */
    public int              nDepth;
    /**
     * 行跨距的字节数
    */
    public int              nStride;
    /**
     * 遮罩数据,参见结构体定义 {@link NET_GROUND_THING_DETECTION_DATA}
    */
    public NET_GROUND_THING_DETECTION_DATA stuData = new NET_GROUND_THING_DETECTION_DATA();
    /**
     * 区域id
    */
    public int              nRegionID;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[60];

    public NET_GROUND_THING_DETECTION_MASK_INFO() {
    }
}

