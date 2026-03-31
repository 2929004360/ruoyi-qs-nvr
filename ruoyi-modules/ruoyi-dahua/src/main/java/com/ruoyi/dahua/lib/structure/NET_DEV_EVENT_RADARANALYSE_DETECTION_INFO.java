package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_RADARANALYSE_DETECTION (雷达联动事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_RADARANALYSE_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 事件发生时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 事件发生的本地时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuLocaleTime = new NetSDKLib.NET_TIME();
    /**
     * 雷达联动流程的开始时间,参见结构体定义 {@link NetSDKLib.NET_TIME}
    */
    public NetSDKLib.NET_TIME stuRadarStartTime = new NetSDKLib.NET_TIME();
    /**
     * 联动信息，保存其他设备传输的信息，比如雷达,参见结构体定义 {@link NET_LINK_INFO}
    */
    public NET_LINK_INFO    stuLinkInfo = new NET_LINK_INFO();
    /**
     * Objects表示多个物体同时触发滞留事件列表，填写检测到的所有物体上限32个数组元素,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = new NetSDKLib.NET_MSG_OBJECT_EX2[32];
    /**
     * 上报对象数量
    */
    public int              nObjectsNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_RADARANALYSE_DETECTION_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT_EX2();
        }
    }
}

