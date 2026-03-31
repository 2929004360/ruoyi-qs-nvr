package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_ELEVATOR_ALARM(电梯异常报警)对应的数据块描述信息
*/
public class NET_DEV_EVENT_ELEVATOR_ALARM_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件编号，用来唯一标志一个事件
    */
    public int              nEventID;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 当前事件触发的报警类型
    */
    public byte[]           szAlarmType = new byte[32];
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 超速异常使用，最大加速度 ，m/s^2
    */
    public double           dbMaxAccel;
    /**
     * 超速异常使用，最大速度 m/s
    */
    public double           dbMaxSpeed;
    /**
     * 超速异常使用，当前速度（必须大于最大速度）m/s
    */
    public double           dbSpeed;
    /**
     * 超速异常使用，当前加速度 m/s^2
    */
    public double           dbAccel;
    /**
     * 非平层停梯使用，楼层名称，下方楼层。如果下方没有楼层显示"Bottom"
    */
    public byte[]           szUnderFloor = new byte[32];
    /**
     * 非平层停梯使用，楼层名称，上方楼层。如果上方没有楼层显示"Top"
    */
    public byte[]           szUpFloor = new byte[32];
    /**
     * 温度异常使用，当前温度，单位摄氏度
    */
    public int              nCurrentTemperature;
    /**
     * 事件触发的预置点号，从1开始，0表示预置点未知
    */
    public int              nPresetID;
    /**
     * 外设数据
    */
    public double           dbPeripheralData;
    /**
     * 外设地址
    */
    public int              nPeripheralAddress;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1012];

    public NET_DEV_EVENT_ELEVATOR_ALARM_INFO() {
    }
}

