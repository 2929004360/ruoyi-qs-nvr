package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 数字类型详细信息
*/
public class NET_OCR_DETECTION_NUMBER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 小数点后有效位个数
    */
    public int              nValidbit;
    /**
     * 检测类型, 0: 未知, 1: 温度, 2: 称重
    */
    public int              nDetectType;
    /**
     * 数字上限报警
    */
    public float            fUpperLimit;
    /**
     * 数字下限报警
    */
    public float            fLowerLimit;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[128];

    public NET_OCR_DETECTION_NUMBER_INFO() {
    }
}

