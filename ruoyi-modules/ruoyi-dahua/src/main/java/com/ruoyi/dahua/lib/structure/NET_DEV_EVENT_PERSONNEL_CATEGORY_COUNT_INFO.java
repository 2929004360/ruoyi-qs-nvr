package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_PERSONNEL_CATEGORY_COUNT (人员类型统计事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_PERSONNEL_CATEGORY_COUNT_INFO extends NetSDKLib.SdkStructure
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
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 智能事件规则编号, 用于标示哪个规则触发的事件
    */
    public int              nRuleID;
    /**
     * 事件编号
    */
    public int              nEventID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[64];
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 全景广角图,参见结构体定义 {@link NET_PERSONNEL_CATEGORY_SCENE_IMAGE}
    */
    public NET_PERSONNEL_CATEGORY_SCENE_IMAGE stuSceneImage = new NET_PERSONNEL_CATEGORY_SCENE_IMAGE();
    /**
     * 人员信息,参见结构体定义 {@link NET_PERSONNEL_CATEGORY_PERSON_INFO}
    */
    public NET_PERSONNEL_CATEGORY_PERSON_INFO[] stuPersonInfo = new NET_PERSONNEL_CATEGORY_PERSON_INFO[64];
    /**
     * 人员信息数量
    */
    public int              nPersonInfoNum;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_PERSONNEL_CATEGORY_COUNT_INFO() {
        for(int i = 0; i < stuPersonInfo.length; i++){
            stuPersonInfo[i] = new NET_PERSONNEL_CATEGORY_PERSON_INFO();
        }
    }
}

