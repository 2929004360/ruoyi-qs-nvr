package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_MAN_STAND_DETECTION(立体视觉站立事件), EVENT_IVS_NEAR_DISTANCE_DETECTION(近距离接触事件)对应的规则配置
*/
public class CFG_STEREO_VISION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 信息
    */
    public byte[]           szRuleName = new byte[128];
    /**
     * 规则名称,不同规则不能重名
    */
    public int              bRuleEnable;
    /**
     * 规则使能
    */
    public byte             bTrackEnable;
    /**
     * 触发跟踪使能,仅对绊线，入侵规则有效
    */
    public byte[]           bReserved1 = new byte[3];
    /**
     * 保留字段
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型个数
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 相应物体类型列表,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 报警联动,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];
    /**
     * 事件响应时间段
    */
    public int              nPtzPresetId;
    /**
     * 检测区域顶点数
    */
    public int              nDetectLinePoint;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.CFG_POLYLINE}
    */
    public NetSDKLib.CFG_POLYLINE[] stuDetectLine = new NetSDKLib.CFG_POLYLINE[20];
    /**
     * 检测区域顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区域坐标,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
    /**
     * 异常阈值
    */
    public int              nThreshold;
    /**
     * 人数异常检测方式（仅用于区域内人数统计）, 0-小于等于阈值报警 1-大于等于阈值报警
    */
    public int              nDetectType;
    /**
     * 灵敏度,[0-100]
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
     * 最短持续时间, 单位:  秒
    */
    public int              nMinDuration;
    /**
     * 报警时间间隔，单位秒
    */
    public int              nReportInterval;
    /**
     * 保留字节
    */
    public byte[]           bReserved = new byte[504];

    public CFG_STEREO_VISION_INFO() {
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
        for(int i = 0; i < stuDetectLine.length; i++){
            stuDetectLine[i] = new NetSDKLib.CFG_POLYLINE();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
    }
}

