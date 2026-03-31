package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_LEAKAGE_DETECTION (渗漏检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_LEAKAGE_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 相对事件时间戳,(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 检测目标物体的个数
    */
    public int              nObjectCount;
    /**
     * 检测目标的物体信息,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = new NetSDKLib.NET_MSG_OBJECT_EX2[128];
    /**
     * 渗漏实际占比，单位:%，取值范围[0, 100]
    */
    public float            fRatio;
    /**
     * 全景图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public DEV_EVENT_LEAKAGE_DETECTION_INFO() {
        for(int i = 0; i < stuObjects.length; i++){
            stuObjects[i] = new NetSDKLib.NET_MSG_OBJECT_EX2();
        }
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

