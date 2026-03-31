package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

import static com.ruoyi.dahua.lib.NetSDKLib.NET_COMMON_STRING_128;

/**
 * @author ： 260611
 * @description ： 车辆位置报警事件{@link NetSDKLib#NET_ALARM_TRAFFIC_VEHICLE_POSITION}
 * @since ： Created in 2022/03/01 14:55
 */

public class ALARM_TRAFFIC_VEHICLE_POSITION extends NetSDKLib.SdkStructure {
    /**
     * 事件动作,0表示脉冲事件,1表示持续性事件开始,2表示持续性事件结束;
     */
    public int              nAction;
    /**
     * 事件名称
     */
    public byte[]           szEventName = new byte[NET_COMMON_STRING_128];
    /**
     * 物体ID
     */
    public int              nObjectID;
    /**
     * 车牌号
     */
    public byte[]           szPlateNumber = new byte[NET_COMMON_STRING_128];
    /**
     * 识别车牌与卡口抓拍线的相对距离(0为基准，表示视频抓拍卡口位置时的车牌距离；正数表示越过抓拍线；负数表示未越过抓拍线)
     */
    public int              nPosition;
    /**
     * 开闸状态,具体请见 EM_OPEN_STROBE_STATE
     */
    public byte             byOpenStrobeState;
    /**
     * 车牌置信度，范围[0, 255]
     */
    public int              nPlateConfidence;
    /**
     * 车牌颜色
     */
    public byte[]           szPlateColor = new byte[32];
    /**
     * 车牌类型
     */
    public byte[]           szPlateType = new byte[32];
    /**
     * 车身置信度，范围[0, 255]
     */
    public int              nVehicleConfidence;
    /**
     * 车头朝向,用于抓拍时，判断当前车头是否正对着相机, EM_VEHICLE_HEAD_DIRECTION
     */
    public int              emVehicleHeadDirection;
    /**
     * 抓拍车的位置, EM_VEHICLE_POSITION
     */
    public int              emVehiclePosition;
    /**
     * 车辆行驶方向(相对车道方向), EM_VEHICLE_DRIVING_DIRECTION
     */
    public int              emDrivingDirection;
    /**
     * 保留字节
     */
    public byte[]           byReserved = new byte[427];
}

