package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型EVENT_IVS_WATER_SPEED_DETECTION (水流速检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_WATER_SPEED_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件组ID
    */
    public int              nGroupID;
    /**
     * 一个事件组内应有的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号
    */
    public int              nIndexInGroup;
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 时间戳(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件的数据类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.NETAEM_EVENT_TYPE}
    */
    public int              emEventType;
    /**
     * 事件触发的预置点号，从1开始没有该字段，表示预置点未知
    */
    public int              nPresetID;
    /**
     * 事件触发的预置点名称
    */
    public byte[]           szPresetName = new byte[64];
    /**
     * 水流速度信息,参见结构体定义 {@link NET_WATER_SPEED_INFO}
    */
    public NET_WATER_SPEED_INFO[] stuWaterSpeed = new NET_WATER_SPEED_INFO[50];
    /**
     * 水流速度信息个数
    */
    public int              nWaterSpeedNum;
    /**
     * 水流速状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_WATER_SPEED_STATUS}
    */
    public int              emStatus;
    /**
     * 原始图，近景,参见结构体定义 {@link NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO}
    */
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuOriginalImage = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO();
    /**
     * 球机变到最小倍下的抓图,参见结构体定义 {@link NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO}
    */
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuSceneImage = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO();
    /**
     * 保留字节,留待扩展
    */
    public byte[]           szReserved = new byte[1024];

    public DEV_EVENT_WATER_SPEED_DETECTION_INFO() {
        for(int i = 0; i < stuWaterSpeed.length; i++){
            stuWaterSpeed[i] = new NET_WATER_SPEED_INFO();
        }
    }
}

