package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;


/**
 * 是否与其他目标关联
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 19:35
 */
public class HUMAN_ATTRIBUTES_INFO extends NetSDKLib.SdkStructure {
    /**
     *  上衣颜色
     */
    public int              emCoatColor;
    /**
     *  上衣类型
     */
    public int              emCoatType;
    /**
     *  裤子颜色
     */
    public int              emTrousersColor;
    /**
     *  裤子类型
     */
    public int              emTrousersType;
    /**
     *  是否戴帽子
     */
    public int              emHasHat;
    /**
     *  是否带包
     */
    public int              emHasBag;
    /**
     *  包围盒(8192坐标系)
     */
    public NET_RECT         stuBoundingBox = new NET_RECT();
    /**
     *  年龄
     */
    public int              nAge;
    /**
     *  性别
     */
    public int              emSex;
    /**
     *  角度
     */
    public int              emAngle;
    /**
     *  是否打伞
     */
    public int              emHasUmbrella;
    /**
     *  包类型
     */
    public int              emBag;
    /**
     *  上半身衣服图案
     */
    public int              emUpperPattern;
    /**
     *  头发样式
     */
    public int              emHairStyle;
    /**
     *  帽类型
     */
    public int              emCap;
    /**
     *  人体型心(不是包围盒中心), 0-8191相对坐标, 相对于大图
     */
    public NetSDKLib.NET_POINT stuHumanCenter = new NetSDKLib.NET_POINT();
    /**
     *  是否有反光背心;
     */
    public int              emHasVest;
    /**
     *  是否佩戴工牌
     */
    public int              emHasBadge;
    /**
     *  是否推婴儿车
     */
    public int              emHasBabyCarriage;
    /**
     *  是否虚检（背景误检，仅头，仅下半身都会判定为虚检）
     */
    public int              emIsErrorDetect;
    /**
     *  人体部位是否有头
     */
    public int              emHasHead;
    /**
     *  人体部位是否有下半身
     */
    public int              emHasDownBody;
    /**
     *  姿态置信度，质量评估总分0到100
     */
    public int              nAngleConf;
    /**
     *  上衣颜色置信度，质量评估总分0到100
     */
    public int              nUpColorConf;
    /**
     *  下衣颜色置信度，质量评估总分0到100
     */
    public int              nDownColorConf;
    /**
     *  性别置信度，质量评估总分0到100
     */
    public int              nGenderConf;
    /**
     *  年龄段置信度，质量评估总分0到100
     */
    public int              nAgeConf;
    /**
     *  帽子类型置信度，质量评估总分0到100
     */
    public int              nHatTypeConf;
    /**
     *  上衣种类置信度，质量评估总分0到100
     */
    public int              nUpTypeConf;
    /**
     *  下衣种类置信度，质量评估总分0到100
     */
    public int              nDownTypeConf;
    /**
     *  发型种类置信度，质量评估总分0到100
     */
    public int              nHairTypeConf;
    /**
     *  人体部位是否有头的置信度，质量评估总分0到100
     */
    public int              nHasHeadConf;
    /**
     *  人体部位是否有下半身置信度，质量评估总分0到100
     */
    public int              nHasDownBodyConf;
    /**
     *  制服类型置信度，质量评估总分0到100
     */
    public int              nUniformStyleConf;
    /**
     * 上衣类型，emCoatType实现和协议不一致，兼容处理，, 0:未知 1:长袖 2:短袖 3:长款大衣 4:夹克及牛仔服 5:T恤；, 6:运动装 7:羽绒服 8:衬衫 9:连衣裙 10:西装 11:毛衣 12:无袖 13:背心
    */
    public byte             nCoatType;
    /**
     *  预留字节
     */
    public byte             byReserved[] = new byte[3];
}

