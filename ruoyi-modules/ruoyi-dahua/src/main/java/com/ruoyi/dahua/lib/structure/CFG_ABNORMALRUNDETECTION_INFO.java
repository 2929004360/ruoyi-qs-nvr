package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_ABNORMALRUNDETECTION(异常奔跑事件)对应的规则配置
*/
public class CFG_ABNORMALRUNDETECTION_INFO extends NetSDKLib.SdkStructure
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
    public byte             bSensitivity;
    /**
     * 灵敏度, 取值1-10，值越小灵敏度越低
    */
    public byte             bTrackEnable;
    /**
     * 触发跟踪使能,仅对绊线，入侵规则有效
    */
    public byte[]           bReserved = new byte[1];
    /**
     * 保留字段
    */
    public int              nObjectTypeNum;
    /**
     * 相应物体类型个数
    */
    public BYTE_ARRAY_128[] szObjectTypes = new BYTE_ARRAY_128[16];
    /**
     * 相应物体类型列表
    */
    public int              nPtzPresetId;
    /**
     * 云台预置点编号	0~65535
    */
    public int              nDetectRegionPoint;
    /**
     * 检测区域顶点数,参见结构体定义 {@link NetSDKLib.CFG_POLYGON}
    */
    public NetSDKLib.CFG_POLYGON[] stuDetectRegion = new NetSDKLib.CFG_POLYGON[20];
    /**
     * 检测区域
    */
    public int              bSizeFileter;
    /**
     * 规则特定的尺寸过滤器是否有效,参见结构体定义 {@link NetSDKLib.CFG_SIZEFILTER_INFO}
    */
    public NetSDKLib.CFG_SIZEFILTER_INFO stuSizeFileter = new NetSDKLib.CFG_SIZEFILTER_INFO();
    /**
     * 规则特定的尺寸过滤器
    */
    public int              nTriggerPosition;
    /**
     * 触发报警位置数
    */
    public byte[]           bTriggerPosition = new byte[8];
    /**
     * 触发报警位置,0-目标外接框中心, 1-目标外接框左端中心, 2-目标外接框顶端中心, 3-目标外接框右端中心, 4-目标外接框底端中心
    */
    public int              nDetectType;
    /**
     * 检测类型数
    */
    public byte[]           bDetectType = new byte[4];
    /**
     * 检测类型,0-快速奔跑, 1-突然加速, 2-突然减速
    */
    public int              nTriggerSpeed;
    /**
     * 触发速度，大于该速度报警	单位：km/h
    */
    public int              nMinDuration;
    /**
     * 最短持续时间	单位：秒，0~65535,参见结构体定义 {@link NetSDKLib.CFG_ALARM_MSG_HANDLE}
    */
    public NetSDKLib.CFG_ALARM_MSG_HANDLE stuEventHandler = new NetSDKLib.CFG_ALARM_MSG_HANDLE();
    /**
     * 报警联动,参见结构体定义 {@link com.ruoyi.dahua.lib.structure.CFG_TIME_SECTION}
    */
    public CFG_TIME_SECTION_ARRAY_10[] stuTimeSection = new CFG_TIME_SECTION_ARRAY_10[7];

    public CFG_ABNORMALRUNDETECTION_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
    }
}

