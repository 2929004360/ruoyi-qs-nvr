package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 灭火器检测事件, 对应事件类型 EVENT_IVS_FIRE_EXTINGUISHER_DETECTION
 * @date 2021/12/27
 */
public class DEV_EVENT_FIRE_EXTINGUISHER_DETECTION_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号,从0开始
     */
    public int              nChannelID;
    /**
     *  事件动作, 1表示开始, 2表示结束, -1表示未知
     */
    public int              nEventAction;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     *  时间戳(单位是毫秒)
     */
    public double           dbPTS;
    /**
     *  事件发生的时间
     */
    public NET_TIME_EX      stuUTC;
    /**
     *  事件ID
     */
    public int              nEventID;
    /**
     *  智能事件所属大类,EM_SCENE_CLASS_TYPE
     */
    public int              emClassType;
    /**
     *  检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT stuObject;
    /**
     *  全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage;
    /**
     *  目标区域内物体计数信息
     */
    public NET_FIRE_EXTINGUISHER_OBJECT_INFO stuObjectInfo;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[1024];
}

