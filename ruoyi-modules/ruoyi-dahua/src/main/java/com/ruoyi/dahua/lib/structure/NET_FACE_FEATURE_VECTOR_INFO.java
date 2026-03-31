package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 斜视状态
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class NET_FACE_FEATURE_VECTOR_INFO extends NetSDKLib.SdkStructure {
    /**
     *  人脸特征值在二进制数据中的偏移, 单位:字节
     */
    public int              nOffset;
    /**
     *  人脸特征值数据长度, 单位:字节
     */
    public int              nLength;
    /**
     *  用于标识特征值是否加密
     */
    public int              bFeatureEnc;
    /**
     *  保留字节
     */
    public byte             byReserved[] = new byte[28];
}

