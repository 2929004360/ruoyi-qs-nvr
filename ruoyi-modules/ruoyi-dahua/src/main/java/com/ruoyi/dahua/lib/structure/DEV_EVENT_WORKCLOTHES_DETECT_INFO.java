package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型EVENT_IVS_WORKCLOTHES_DETECT(工装 ( 安全帽 / 工作服等)检测事件)对应的数据块描述信息
 * @date 2023/04/21 16:09:59
 */
public class DEV_EVENT_WORKCLOTHES_DETECT_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲 1:开始 2:停止
     */
    public int              nAction;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
     */
    public double           PTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件ID
     */
    public int              nEventID;
    /**
     * 智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
     */
    public int              emClassType;
    /**
     * 智能事件规则编号，用于标示哪个规则触发的事件
     */
    public int              nRuleID;
    /**
     * 物体ID
     */
    public int              nObjectID;
    /**
     * 事件组ID，一次检测的多个nGroupID相同
     */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数,一次检测的多个nCountInGroup相同
     */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号，从1开始
     */
    public int              nIndexInGroup;
    /**
     * 全景大图信息
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 小图信息
     */
    public NetSDKLib.HUMAN_IMAGE_INFO stuHumanImage = new NetSDKLib.HUMAN_IMAGE_INFO();
    /**
     * 安全帽属性
     */
    public NetSDKLib.NET_HELMET_ATTRIBUTE stuHelmetAttribute = new NetSDKLib.NET_HELMET_ATTRIBUTE();
    /**
     * 工作服属性
     */
    public NetSDKLib.NET_WORKCLOTHES_ATTRIBUTE stuWorkClothesAttribute = new NetSDKLib.NET_WORKCLOTHES_ATTRIBUTE();
    /**
     * 工作裤颜色
     */
    public NetSDKLib.NET_WORKPANTS_ATTRIBUTE stuWorkPantsAttribute = new NetSDKLib.NET_WORKPANTS_ATTRIBUTE();
    /**
     * 不规范报警类型 0-未知 1-防护服不规范 2: 工作服不规范3:安全帽不规范4:安全帽和工作服不规范
     */
    public int              nAlarmType;
    /**
     * 事件关联ID。应用场景是同一个物体或者同一张图片做不同分析，产生的多个事件的SourceID相同
     * 格式：类型+时间+序列号，其中类型2位，时间14位，序列号5位
     * 类型：02-图像   时间：YYYYMMDDhhmmss   序列号：00001
     */
    public byte[]           szSourceID = new byte[32];
    /**
     * null {@link com.ruoyi.dahua.lib.enumeration.EM_EVENT_WORKCLOTHES_RULE_TYPE}
     */
    public int              emRuleType;
    /**
     * 图片信息数组
     */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 图片信息个数
     */
    public int              nImageInfoNum;
    /**
     * 普通帽子相关属性状态信息
     */
    public NET_NORMALHAT_ATTRIBUTE stuNormalHat = new NET_NORMALHAT_ATTRIBUTE();
    /**
     * 口罩相关属性状态信息
     */
    public NET_MASK_ATTRIBUTE stuMask = new NET_MASK_ATTRIBUTE();
    /**
     * 围裙相关属性状态信息
     */
    public NET_APRON_ATTRIBUTE stuApron = new NET_APRON_ATTRIBUTE();
    /**
     * 手套相关属性状态信息
     */
    public NET_GLOVE_ATTRIBUTE stuGlove = new NET_GLOVE_ATTRIBUTE();
    /**
     * 靴子相关属性状态信息
     */
    public NET_BOOT_ATTRIBUTE stuBoot = new NET_BOOT_ATTRIBUTE();
    /**
     * 鞋套相关属性状态信息
     */
    public NET_SHOESCOVER_ATTRIBUTE stuShoesCover = new NET_SHOESCOVER_ATTRIBUTE();
    /**
     * 无帽子相关属性状态信息
     */
    public NET_NOHAT_ATTRIBUTE stuNoHat = new NET_NOHAT_ATTRIBUTE();
    /**
     * 防护面罩相关属性状态信息
     */
    public NET_PROHELMET_ATTRIBUTE stuProhelmet = new NET_PROHELMET_ATTRIBUTE();
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 防火衣相关属性状态信息
     */
    public NET_FIREPROOF_CLOTHES stuFireProofClothes = new NET_FIREPROOF_CLOTHES();
    /**
     * 物体信息数据,{@link NetSDKLib.NET_MSG_OBJECT_EX2}
     */
    public Pointer          pstObjectInfo;
    /**
     * 物体信息数
     */
    public int              nObjectNum;
    /**
     * 眼镜相关属性状态信息
     */
    public NET_GLASSES_RELATED_INFO stuGlassesInfo = new NET_GLASSES_RELATED_INFO();
    /**
     * 呼吸面罩相关属性状态信息
     */
    public NET_BREATHING_MASK_INFO stuBreathingMaskInfo = new NET_BREATHING_MASK_INFO();
    /**
     * 防护服相关属性状态信息
     */
    public NET_PROTECTIVE_SUIT_INFO stuProtectiveSuitInfo = new NET_PROTECTIVE_SUIT_INFO();
    /**
     * 制服相关属性状态信息
     */
    public NET_UNIFORM_INFO stuUniformInfo = new NET_UNIFORM_INFO();
    /**
     * 安全绳相关属性状态信息
     */
    public NET_SAFETY_ROPE_INFO stuSafetyRopeInfo = new NET_SAFETY_ROPE_INFO();
    /**
     * 安全带相关属性状态信息
     */
    public NET_SAFE_BELT_INFO stuSafeBeltInfo = new NET_SAFE_BELT_INFO();
    /**
     * 反光背心相关属性状态信息
     */
    public NET_VEST_INFO    stuVestInfo = new NET_VEST_INFO();
    /**
     * 劳保鞋相关属性状态信息
     */
    public NET_SAFETY_SHOES_INFO stuSafetyShoesInfo = new NET_SAFETY_SHOES_INFO();
    /**
     * 防割护腕相关属性状态信息
     */
    public NET_WRIST_GUARD_INFO stuWristGuardInfo = new NET_WRIST_GUARD_INFO();
    /**
     * 报警方式, 0:未知, 1:有不合规项即报警 2:所有合规才报警
     */
    public int              nLegalAlarmType;
    /**
     * 是否上报了Helmet属性
    */
    public byte             bHelmet;
    /**
     * 是否上报了Clothes属性
    */
    public byte             bClothes;
    /**
     * 是否上报了WorkPants属性
    */
    public byte             bWorkPants;
    /**
     * 是否上报了NormalHat属性
    */
    public byte             bNormalHat;
    /**
     * 是否上报了Mask属性
    */
    public byte             bMask;
    /**
     * 是否上报了Apron属性
    */
    public byte             bApron;
    /**
     * 是否上报了Glove属性
    */
    public byte             bGlove;
    /**
     * 是否上报了Boot属性
    */
    public byte             bBoot;
    /**
     * 是否上报了ShoesCover属性
    */
    public byte             bShoesCover;
    /**
     * 是否上报了NoHat属性
    */
    public byte             bNoHat;
    /**
     * 是否上报了Prohelmet属性
    */
    public byte             bProhelmet;
    /**
     * 是否上报了Glasses属性
    */
    public byte             bGlasses;
    /**
     * 是否上报了FireProofClothes属性
    */
    public byte             bFireProofClothes;
    /**
     * 是否上报了ProtectiveSuit属性
    */
    public byte             bProtectiveSuit;
    /**
     * 是否上报了Uniform属性
    */
    public byte             bUniform;
    /**
     * 是否上报了BreathingMask属性
    */
    public byte             bBreathingMask;
    /**
     * 是否上报了SafeBelt属性
    */
    public byte             bSafeBelt;
    /**
     * 是否上报了Vest属性
    */
    public byte             bVest;
    /**
     * 是否上报了SafetyShoes属性
    */
    public byte             bSafetyShoes;
    /**
     * 是否上报了WristGuard属性
    */
    public byte             bWristGuard;
    /**
     * 是否上报了SafetyRope属性
    */
    public byte             bSafetyRope;
    /**
     * 字节对齐
    */
    public byte[]           szResvered1 = new byte[3];
    /**
     * 预置点ID,如果普通IPC则为0
    */
    public int              nPresetID;
    /**
     * 事件触发的预置点名称
    */
    public byte[]           szPresetName = new byte[64];
    /**
     * 职员工装,参见结构体定义 {@link NET_EMPLOYEE_CLOTHES_INFO}
    */
    public Pointer          pstuEmployeeClothes;
    /**
     * 预留字节
     */
    public byte[]           byReserved = new byte[236-2*NetSDKLib.POINTERSIZE];

    public DEV_EVENT_WORKCLOTHES_DETECT_INFO() {
        for (int i = 0; i < stuImageInfo.length; i++) {
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

