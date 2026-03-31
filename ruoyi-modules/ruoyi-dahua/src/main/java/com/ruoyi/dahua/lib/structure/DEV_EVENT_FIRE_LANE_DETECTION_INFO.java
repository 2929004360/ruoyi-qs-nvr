package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 事件类型EVENT_IVS_FIRE_LANE_DETECTION (消防占道检测事件)对应的数据块描述信息
 * @date 2021/03/12
 */
public class DEV_EVENT_FIRE_LANE_DETECTION_INFO extends NetSDKLib.SdkStructure {
	 /**
     *  通道号
     */
    public int              nChannelID;
    /**
     *  1:开始，2:停止
     */
    public int              nAction;
    /**
     *  事件名称
     */
    public byte[]           szName = new byte[128];
    /**
     *  时间戳(单位是毫秒)
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
     *  帧序号
     */
    public int              nSequence;
    /**
     *  规则编号
     */
    public int              nRuleID;
    /**
     *  检测到的物体个数
     */
    public int              nObjectNum;
    /**
     *  检测到的物体
     */
    public NetSDKLib.NET_MSG_OBJECT[] stuObjects = (NetSDKLib.NET_MSG_OBJECT[]) new NetSDKLib.NET_MSG_OBJECT().toArray(64);
    /**
     *  事件对应文件信息
     */
    public NetSDKLib.NET_EVENT_FILE_INFO stuFileInfo;
    /**
     *  stuSceneImage 是否有效
     */
    public int              bSceneImage;
    /**
     *  全景广角图
     */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage;
    /**
     *  表示物体进入还是离开
     */
    public int              emMoveState;
    /**
     *  表示加油口盖标示牌状态，参考枚举EM_FIRE_LANE_OILCAP_STATE
     */
    public int              emOilCapState;
    /**
     *  检测到的车辆信息
     */
    public NetSDKLib.DH_MSG_OBJECT stuVehicle = new NetSDKLib.DH_MSG_OBJECT();
    /**
     *  图片信息数组
     */
    public NET_IMAGE_INFO_EX2 stuImageInfo[] = new NET_IMAGE_INFO_EX2[32];
    /**
     *  图片信息个数
     */
    public int              nImageInfoNum;
    /**
     *  是否有非机动车信息
     */
    public int              bNonMotorInfo;
    /**
     *  非机动车信息
     */
    public NetSDKLib.VA_OBJECT_NONMOTOR stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR();
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     *  预留字段
     */
    public byte             byReserved[] = new byte[1024];
}

