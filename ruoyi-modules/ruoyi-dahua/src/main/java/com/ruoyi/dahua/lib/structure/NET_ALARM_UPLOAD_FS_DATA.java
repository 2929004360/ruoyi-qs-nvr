package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标数据
*/
public class NET_ALARM_UPLOAD_FS_DATA extends NetSDKLib.SdkStructure
{
    /**
     * 年龄,-1表示该字段数据无效
    */
    public int              nAge;
    /**
     * 目标特征数组有效个数,与 emFeature 结合使用
    */
    public int              nFeatureValidNum;
    /**
     * 目标特征数组,与 nFeatureValidNum 结合使用,参见枚举定义 {@link NetSDKLib.EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE}
    */
    public int[]            emFeature = new int[32];
    /**
     * 性别,参见枚举定义 {@link NetSDKLib.EM_DEV_EVENT_FACEDETECT_SEX_TYPE}
    */
    public int              emSex;
    /**
     * 口罩状态,参见枚举定义 {@link NetSDKLib.EM_MASK_STATE_TYPE}
    */
    public int              emMask;
    /**
     * 胡子状态,参见枚举定义 {@link NetSDKLib.EM_BEARD_STATE_TYPE}
    */
    public int              emBeard;
    /**
     * 戴眼镜状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GLASS_STATE_TYPE}
    */
    public int              emGlass;
    /**
     * 人脸抓拍质量分数, 取值范围 0~10000
    */
    public int              nFaceQuality;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_ALARM_UPLOAD_FS_DATA() {
    }
}

