package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_BACK_TO_DETECTION(背对检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_BACK_TO_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 1:开始 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 场景预置点号
    */
    public int              nPresetID;
    /**
     * 事件公共扩展字段结构体,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 预留字段
    */
    public byte[]           byReserved = new byte[1024];

    public DEV_EVENT_BACK_TO_DETECTION_INFO() {
    }
}

