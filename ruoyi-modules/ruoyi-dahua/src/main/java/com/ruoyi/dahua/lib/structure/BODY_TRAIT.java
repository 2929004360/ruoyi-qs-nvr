package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description
 * @date 2021/8/24 14:35
 */
public class BODY_TRAIT extends NetSDKLib.SdkStructure {
    /** EM_SEX_TYPE */
    public int              emSex;                                // 性别
    /** EM_AGE_GROUP_TYPE  */
    public int              emAgeGroup;                           // 年龄组
    /** EM_COAT_TYPE */
    public int              emCoatType;                           // 上衣类型
    /** EM_TROUSERS_TYPE  */
    public int              emTrousersType;                       // 裤子类型
    /**EM_ANGLE_TYPE  */
    public int              emAngle;                              // 人体角度
    /** EM_CLOTHES_PATTERN */
    public int              emUpperPattern;                       // 上衣模式
    /**EM_UNIFIED_COLOR_TYPE  */
    public int              emTrousersColor;                      // 裤子颜色
    /** EM_HAS_UMBRELLA */
    public int              emUmbrella;                           // 打伞
    /** EM_UNIFIED_COLOR_TYPE */
    public int              emUmbrellaColor;                      // 雨伞颜色
    /** EM_HAS_HOLD */
    public int              emHold;                               // 胸前抱东西
    /**  EM_CLOTHES_PATTERN*/
    public int              emTrousersPattern;                    // 裤子模式
    /** EM_HAT_STYLE */
    public  int             emHatType;                            // 帽子款式
    /** EM_UNIFIED_COLOR_TYPE */
    public int              emHatColor;                           // 帽子颜色
    /** EM_UPPER_TYPE */
    public int              emUpperType;                          // 上衣类型
    /** EM_UNIFIED_COLOR_TYPE */
    public int              emCoatColor;                          // 上衣颜色
    /**EM_HAIR_STYLE  */
    public int              emHairStyle;                          // 发型
    /** EM_UNIFIED_COLOR_TYPE */
    public int              emHairColor;                          // 头发颜色
    /** EM_SHOES_TYPE */
    public int              emShoesType;                          // 鞋子款式
    /** EM_UNIFIED_COLOR_TYPE*/
    public int              emShoesColor;                         // 鞋子颜色
    /** EM_BAG_TYPE */
    public int              emBagType;                            // 箱包款式
    /**  EM_UNIFIED_COLOR_TYPE */
    public int              emBagColor;                           // 箱包颜色
    /** EM_MASK_TYPE */
    public int              emMaskType;                           // 口罩
    /** EM_UNIFIED_COLOR_TYPE */
    public int              emMaskColor;                          // 口罩颜色
    public NET_TIME_EX      stuSnapTime = new NET_TIME_EX();      // 抓图时间
    public  byte[]          szReserved = new byte[1024];          // 预留字段

    @Override
    public String toString() {
        return "BODY_TRAIT{" +
                "emSex=" + emSex +
                ", emAgeGroup=" + emAgeGroup +
                ", emCoatType=" + emCoatType +
                ", emTrousersType=" + emTrousersType +
                ", emAngle=" + emAngle +
                ", emUpperPattern=" + emUpperPattern +
                ", emTrousersColor=" + emTrousersColor +
                ", emUmbrella=" + emUmbrella +
                ", emUmbrellaColor=" + emUmbrellaColor +
                ", emHold=" + emHold +
                ", emTrousersPattern=" + emTrousersPattern +
                ", emHatType=" + emHatType +
                ", emHatColor=" + emHatColor +
                ", emUpperType=" + emUpperType +
                ", emCoatColor=" + emCoatColor +
                ", emHairStyle=" + emHairStyle +
                ", emHairColor=" + emHairColor +
                ", emShoesType=" + emShoesType +
                ", emShoesColor=" + emShoesColor +
                ", emBagType=" + emBagType +
                ", emBagColor=" + emBagColor +
                ", emMaskType=" + emMaskType +
                ", emMaskColor=" + emMaskColor +
                ", stuSnapTime=" + stuSnapTime +
                '}';
    }
}

