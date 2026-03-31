package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 事件类型EVENT_IVS_FACEBODY_DETECT(人像检测事件)对应的数据块描述信息
 * @date 2021/8/24 14:09
 */
public class DEV_EVENT_FACEBODY_DETECT_INFO extends NetSDKLib.SdkStructure {
    public int              nChannelID;                           // 通道号
    public int              nAction;                              // 1:开始 2:停止
    public byte[]           szName = new byte[128];               // 事件名称
    public double           PTS;                                  // 时间戳(单位是毫秒)
    public NET_TIME_EX      UTC = new NET_TIME_EX();              // 事件发生的时间
    /** {@link NetSDKLib.EM_CLASS_TYPE} */
    public int              emClassType;                          // 智能事件所属大类
    public int              nEventID;                             // 事件ID
    public  int             nObjectID;                            // 物体ID
    public int              nRuleID;                              // 智能事件规则编号，用于标示哪个规则触发的事件
    public int              nSequence;                            // 帧序号
    public int              nGroupID;                             // 事件组ID，一次检测的多个nGroupID相同
    public int              nCountInGroup;                        // 一个事件组内的抓拍张数,一次检测的多个nCountInGroup相同
    public int              nIndexInGroup;                        // 一个事件组内的抓拍序号，从1开始
    public  byte[]          byReserved1 = new byte[4];            // 字节对齐
    public FACE_TRAIT       stuFaceTrait = new FACE_TRAIT();      // 人脸特征
    public   BODY_TRAIT     stuBodyTrait = new BODY_TRAIT();      // 人体特征
    public NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO stuBodyImage = new NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO(); // 人体图片信息
    public NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO stuFaceImage = new NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO(); // 人脸图片信息
    public NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO stuSceneImage = new NetSDKLib.NET_EVENT_IMAGE_OFFSET_INFO(); // 全景图片信息
    public OBJECT_RELATED_INFO stuFaceObject = new OBJECT_RELATED_INFO(); // 人脸关联信息
    public OBJECT_RELATED_INFO stuBodyObject = new OBJECT_RELATED_INFO(); // 人体关联信息

    @Override
    public String toString() {
        return "DEV_EVENT_FACEBODY_DETECT_INFO{" +
                "nChannelID=" + nChannelID +
                ", nAction=" + nAction +
                ", szName=" + new String(szName) +
                ", PTS=" + PTS +
                ", UTC=" + UTC +
                ", emClassType=" + emClassType +
                ", nEventID=" + nEventID +
                ", nObjectID=" + nObjectID +
                ", nRuleID=" + nRuleID +
                ", nSequence=" + nSequence +
                ", nGroupID=" + nGroupID +
                ", nCountInGroup=" + nCountInGroup +
                ", nIndexInGroup=" + nIndexInGroup +
                '}';
    }
}

