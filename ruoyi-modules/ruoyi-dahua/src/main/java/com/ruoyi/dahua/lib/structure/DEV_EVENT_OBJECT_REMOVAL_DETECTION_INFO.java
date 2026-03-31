package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.NetSDKLib.NET_MSG_OBJECT_EX2;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_OBJECT_REMOVAL_DETECTION(物品拿取检测事件) 对应数据
 * @date 2023/04/21 16:10:01
 */
public class DEV_EVENT_OBJECT_REMOVAL_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲
     */
    public int              nAction;
    /**
     * 时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      UTC = new NET_TIME_EX();
    /**
     * 事件时间毫秒数
     */
    public int              nUTCMS;
    /**
     * 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * 智能事件所属大类 {@link com.ruoyi.dahua.lib.enumeration.EM_CLASS_TYPE}
     */
    public int              emClassType;
    /**
     * 抓图标志(按位),具体见NET_RESERVED_COMMON
     */
    public int              dwSnapFlagMask;
    /**
     * 规则编号,用于标示哪个规则触发的事件，缺省时默认为0
     */
    public int              nRuleID;
    /**
     * 预录时长, 单位秒
     */
    public int              nPreRecord;
    /**
     * 物体信息数据
     */
    public NetSDKLib.DH_MSG_OBJECT stuObject = new NetSDKLib.DH_MSG_OBJECT();
    /**
     * 广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 检测目标物体的个数
     */
    public int              nObjectCount;
    /**
     * 表示检测到的物体信息 有Objects, 以Objects为准，无Objects, 以Object为准，为兼容老的客户端，有Objects的，Object也要填数据
     */
    public NET_MSG_OBJECT_EX2[] stuObjects = new NET_MSG_OBJECT_EX2[128];
    /**
     * 图片信息数组,{@link NET_IMAGE_INFO_EX2}
     */
    public Pointer          pstuImageInfo;
    /**
     * 图片信息个数
     */
    public int              nImageInfoNum;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 保留字节
     */
    public byte[]           Reserved = new byte[1012];

    public DEV_EVENT_OBJECT_REMOVAL_DETECTION_INFO() {
        for (int i = 0; i < stuObjects.length; i++) {
            stuObjects[i] = new NET_MSG_OBJECT_EX2();
        }
    }
}

