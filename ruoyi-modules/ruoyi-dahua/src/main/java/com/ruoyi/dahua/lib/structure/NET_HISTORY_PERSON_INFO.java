package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 历史库人员信息
*/
public class NET_HISTORY_PERSON_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 人员唯一标识符,首次由服务端生成,区别于ID字段, 修改,删除操作时必填
    */
    public byte[]           szUID = new byte[32];
    /**
     * 性别,参见枚举定义 {@link NetSDKLib.EM_SEX_TYPE}
    */
    public int              emSexType;
    /**
     * 年龄, 0表示未知
    */
    public int              nAge;
    /**
     * 表情,参见枚举定义 {@link NetSDKLib.EM_DEV_EVENT_FACEDETECT_FEATURE_TYPE}
    */
    public int              emEmotion;
    /**
     * 眼镜状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GLASSES_STATE}
    */
    public int              emGlassesState;
    /**
     * 眼镜类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_GLASSES_TYPE}
    */
    public int              emGlassesType;
    public byte[]           szReserved1 = new byte[4];
    /**
     * 眼睛状态,参见枚举定义 {@link NetSDKLib.EM_EYE_STATE_TYPE}
    */
    public int              emEye;
    /**
     * 嘴巴状态,参见枚举定义 {@link NetSDKLib.EM_MOUTH_STATE_TYPE}
    */
    public int              emMouth;
    /**
     * 口罩状态,参见枚举定义 {@link NetSDKLib.EM_MASK_STATE_TYPE}
    */
    public int              emMask;
    /**
     * 胡子状态,参见枚举定义 {@link NetSDKLib.EM_BEARD_STATE_TYPE}
    */
    public int              emBeard;
    /**
     * 魅力值, -1表示无效, 0未识别，识别时范围1-100,得分高魅力高
    */
    public int              nAttractive;
    /**
     * 图片张数
    */
    public int              nFacePicNum;
    /**
     * 当前人员对应的图片信息,参见结构体定义 {@link NET_FACE_IMAGE_INFO}
    */
    public NET_FACE_IMAGE_INFO[] stuFacePicInfo = new NET_FACE_IMAGE_INFO[48];
    /**
     * 预留字节数
    */
    public byte[]           bReserved = new byte[2048];

    public NET_HISTORY_PERSON_INFO() {
        for(int i = 0; i < stuFacePicInfo.length; i++){
            stuFacePicInfo[i] = new NET_FACE_IMAGE_INFO();
        }
    }
}

