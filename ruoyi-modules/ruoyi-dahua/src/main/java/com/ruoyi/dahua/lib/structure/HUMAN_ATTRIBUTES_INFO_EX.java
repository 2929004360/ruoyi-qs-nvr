package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 是否与其他目标关联
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class HUMAN_ATTRIBUTES_INFO_EX extends NetSDKLib.SdkStructure {
    /**
     *  是否戴帽子
     */
    public int              emHasRainCoat;
    /**
     *  口罩颜色
     */
    public int              emMaskColor;
    /**
     *  人体优选分数
     */
    public int              nQeScore;
    /**
     *  目标完整性，0-完整，非0-不完整，视频流时值有效。
     */
    public int              nIntegrality;
    /**
     *  是否与其他目标关联
     */
    public int              emExtRecordSource;
    /**
     * 帽子颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emCapColor;
    /**
     * 制服类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_UNIFORM_STYLE}
    */
    public int              emUniformStyle;
    /**
     * 人体清晰度分数取值范围 1~100，0为无效值
    */
    public int              nHumanClarity;
    /**
     * 人体完整度评分
    */
    public int              nHumanCompleteScore;
    /**
     * 表示当前人体是否曾经关联过目标
    */
    public int              bIsRelatedFace;
    /**
     * 上衣款式,参见枚举定义 {@link NetSDKLib.EM_COAT_TYPE}
    */
    public int              emCoatStyle;
    /**
     * 鞋子款式,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SHOES_TYPE}
    */
    public int              emShoesType;
    /**
     * 鞋子颜色,参见枚举定义 {@link NetSDKLib.EM_CLOTHES_COLOR}
    */
    public int              emShoesColor;
    /**
     * 年龄段,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_AGE_SEG}
    */
    public int              emAgeSeg;
    /**
     * 口罩状态, -1:未知, 0:未做识别, 1:不带口罩, 2:带口罩
    */
    public int              nMask;
    /**
     * 人员进出状态
    */
    public int              nHumanState;
    /**
     *  预留字节
     */
    public byte             byReserved[] = new byte[980];
}

