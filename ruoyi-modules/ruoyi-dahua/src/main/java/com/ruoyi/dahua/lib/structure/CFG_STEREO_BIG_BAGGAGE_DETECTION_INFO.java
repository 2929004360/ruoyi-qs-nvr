package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_STEREO_BIG_BAGGAGE_DETECTION (大件行李箱检测)对应的规则配置
*/
public class CFG_STEREO_BIG_BAGGAGE_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 规则名称,不同规则不能重名
    */
    public byte[]           szRuleName = new byte[128];
    /**
     * 规则使能
    */
    public byte             bRuleEnable;
    /**
     * 相应物体类型个数
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 报警联动,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 事件响应时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];
    /**
     * 云台预置点编号  0~65535
    */
    public int              nPtzPresetId;
    /**
     * 检测区域顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
    /**
     * 最大检测高度,单位：cm
    */
    public int              nMaxHeight;
    /**
     * 最小检测高度,单位：cm
    */
    public int              nMinHeight;
    /**
     * 最短持续时间,单位：秒,范围：1-60
    */
    public int              nMinDuration;
    /**
     * 重复报警间隔,单位：秒,为0不重复报警,范围：0-300
    */
    public int              nReportInterval;
    /**
     * 灵敏度,范围：1-10
    */
    public int              nSensitivity;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[4092];

    public CFG_STEREO_BIG_BAGGAGE_DETECTION_INFO() {
        for(int i = 0; i < szObjectTypes.length; i++){
            szObjectTypes[i] = new BYTE_ARRAY_128();
        }
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
    }
}

