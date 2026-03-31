package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_SMART_MOTION_EQUIPMENT (智能动检事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_SMART_MOTION_EQUIPMENT_INFO extends NetSDKLib.SdkStructure
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
     * 设备运行状况检测信息有效个数
    */
    public int              nObjectNum;
    /**
     * 设备运行状况检测信息,参见结构体定义 {@link NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO}
    */
    public NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO[] stuObject = new NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO[64];
    /**
     * 场景大图,参见结构体定义 {@link NET_SMART_MOTION_EQUIPMENT_SCENE_IMAGE_INFO}
    */
    public NET_SMART_MOTION_EQUIPMENT_SCENE_IMAGE_INFO stuSceneImage = new NET_SMART_MOTION_EQUIPMENT_SCENE_IMAGE_INFO();
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 保留字节
    */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_SMART_MOTION_EQUIPMENT_INFO() {
        for(int i = 0; i < stuObject.length; i++){
            stuObject[i] = new NET_SMART_MOTION_EQUIPMENT_OBJECT_INFO();
        }
    }
}

