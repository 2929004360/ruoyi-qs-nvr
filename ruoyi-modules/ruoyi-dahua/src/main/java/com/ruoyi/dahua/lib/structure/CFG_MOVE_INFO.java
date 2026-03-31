package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_MOVEDETECTION(移动事件/运动检测)对应的规则配置
*/
public class CFG_MOVE_INFO extends NetSDKLib.SdkStructure
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
     * 灵敏度，取值1-10，值越小灵敏度越低
    */
    public byte             bSensitivity;
    /**
     * 触发跟踪使能,仅对绊线，入侵规则有效
    */
    public byte             bTrackEnable;
    /**
     * 保留字段
    */
    public byte[]           bReserved = new byte[1];
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
     * 触发速度，km/h
    */
    public int              nTriggerSpeed;
    /**
     * 触发报警位置数
    */
    public int              nTriggerPosition;
    /**
     * 触发报警位置,0-目标外接框中心, 1-目标外接框左端中心, 2-目标外接框顶端中心, 3-目标外接框右端中心, 4-目标外接框底端中心
    */
    public byte[]           bTriggerPosition = new byte[8];
    /**
     * 规则特定的尺寸过滤器是否有效
    */
    public int              bSizeFileter;
    /**
     * 规则特定的尺寸过滤器,参见结构体定义 {@link NetSDKLib.CFG_SIZEFILTER_INFO}
    */
    public NetSDKLib.CFG_SIZEFILTER_INFO stuSizeFileter = new NetSDKLib.CFG_SIZEFILTER_INFO();
    /**
     * 检测类型数
    */
    public int              nTypeNumber;
    /**
     * 检测动作列表,0-快速运动 1-突然加速 2-突然减速
    */
    public byte[]           bDetectType = new byte[4];
    /**
     * 跟踪持续时间,0秒:一直跟踪,1~300秒:跟踪持续时间
    */
    public int              nTrackDuration;

    public CFG_MOVE_INFO() {
        for(int i = 0; i < szObjectTypes.length; i++){
            szObjectTypes[i] = new BYTE_ARRAY_128();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.CFG_POLYGON();
        }
        for(int i = 0; i < stuTimeSection.length; i++){
            stuTimeSection[i] = new CFG_TIME_SECTION_ARRAY_10();
        }
    }
}

