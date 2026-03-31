package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 260611
 * @description 事件类型 EVENT_IVS_OBJECT_STATE_DETECTION (目标状态事件)对应的数据块描述信息
 * @date 2023/04/23 11:01:23
 */
public class NET_DEV_EVENT_OBJECT_STATE_DETECTION_INFO extends NetSDKLib.SdkStructure {
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
     * 事件发生时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 智能事件规则编号
     */
    public int              nRuleID;
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
     */
    public byte[]           szClass = new byte[16];
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
     * 事件编号
     */
    public int              nEventID;
    /**
     * 相对事件时间戳,单位毫秒
     */
    public double           dbPTS;
    /**
     * 帧序号
     */
    public int              nSequence;
    /**
     * 视频分析帧序号
     */
    public int              nFrameSequence;
    /**
     * 规则区域
     */
    public NET_POINT_EX[]   stuDetectRegion = new NET_POINT_EX[20];
    /**
     * 规则区域个数
     */
    public int              nDetectRegionCount;
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
     * 目标状态, 1:正常, 2:异常, 0:未知
     */
    public int              nObjectStatus;
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[1020];

    public NET_DEV_EVENT_OBJECT_STATE_DETECTION_INFO() {
        for (int i = 0; i < stuDetectRegion.length; i++) {
            stuDetectRegion[i] = new NET_POINT_EX();
        }
    }
}

