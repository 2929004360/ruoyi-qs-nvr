package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型EVENT_IVS_FACERECOGNITION(目标识别)对应的数据块描述信息
*/
public class DEV_EVENT_FACERECOGNITION_INFO_V2 extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX UTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 当前人脸匹配到的候选对象数量
    */
    public int              nCandidateNum;
    /**
     * 当前人脸匹配到的候选对象信息,参见结构体定义 {@link NetSDKLib.CANDIDATE_INFOEX}
    */
    public Pointer          pstuCandidates;
    /**
     * 检测到的物体,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT}
    */
    public NetSDKLib.NET_MSG_OBJECT stuObject = new NetSDKLib.NET_MSG_OBJECT();
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[10240];

    public DEV_EVENT_FACERECOGNITION_INFO_V2() {
    }
}

