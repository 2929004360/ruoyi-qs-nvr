package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型 EVENT_IVS_HIGHWAY_DISASTER_DETECTION（公路灾害检测）对应的数据块描述信息
*/
public class NET_DEV_HIGHWAY_DISASTER_DETECTION_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0：脉冲，1：开始，2：停止
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
     * 事件组ID
    */
    public int              nGroupID;
    /**
     * 一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * 一个事件组内的抓拍序号，从1开始
    */
    public int              nIndexInGroup;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 事件码，用于事件确认
    */
    public int              nEventID;
    /**
     * 智能事件规则编号
    */
    public int              nRuleID;
    /**
     * 帧序号
    */
    public int              nSequence;
    /**
     * 检测区个数
    */
    public int              nDetectRegionNum;
    /**
     * 检测区,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 公路灾害区域个数
    */
    public int              nDisasterRegionNum;
    /**
     * 公路灾害区域,参见结构体定义 {@link NET_POINT_EX}
    */
    public NET_POINT_EX[]   stuDisasterRegion = new NET_POINT_EX[30];
    /**
     * 预置点编号，从1开始
    */
    public int              nPresetID;
    /**
     * 目标类型, "Unknown":不确定情况, "RoadPothole":道路塌陷, "Landslides":滑坡, "Floods":山洪, "DebrisFlow":泥石流, "RockFall":落石
    */
    public byte[]           szObjectType = new byte[32];
    /**
     * 字节对齐
    */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 图片信息有效个数
    */
    public int              nImageInfoNum;
    /**
     * 图片信息数组，内存由NetSDK申请释放,参见结构体定义 {@link NET_IMAGE_INFO_EX3}
    */
    public Pointer          pstuImageInfo;
    /**
     * 时间戳
    */
    public double           dbPTS;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_HIGHWAY_DISASTER_DETECTION_INFO() {
        for(int i = 0; i < stuDetectRegion.length; i++){
            stuDetectRegion[i] = new NET_POINT_EX();
        }
        for(int i = 0; i < stuDisasterRegion.length; i++){
            stuDisasterRegion[i] = new NET_POINT_EX();
        }
    }
}

