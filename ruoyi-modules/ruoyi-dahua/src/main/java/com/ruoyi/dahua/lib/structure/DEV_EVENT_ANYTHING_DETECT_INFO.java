package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
import com.sun.jna.Pointer;

/**
 * @author 251823
 * @description 事件类型 EVENT_IVS_ANYTHING_DETECT  全物体类型检测事件 对应的数据块描述信息 
 * @date 2021/10/09
 */
public class DEV_EVENT_ANYTHING_DETECT_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  0-脉冲 1-开始 2-结束
     */
    public int              nAction;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     *  时间戳(单位:毫秒)
     */
    public double           PTS;
    /**
     *  事件发生的时间
     */
    public NET_TIME_EX      UTC;
    /**
     *  事件ID
     */
    public int              nEventID;
    /**
     *  智能事件所属大类 {@link NetSDKLib.EM_CLASS_TYPE}
     */
    public int              emClassType;
    /**
     *  智能事件规则编号
     */
    public int              nRuleId;
    /**
     *  事件触发的预置点号，从1开始
     */
    public int              nPresetID;
    /**
     *  事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
    /**
     *  全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage;
    /**
     *  检测到的物体个数
     */
    public int              nObjectNum;
    /**
     *  检测到的物体信息,对应结构体{@link NET_VAGEOBJECT_INFO},指针映射内存数据为NET_VAGEOBJECT_INFO[nObjectNum]数组
     */
    public Pointer          pstuObjects;
    /**
     *  保留字节
     */
    public byte[]           bReserved = new byte[1024];
}

