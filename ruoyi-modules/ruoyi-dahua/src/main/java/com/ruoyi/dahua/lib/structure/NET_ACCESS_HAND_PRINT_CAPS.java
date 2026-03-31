package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * AccessHandPrint能力集
*/
public class NET_ACCESS_HAND_PRINT_CAPS extends NetSDKLib.SdkStructure
{
    /**
     * 每次最大插入量
    */
    public int              nMaxInsertRate;
    /**
     * 掌纹（静脉）图片最小尺寸,单位KB
    */
    public int              nMinHandPrintPhotoSize;
    /**
     * 掌纹（静脉）图片最大尺寸，单位KB
    */
    public int              nMaxHandPrintPhotoSize;
    /**
     * 每个用户最多支持多少组掌纹（静脉）
    */
    public int              nMaxHandPrintGroup;
    /**
     * 目标识别算法提供标识，0未知，1私有
    */
    public int              nRecognitionAlgorithmVender;
    /**
     * 算法(模型)版本号
    */
    public int              nRecognitionVersion;
    /**
     * 掌纹（静脉）存储上限
    */
    public int              nMaxHandPrintCount;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[124];

    public NET_ACCESS_HAND_PRINT_CAPS() {
    }
}

