package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 事件类型 EVENT_IVS_BARELAND_DETECTION (裸土检测事件)对应的数据块描述信息
 * @date 2021/10/13 10:50
 */
public class DEV_EVENT_BARELAND_DETECTION_INFO extends NetSDKLib.SdkStructure {
    /**
     * 事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
     */
    public byte[]           szClass = new byte[16];
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
     * 事件发生时间，带时区偏差的UTC时间，单位：秒
     */
    public int              nUTC;
    /**
     * 相对事件时间戳，单位毫秒
     */
    public double           dbPTS;
    /**
     * 事件时间毫秒数
     */
    public int              nUTCMS;
    /**
     * 事件编号，用来唯一标志一个事件
     */
    public int              nEventID;
    /**
     * 裸土实际占比，取值0~100
     */
    public int              nRatio;
    /**
     * 全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO();
    /**
     * 通道号
     */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * 原始图,参见结构体定义 {@link NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO}
    */
    public NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO stuOriginalImage = new NetSDKLib.NET_INTELLIGENCE_IMAGE_INFO();
    /**
     * 预留字节
     */
    public byte[]           szReserved = new byte[916];

    public DEV_EVENT_BARELAND_DETECTION_INFO() {
    }
}

