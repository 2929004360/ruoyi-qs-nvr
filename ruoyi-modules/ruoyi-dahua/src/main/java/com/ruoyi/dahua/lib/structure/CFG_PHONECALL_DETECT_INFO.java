package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_PHONECALL_DETECT (打电话报警事件)对应的规则配置
*/
public class CFG_PHONECALL_DETECT_INFO extends NetSDKLib.SdkStructure
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
     * 检测区顶点数
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
    /**
     * 最短持续时间，单位秒
    */
    public int              nMinDuration;
    /**
     * 灵敏度
    */
    public int              nSensitivity;
    /**
     * 报告时间间隔
    */
    public int              nReportInterval;
    /**
     * 是否包含尺寸过滤器
    */
    public int              bSizeFilterEnable;
    /**
     * 规则特定的尺寸过滤器，为提高规则判断精度,参见结构体定义 {@link NetSDKLib.CFG_SIZEFILTER_INFO}
    */
    public NetSDKLib.CFG_SIZEFILTER_INFO stuSizeFilter = new NetSDKLib.CFG_SIZEFILTER_INFO();
    /**
     * 关联目标, 抓拍目标使能开关，false:关闭，true：开启，默认关闭
    */
    public int              bRelateFace;
    /**
     * 保留字节
    */
    public byte[]           byReserved = new byte[3608];

    public CFG_PHONECALL_DETECT_INFO() {
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
    }
}

