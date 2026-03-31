package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 目标识别事件，对应DH_UPLOAD_FS_RECOGNITION
*/
public class NET_ALARM_UPLOAD_FS_RECOGNITION_INFO extends NetSDKLib.SdkStructure
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
     * 事件编号
    */
    public int              nEventID;
    /**
     * 目标数据,参见结构体定义 {@link NET_ALARM_UPLOAD_FS_DATA}
    */
    public NET_ALARM_UPLOAD_FS_DATA stuFaceData = new NET_ALARM_UPLOAD_FS_DATA();
    /**
     * 检测到的物体,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public NetSDKLib.NET_MSG_OBJECT_EX2 stuObject = new NetSDKLib.NET_MSG_OBJECT_EX2();
    /**
     * 当前目标匹配到的候选对象信息,参见结构体定义 {@link NetSDKLib.CANDIDATE_INFO}
    */
    public NetSDKLib.CANDIDATE_INFO[] stuCandidates = new NetSDKLib.CANDIDATE_INFO[50];
    /**
     * 候选对象信息有效个数
    */
    public int              nCandidatesNum;
    /**
     * 全景图是否存在
    */
    public int              bGlobalScenePic;
    /**
     * 全景图片信息,参见结构体定义 {@link NET_RECOGNITION_PIC_INFO}
    */
    public NET_RECOGNITION_PIC_INFO stuGlobalScenePicInfo = new NET_RECOGNITION_PIC_INFO();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[508];

    public NET_ALARM_UPLOAD_FS_RECOGNITION_INFO() {
        for(int i = 0; i < stuCandidates.length; i++){
            stuCandidates[i] = new NetSDKLib.CANDIDATE_INFO();
        }
    }
}

