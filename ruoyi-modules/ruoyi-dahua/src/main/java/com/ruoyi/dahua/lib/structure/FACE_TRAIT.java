package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 人脸属性
 * @date 2021/8/24 14:14
 */
public class FACE_TRAIT extends NetSDKLib.SdkStructure {
    /**参考枚举 EM_SEX_TYPE */
    public int              emSex;                                // 性别
    public int              nAge;                                 // 年龄
   /**参考枚举 EM_HAS_GLASS */
    public int              emGlass;                              // 眼镜
    /**参考枚举  EM_BEARD_STATE_TYPE */
    public int              emBeard;                              // 胡子
    /** */
    public byte[]           szReserved = new byte[4];
    /**参考枚举 EM_MASK_STATE_TYPE */
    public	int              emMask;                               // 口罩
    /**参考枚举 EM_FACE_TRAIT_EMOTION_TYPE */
    public int              emEmotion;                            // 表情
    public NET_TIME_EX      stuSnapTime = new NET_TIME_EX();      // 抓拍时间
    /**参考枚举  EM_FACE_TRAIT_ALIVE_TYPE*/
    public int              emAlive;                              // 活体信息
    public byte[]           byReserved = new  byte[1020];         // 预留字段

    @Override
    public String toString() {
        return "FACE_TRAIT{" +
                "emSex=" + emSex +
                ", nAge=" + nAge +
                ", emGlass=" + emGlass +
                ", emBeard=" + emBeard +
                ", emMask=" + emMask +
                ", emEmotion=" + emEmotion +
                ", stuSnapTime=" + stuSnapTime +
                ", emAlive=" + emAlive +
                '}';
    }
}

