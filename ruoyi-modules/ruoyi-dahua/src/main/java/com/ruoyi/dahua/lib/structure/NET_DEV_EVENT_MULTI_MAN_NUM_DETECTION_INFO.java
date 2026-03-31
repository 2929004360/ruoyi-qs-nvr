package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型 EVENT_IVS_MULTI_MAN_NUM_DETECTION (讯问会见室人数报警事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_MULTI_MAN_NUM_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 相对事件时间戳,(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 实际有效目标检测信息个数
    */
    public int              nObjectsRealNum;
    /**
     * 检测目标的物体信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = new NetSDKLib.NET_MSG_OBJECT[128];
    /**
     * 检测区域信息,参见结构体定义 {@link NET_DETECT_REGION_INFO_EX}
    */
    public Pointer          pstuDetectRegionInfo;
    /**
     * 检测区域信息个数
    */
    public int              nDetectRegionInfoCount;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020-NetSDKLib.POINTERSIZE];

    public NET_DEV_EVENT_MULTI_MAN_NUM_DETECTION_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT();
        }
    }
}

