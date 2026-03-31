package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_HUMAN_TRAIT (人员信息事件)对应的数据块描述信息
*/
public class NET_ALARM_HUMAN_TRAIT_INFO extends NetSDKLib.SdkStructure
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
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 智能事件规则编号
    */
    public int              nRuleID;
    /**
     * 事件组ID，一次检测的多个人体特征nGroupID相同
    */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数(人体个数),一次检测的多个人体特征nCountInGroup相同
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号，从1开始
    */
    public int              nIndexInGroup;
    /**
     * 是否为特定人员类,算法上报, -1 非该类, 0 未知, 1 是特定人员类
    */
    public int              nStaffType;
    /**
     * 区域ID，表示该通道和PtzPresetId下的某个区域，区域ID从1开始
    */
    public int              nAreaIDEx;
    /**
     * 智能物体全局唯一物体标识
    */
    public byte[]           szObjectUUID = new byte[48];
    /**
     * 预置点编号
    */
    public int              nPtzPresetId;
    /**
     * 物体ID
    */
    public int              nObjectID;
    /**
     * 目标唯一ID
    */
    public long             nUniqueID;
    /**
     * 目标属性,参见结构体定义 {@link NetSDKLib.NET_FACE_ATTRIBUTE}
    */
    public NetSDKLib.NET_FACE_ATTRIBUTE stuFaceAttributes = new NetSDKLib.NET_FACE_ATTRIBUTE();
    /**
     * 人体属性,参见结构体定义 {@link NetSDKLib.HUMAN_ATTRIBUTES_INFO}
    */
    public NetSDKLib.HUMAN_ATTRIBUTES_INFO stuHumanAttributes = new NetSDKLib.HUMAN_ATTRIBUTES_INFO();
    /**
     * stuHumanAttributes 的扩展,参见结构体定义 {@link HUMAN_ATTRIBUTES_INFO_EX}
    */
    public HUMAN_ATTRIBUTES_INFO_EX stuHumanAttributesEx = new HUMAN_ATTRIBUTES_INFO_EX();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_HUMAN_TRAIT_INFO() {
    }
}

