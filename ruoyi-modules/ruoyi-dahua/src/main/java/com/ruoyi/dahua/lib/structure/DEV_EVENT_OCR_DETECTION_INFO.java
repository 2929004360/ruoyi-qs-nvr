package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;
/**
 * 事件类型 EVENT_IVS_OCR_DETECTION (OCR检测事件)对应的数据块描述信息
*/
public class DEV_EVENT_OCR_DETECTION_INFO extends NetSDKLib.SdkStructure
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
     * 智能事件规则编号，用于标示哪个规则触发的事件
    */
    public int              nRuleId;
    /**
     * GroupID事件组ID，同一物体抓拍过程内GroupID相同
    */
    public int              nGroupID;
    /**
     * CountInGroup一个事件组内的抓拍张数
    */
    public int              nCountInGroup;
    /**
     * IndexInGroup一个事件组内的抓拍序号，从1开始
    */
    public int              nIndexInGroup;
    /**
     * 事件发生时间，带时区偏差的UTC时间，单位：秒,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 相对事件时间戳，单位毫秒
    */
    public double           dbPTS;
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 事件触发的预置点号，从1开始 没有该字段，表示预置点未知
    */
    public int              nPresetID;
    /**
     * 识别内容
    */
    public byte[]           szContent = new byte[256];
    /**
     * 检测类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_OCR_DETECTION_DETECT_TYPE}
    */
    public int              emDetectType;
    /**
     * 检测事件类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_OCR_DETECTION_TRIGGER_TYPE}
    */
    public int              emTriggerType;
    /**
     * 全景广角图，仅IndexInData字段有效,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO}
    */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 图片信息数组,参见结构体定义 {@link NET_IMAGE_INFO_EX2}
    */
    public NET_IMAGE_INFO_EX2[] stuImageInfo = new NET_IMAGE_INFO_EX2[32];
    /**
     * 图片信息个数
    */
    public int              nImageInfoNum;
    /**
     * 物体信息数
    */
    public int              nObjectNum;
    /**
     * 物体信息数据,参见结构体定义 {@link NetSDKLib.NET_MSG_OBJECT_EX2}
    */
    public Pointer          pstuObjectInfo;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1016-NetSDKLib.POINTERSIZE];

    public DEV_EVENT_OCR_DETECTION_INFO() {
        for(int i = 0; i < stuImageInfo.length; i++){
            stuImageInfo[i] = new NET_IMAGE_INFO_EX2();
        }
    }
}

