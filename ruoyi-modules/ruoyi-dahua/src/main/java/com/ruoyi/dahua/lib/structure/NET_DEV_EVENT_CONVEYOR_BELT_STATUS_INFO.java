package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_CONVEYOR_BELT_STATUS (传送带运动状态检测报警事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_CONVEYOR_BELT_STATUS_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件编号
    */
    public int              nEventID;
    /**
     * 检测区,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 检测区个数
    */
    public int              nDetectRegionCount;
    /**
     * 当前煤量占比值
    */
    public float            fCoalData;
    /**
     * 本次事件触发对应的传送带运行状态: 0表示运动到静止, 1表示静止到运动, 2表示未知
    */
    public int              nStatusType;
    /**
     * 本次事件触发对应的传送带上的物品状态: 0表示未知, 1表示物品从有到无, 2表示物品从无到有
    */
    public int              nObjectStatusType;
    /**
     * 全景广角图,参见结构体定义 {@link NET_OBJECT_IMAGE_INFO}
    */
    public NET_OBJECT_IMAGE_INFO stuSceneImage = new NET_OBJECT_IMAGE_INFO();
    /**
     * 检测类型个数
    */
    public int              nRuleTypeCount;
    /**
     * 检测类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE}
    */
    public int[]            emRuleType = new int[16];
    /**
     * 皮带运动到静止时或静止到运动时， 皮带上是否有物体。 0- 未知 1- 有物体 2- 无物体
    */
    public int              nStatusTypeObjRst;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_CONVEYOR_BELT_STATUS_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

