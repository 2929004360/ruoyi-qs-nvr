package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_RIDING_MOTOR_CYCLE (摩托车骑跨检测事件)对应的数据块描述信息
 * @date 2021/12/27
 */
public class DEV_EVENT_RIDING_MOTOR_CYCLE_INFO extends NetSDKLib.SdkStructure {
	/**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     *  事件名称 
     */
    public byte[]           szName = new byte[128];
    /**
     *  智能事件所属大类
     */
    public byte[]           szClass = new byte[16];
    /**
     *  GroupID事件组ID，同一物体抓拍过程内GroupID相同
     */
    public int              nGroupID;
    /**
     *  CountInGroup一个事件组内的抓拍张数
     */
    public int              nCountInGroup;
    /**
     *  IndexInGroup一个事件组内的抓拍序号，从1开始
     */
    public int              nIndexInGroup;
    /**
     *  事件时间毫秒数
     */
    public int              nUTCMS;
    /**
     *  相对事件时间戳,(单位是毫秒)
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
     *  检测到的物体信息个数
     */
    public int              nObjectNum;
    /**
     *  表示检测到的物体信息
     */
    public NetSDKLib.NET_MSG_OBJECT_EX2[] stuObjects = (NetSDKLib.NET_MSG_OBJECT_EX2[]) new NetSDKLib.NET_MSG_OBJECT_EX2().toArray(64);
    /**
     *  全景广角图，仅IndexInData字段有效
     */
    public NetSDKLib.SCENE_IMAGE_INFO stuSceneImage;
    /**
     *  预留字节
     */
    public byte[]           szReserved = new byte[1024];
}

