package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_RIOTERDETECTION(聚众事件)对应的规则配置
*/
public class CFG_RIOTER_INFO extends NetSDKLib.SdkStructure
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
     * 聚集所占区域面积百分比
    */
    public byte             bAreaPercent;
    /**
     * 灵敏度，取值1-10，值越小灵敏度越低，对应人群的密集程度越高(取代bAreaPercent)
    */
    public byte             bSensitivity;
    /**
     * 保留字段
    */
    public byte             bReserved;
    /**
     * 相应物体类型个数
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型列表
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 最短持续时间	单位：秒，0~65535
    */
    public int              nMinDuration;
    /**
     * 检测区域顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
    /**
     * 报警联动,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 事件响应时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];
    /**
     * 云台预置点编号	0~65535
    */
    public int              nPtzPresetId;
    /**
     * 报告时间间隔,单位秒 [1,600] 默认0表示不重复报警
    */
    public int              nReportInterval;
    /**
     * 最小聚集区域矩形框，分别对应矩形框的左上和右下点，矩形框的实际面积表示触发报警的最小人群聚集面积,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuMinDetectRect = new NetSDKLib.CFG_POLYGON[2];
    /**
     * 跟踪持续时间,0秒:一直跟踪,1~300秒:跟踪持续时间
    */
    public int              nTrackDuration;
    /**
     * 触发跟踪使能
    */
    public byte             bTrackEnable;
    /**
     * 聚集人数阀值, 聚集人数超过此值，开始报警
    */
    public int              nRioterThreshold;
    /**
     * 当前规则的检测模式有效个数
    */
    public int              nDetectModesNum;
    /**
     * 当前规则的检测模式
    */
    public int[]            nDetectModes = new int[64];
    /**
     * 人数聚集的检测方式，对RioterThreshold参数的判断方式, "COMPARE_MODE_MORE" 大于, "COMPARE_MODE_LESS" 小于 , "COMPARE_MODE_NOT_EQUAL" 不等于, "COMPARE_MODE_EQUAL" 等于, "COMPARE_MODE_NONE" 不做比较,直接输出人数
    */
    public byte[]           szCompareMode = new byte[32];
    /**
     * 比较模式间的组合方式, 0 组合模式不生效, 1 采用"与"的方式组合, 2 采用"或"的方式组合
    */
    public int              nCompositeMode;
    /**
     * 组合模式时, 聚集人数阈值, CompositeMode不为0时生效
    */
    public int              nRioterSecondThreshold;
    /**
     * 人数聚集的检测方式, 值同szCompareMode
    */
    public byte[]           szSecondCompareMode = new byte[32];
    /**
     * 对应1个或多个排除区域,参见结构体定义 {@link NET_EXCLUDE_REGION_INFO}
    */
    public NET_EXCLUDE_REGION_INFO[] stuExcludeRegions = new NET_EXCLUDE_REGION_INFO[20];
    /**
     * 主动巡视配置信息,参见结构体定义 {@link NET_PARTOL_LINKAGE_INFO}
    */
    public NET_PARTOL_LINKAGE_INFO stuPatrolLinkage = new NET_PARTOL_LINKAGE_INFO();
    /**
     * 排除区域有效个数
    */
    public int              nExcludeRegionsNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public CFG_RIOTER_INFO() {
        for(int i = 0; i < szObjectTypes.length; i++){
            szObjectTypes[i] = new BYTE_ARRAY_128();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
        for(int i = 0; i < stuMinDetectRect.length; i++){
            stuMinDetectRect[i] = new NetSDKLib.CFG_POLYGON();
        }
        for(int i = 0; i < stuExcludeRegions.length; i++){
            stuExcludeRegions[i] = new NET_EXCLUDE_REGION_INFO();
        }
    }
}

