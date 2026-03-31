package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_PRAM_DETECTION (婴儿车检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_PRAM_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲 1:开始 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 时间戳(单位是毫秒)
    */
    public double           PTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 智能事件所属大类,参见枚举定义 {@link NetSDKLib.EM_CLASS_TYPE}
    */
    public int              emClassType;
    /**
     * 智能事件规则编号，用于标示哪个规则触发的事件
    */
    public int              nRuleID;
    /**
     * 事件组ID，同一物体抓拍过程内GroupID相同
    */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号
    */
    public int              nIndexInGroup;
    /**
     * 事件触发的预置点号，从1开始, 没有该字段，表示预置点未知
    */
    public int              nPresetID;
    /**
     * 检测到的多个物体,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[128];
    /**
     * 检测到的物体个数
    */
    public int              nObjectNum;
    /**
     * 实际检测到区域个数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区域,参见结构体定义 {@link NetSDKLib.NET_POINT}
    */
    public NetSDKLib.NET_POINT[] stuDetectRegion = new NetSDKLib.NET_POINT[20];
    /**
     * 保留字节
    */
    public byte[]           byReversed = new byte[1024];

    public DEV_EVENT_PRAM_DETECTION_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NetSDKLib.NET_POINT();
        }
    }
}

