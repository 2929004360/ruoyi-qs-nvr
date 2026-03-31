package com.ruoyi.dahua.lib.structure;
/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:47
 *  人脸信息
 */

import com.ruoyi.dahua.lib.NetSDKLib;


public class NET_SECURITYGATE_ALARM_FACE_INFO extends NetSDKLib.SdkStructure {
    /**
     *  性别,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_SEX_TYPE }
     */
    public int              emSex;                                // 性别
    public   int            nAge;                                 // 年龄
    /**
     *  人脸表情,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_EXPRESSION_TYPE }
     */
    public int              emEmotion;                            // 人脸表情
    /**
     *  是否戴眼镜,参考枚举{@link com.ruoyi.dahua.lib.enumeration.EM_SECURITYGATE_GLASSES_TYPE }
     */
    public int              emGlasses;                            // 是否戴眼镜
    /**
     *  口罩状态,参考枚举{@link NetSDKLib.EM_MASK_STATE_TYPE }
     */
    public int              emMask;                               // 是否带口罩
    /**
     *   是否有胡子,参考枚举{@link NetSDKLib.EM_BEARD_STATE_TYPE }
     */
    public int              emBeard;
    public int              nAttractive;                          // 魅力值
    /**
     *  
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     *  嘴巴状态 ,参考枚举{@link NetSDKLib.EM_MOUTH_STATE_TYPE }
     */
    public int              emMouth;
    /**
     *  眼睛状态 ,参考枚举{@link NetSDKLib.EM_EYE_STATE_TYPE }
     */
    public int              emEye;                                // 眼睛状态
    public   float          fTemperature;                         // 体温
    /**
     *  温度单位 ,参考枚举{@link NetSDKLib.EM_TEMPERATURE_UNIT }
     */
    public int              emTempUnit;                           // 温度单位
    /**
     *  体温类型 ,参考枚举{@link NetSDKLib.EM_TEMPERATURE_TYPE }
     */
    public int              emTempType;                           // 体温类型
    public byte[]           byReserved = new byte[1024];          // 保留字段
}

