package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_WRITE_ON_THE_BOARD_DETECTION(板书检测事件)对应的规则配置
*/
public class CFG_WRITE_ON_THE_BOARD_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 报警联动,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 事件响应时间段,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];
    /**
     * 云台预置点编号   0~65535
    */
    public int              nPtzPresetId;
    /**
     * 警戒区端点个数
    */
    public int              nDetectRegionNum;
    /**
     * 警戒区， 多边形类型，多边形中每个端点的坐标归一化到[0,8192)区间。,参见结构体定义 {@link NetSDKLib.CFG_POLYLINE}
    */
    public NetSDKLib.CFG_POLYLINE[] stuDetectRegion = new NetSDKLib.CFG_POLYLINE[20];
    /**
     * 最短持续时间, 单位 s, 默认2，范围[1,3600]
    */
    public int              nMinDuration;
    /**
     * 灵敏度灵敏度,[1, 10], 默认值5
    */
    public int              nSensitivity;
    /**
     * 最大检测高度,单位cm
    */
    public int              nMaxHeight;
    /**
     * 最小检测高度,单位cm
    */
    public int              nMinHeight;
    /**
     * 系统联动特写模式, 为UNKNOWN时不下发,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CLOSE_UP_MODE}
    */
    public int              emCloseUpMode;
    /**
     * 联动的通道号
    */
    public int              nChannelID;
    /**
     * 联动码流类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LINKAGE_STREAM_TYPE}
    */
    public int              emStreamType;
    /**
     * 检测区顶点数
    */
    public int              nDesignedRegionPoint;
    /**
     * 检测区,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDesignedRegion = new NetSDKLib.CFG_POLYGON[2];
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[4096];

    public CFG_WRITE_ON_THE_BOARD_DETECTION_INFO() {
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYLINE();
        }
        for(int i = 0; i < stuDesignedRegion.length; i++){
            stuDesignedRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
    }
}

