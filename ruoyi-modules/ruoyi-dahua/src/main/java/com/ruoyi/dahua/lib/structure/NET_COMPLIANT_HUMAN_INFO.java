package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/** 
* @author 291189
* @description  人体过滤配置，包括人体和目标的属性 
* @origin autoTool
* @date 2023/05/29 11:40:44
*/
public class NET_COMPLIANT_HUMAN_INFO extends NetSDKLib.SdkStructure {
/** 
年龄过滤条件
*/
    public			NET_HUMAN_AGE_INFO stuHumanAge = new NET_HUMAN_AGE_INFO();
/** 
性别过滤条件
*/
    public			NET_HUMAN_SEX_INFO stuHumanSex = new NET_HUMAN_SEX_INFO();
/** 
表情过滤条件
*/
    public			NET_HUMAN_EMOTION_INFO stuHumanEmotion = new NET_HUMAN_EMOTION_INFO();
/** 
眼镜佩戴过滤条件
*/
    public			NET_HUMAN_GLASSES_INFO stuHumanGlasses = new NET_HUMAN_GLASSES_INFO();
/** 
口罩佩戴过滤条件
*/
    public			NET_HUMAN_MASK_INFO stuHumanMask = new NET_HUMAN_MASK_INFO();
/** 
胡子过滤条件
*/
    public			NET_HUMAN_BEARD_INFO stuHumanBeard = new NET_HUMAN_BEARD_INFO();
/** 
上衣类型过滤条件
*/
    public			NET_HUMAN_COAT_TYPE_INFO stuHumanCoatType = new NET_HUMAN_COAT_TYPE_INFO();
/** 
上衣颜色过滤条件
*/
    public			NET_HUMAN_COAT_COLOR_INFO stuHumanCoatColor = new NET_HUMAN_COAT_COLOR_INFO();
/** 
裤子类型过滤条件
*/
    public			NET_HUMAN_TROUSERS_TYPE_INFO stuHumanTrousersType = new NET_HUMAN_TROUSERS_TYPE_INFO();
/** 
裤子颜色过滤条件
*/
    public			NET_HUMAN_TROUSERS_COLOR_INFO stuHumanTrousersColor = new NET_HUMAN_TROUSERS_COLOR_INFO();
/** 
背包过滤条件
*/
    public			NET_HUMAN_HAS_BAG_INFO stuHumanHasBag = new NET_HUMAN_HAS_BAG_INFO();
/** 
雨伞过滤条件
*/
    public			NET_HUMAN_HAS_UMBRELLA_INFO stuHumanHasUmbrella = new NET_HUMAN_HAS_UMBRELLA_INFO();
/** 
穿戴雨披
*/
    public			NET_HUMAN_RAIN_COAT_INFO stuHumanRainCoat = new NET_HUMAN_RAIN_COAT_INFO();
/** 
帽子过滤条件
*/
    public			NET_HUMAN_HAS_HAT_INFO stuHumanHasHat = new NET_HUMAN_HAS_HAT_INFO();
/** 
安全帽合规性检测信息
*/
    public			NET_HUMAN_HELMET_INFO stuHumanHelmet = new NET_HUMAN_HELMET_INFO();
/** 
安全背心合规性检测信息
*/
    public			NET_HUMAN_VEST_INFO stuHumanVest = new NET_HUMAN_VEST_INFO();
/** 
发型过滤条件
*/
    public			NET_HUMAN_HAIR_STYLE_INFO stuHumanHairStyle = new NET_HUMAN_HAIR_STYLE_INFO();
/** 
人体拍摄角度
*/
    public			NET_HUMAN_ANGLE_INFO stuHumanAngle = new NET_HUMAN_ANGLE_INFO();
/** 
抱小孩
*/
    public			NET_HUMAN_HOLD_BABY_INFO stuHumanHoldBaby = new NET_HUMAN_HOLD_BABY_INFO();
/** 
保留字节
*/
    public			byte[]         szReserved = new byte[1024];

public NET_COMPLIANT_HUMAN_INFO(){
}
}

