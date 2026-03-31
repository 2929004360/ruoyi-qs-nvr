package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_HUMANTRAIT(人员检测规则)对应的规则配置
*/
public class CFG_HUMANTRAIT_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 规则名称,不同规则不能重名
    */
    public byte[]           szRuleName = new byte[128];
    /**
     * 规则使能
    */
    public int              bRuleEnable;
    /**
     * 相应物体类型个数
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 云台预置点编号	0~65535
    */
    public int              nPtzPresetId;
    /**
     * 报警联动,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 事件响应时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];
    /**
     * 支持的目标检测类型个数
    */
    public int              nHumanFaceTypeNum;
    /**
     * 支持的目标检测类型列表,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VIDEO_ANALYSE_HUMANFACE_TYPE}
    */
    public int[]            emHumanFaceType = new int[8];
    /**
     * 最短触发时间,单位：秒
    */
    public int              nMinDuration;
    /**
     * 触发报警的目标个数
    */
    public int              nTriggerTargets;
    /**
     * 灵敏度,越高越容易检测, 0-10
    */
    public int              nSensitivity;
    /**
     * 是否使用尺寸过滤器
    */
    public int              bSizeFileter;
    /**
     * 规则特定的尺寸过滤器，提高判断精度,参见结构体定义 {@link NetSDKLib.CFG_SIZEFILTER_INFO}
    */
    public NetSDKLib.CFG_SIZEFILTER_INFO stuSizeFileter = new NetSDKLib.CFG_SIZEFILTER_INFO();
    /**
     * 是否开启目标属性识别
    */
    public int              bFeatureEnable;
    /**
     * 在目标属性开启前提下，如果目标图像质量太差，是否不上报属性  true-图像太差不上报属性;false-图像很差也上报属性(可能会非常不准，影响用户体验)
    */
    public int              bFeatureFilter;
    /**
     * 目标图片质量阈值,和bFeatureFilter一起使用
    */
    public int              nMinQuality;
    /**
     * 需要检测的目标属性个数
    */
    public int              nFaceFeatureNum;
    /**
     * 需检测的目标属性,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_FACEFEATURE_TYPE}
    */
    public int[]            emFaceFeatureType = new int[32];
    /**
     * 保留字节
    */
    public byte[]           bReserved = new byte[512];

    public CFG_HUMANTRAIT_INFO() {
        for(int i = 0; i < szObjectTypes.length; i++){
            szObjectTypes[i] = new BYTE_ARRAY_128();
        }
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
    }
}

