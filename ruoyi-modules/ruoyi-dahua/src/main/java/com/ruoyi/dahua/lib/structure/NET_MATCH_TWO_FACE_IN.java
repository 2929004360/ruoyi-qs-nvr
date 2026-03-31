package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 47081
 * @version 1.0
 * @description {@link NetSDKLib#CLIENT_MatchTwoFaceImage}的入参
 * @date 2020/10/19
 */
public class NET_MATCH_TWO_FACE_IN extends NetSDKLib.SdkStructure {
    public int              dwSize;
    /**
     * 原图
     */
    public NET_IMAGE_COMPARE_INFO stuOriginalImage;
    /**
     * 比较图
     */
    public NET_IMAGE_COMPARE_INFO stuCompareImage;
    /**
     * 两张人脸图片数据
     */
    public Pointer          pSendBuf;
    /**
     * 数据大小
     */
    public int              dwSendBufLen;
    /**
     * 比对方式, 不填默认图片对比,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_MATCH_TWO_FACE_TYPE}
    */
    public int              emType;
    /**
     * 比对的人脸特征值, 两维数组分别为需要对比的两个特征值,使用szFeature时emType应传EM_MATCH_TWO_FACE_TYPE_EIGENVALUE
    */
    public BYTE_ARRAY_1024[] szFeature = new BYTE_ARRAY_1024[2];
    /**
     * 比对的人脸特征值传入长度,两个维度数值分别对应szFeature的两个维度
    */
    public int[]            nFeatureLen = new int[2];

    public NET_MATCH_TWO_FACE_IN() {
        this.dwSize = this.size();
    }
}

