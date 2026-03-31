package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_ELEVATOR_WORK_INFO(上报电梯运行数据事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_ELEVATOR_WORK_INFO_INFO extends NetSDKLib.SdkStructure
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
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[32];
    /**
     * 帧序号
    */
    public int              nSequence;
    /**
     * 规则编号，用于标示哪个规则触发的事件。
    */
    public int              nRuleID;
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
     * 电梯内的温度值，单位：℃或H，根据 TemperatureUnit判断
    */
    public float            fTemperature;
    /**
     * 温度单位支持两种类型：    "Centigrade" : 摄氏度"Fahrenheit":华氏度
    */
    public byte[]           szTemperatureUnit = new byte[16];
    /**
     * 电梯报警按钮状态 [0：未知，1:未启用，2:已启用，3:报警中]
    */
    public int              nAlarmButtonStat;
    /**
     * 电梯门的状态，0：未知, 1：开、分 ,2：关、合 , 3：半开半合
    */
    public int              nDoorState;
    /**
     * 当前电梯内人数
    */
    public int              nCurrentNumber;
    /**
     * 平层状态，0:未知，1:未启用，2:平层，3:非平层，4:冲顶，5:蹲底]
    */
    public int              nLevelingStat;
    /**
     * 非机动车信息个数
    */
    public int              nObjectsCount;
    /**
     * 非机动车信息,参见结构体定义 {@link NetSDKLib.VA_OBJECT_NONMOTOR}
    */
    public NetSDKLib.VA_OBJECT_NONMOTOR[] stuNonMotor = new NetSDKLib.VA_OBJECT_NONMOTOR[8];
    /**
     * 全景广角图,参见结构体定义 {@link NetSDKLib.SCENE_IMAGE_INFO_EX}
    */
    public NetSDKLib.SCENE_IMAGE_INFO_EX stuSceneImage = new NetSDKLib.SCENE_IMAGE_INFO_EX();
    /**
     * 电梯内亮度，分为三档 “Strong”，“Medium”，“Weak”，字符串表示
    */
    public byte[]           szBrightness = new byte[32];
    /**
     * 相对事件时间戳,单位毫秒
    */
    public double           dbPTS;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_EVENT_ELEVATOR_WORK_INFO_INFO() {
        for(int i = 0; i < stuNonMotor.length; i++){
            stuNonMotor[i] = new NetSDKLib.VA_OBJECT_NONMOTOR();
        }
    }
}

