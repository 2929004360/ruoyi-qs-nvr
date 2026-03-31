package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_OBJECT_APPEAR_DETECTION (目标出现事件)对应的数据块描述信息
 * @date 2023/04/21 16:10:02
 */
public class NET_DEV_EVENT_OBJECT_APPEAR_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * 扩展协议字段
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
     * 相对事件时间戳,单位毫秒
     */
    public double           dbPTS;
    /**
     * 事件发生时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 智能事件规则编号
     */
    public int              nRuleID;
    /**
     * 事件编号
     */
    public int              nEventID;
    /**
     * 帧序号
     */
    public int              nSequence;
    /**
     * 视频分析帧序号
     */
    public int              nFrameSequence;
    /**
     * GroupID事件组ID
     */
    public int              nGroupID;
    /**
     * CountInGroup一个事件组内的抓拍张数
     */
    public int              nCountInGroup;
    /**
     * IndexInGroup一个事件组内的抓拍序号
     */
    public int              nIndexInGroup;
    /**
     * 字节对齐
     */
    public byte[]           szReserved1 = new byte[4];
    /**
     * 检测目标的物体信息个数
     */
    public int              nObjectCount;
    /**
     * 检测目标的物体信息,{@link NetSDKLib.DH_MSG_OBJECT}
     */
    public Pointer          pstuObjects;
    /**
     * 全景图
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_EVENT_OBJECT_APPEAR_DETECTION_INFO() {
    }
}

