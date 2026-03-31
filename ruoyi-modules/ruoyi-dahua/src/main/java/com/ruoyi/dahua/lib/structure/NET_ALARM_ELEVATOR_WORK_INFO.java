package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 DH_ALARM_ELEVATOR_WORK (上报电梯运行数据报警)对应的数据块描述信息
*/
public class NET_ALARM_ELEVATOR_WORK_INFO extends NetSDKLib.SdkStructure
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
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 当前电梯内人数
    */
    public int              nCurrentNumber;
    /**
     * 扩展协议字段,参见结构体定义 {@link NET_EVENT_INFO_EXTEND}
    */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 电梯所在的楼层,最大值255
    */
    public byte[]           szElevatorFloor = new byte[32];
    /**
     * 电梯运行状态,0 未知，1电梯停止,2 电梯上行,3 电梯下行
    */
    public int              nElevatorWorkState;
    /**
     * 电梯运行速度，单位mm/s，最大速度为65535mm/s
    */
    public int              nElevatorWorkSpeed;
    /**
     * 基准楼层是否已校准（梯控传感器重启需要重新校准
    */
    public int              bIsCheckFloor;
    /**
     * 电梯门的状态，0：未知, 1：开、分 ,2：关、合 , 3：半开半合
    */
    public int              nDoorState;
    /**
     * 温度单位支持两种类型：    "Centigrade" : 摄氏度"Fahrenheit":华氏度
    */
    public byte[]           szTemperatureUnit = new byte[16];
    /**
     * 电梯内亮度，分为三档 “Strong”，“Medium”，“Weak”，字符串表示
    */
    public byte[]           szBrightness = new byte[32];
    /**
     * 电梯内的温度值，单位：℃或H，根据 TemperatureUnit判断
    */
    public float            fTemperature;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[972];

    public NET_ALARM_ELEVATOR_WORK_INFO() {
    }
}

