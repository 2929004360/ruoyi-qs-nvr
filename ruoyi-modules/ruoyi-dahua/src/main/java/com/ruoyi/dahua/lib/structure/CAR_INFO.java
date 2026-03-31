package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 车辆运行状态信息
 * @date 2021/09/01
 */
public class CAR_INFO extends NetSDKLib.SdkStructure {
	/**
     *  机动车电子标识序列号
     */
    public byte[]           szCarId = new byte[128];
    /**
     *  车牌号码
     */
    public byte[]           szPlateNumber = new byte[128];
    /**
     *  车辆类型,参考NetSDKLib.EM_CAR_TYPE
     */
    public int              emCarType;
    /**
     *  速度单位为米每秒（m/s）
     */
    public int              nSpeed;
    /**
     *  加速度单位为米每平方秒（m/s2）
     */
    public int              nAcceleration;
    /**
     *  车头方向角以地理正北方向为起点的顺时针旋转角度，单位为度（°）
     */
    public int              nHeadDirAngle;
    /**
     *  车辆故障报警自动驾驶汽车测试期间主动上报的故障信息，按位表示：Bit0：故障报警灯开启Bit1~Bit7：保留
     */
    public int              nAlarm;
    /**
     *  预留字节
     */
    public byte[]           byReserved = new byte[256];
}

