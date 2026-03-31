package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 事件类型 EVENT_IVS_VEHICLE_STATE (车辆状态事件)对应的数据块描述信息
*/
public class NET_DEV_EVENT_VEHICLE_STATE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 通道号
    */
    public int              nChannelID;
    /**
     * 事件类型 0:脉冲,1:开始, 2:停止
    */
    public int              nAction;
    /**
     * 事件名称
    */
    public byte[]           szName = new byte[128];
    /**
     * 智能事件所属大类
    */
    public byte[]           szClass = new byte[16];
    /**
     * 相对事件时间戳,(单位是毫秒)
    */
    public double           dbPTS;
    /**
     * 事件发生的时间,参见结构体定义 {@link NetSDKLib.NET_TIME_EX}
    */
    public NetSDKLib.NET_TIME_EX stuUTC = new NetSDKLib.NET_TIME_EX();
    /**
     * 事件ID
    */
    public int              nEventID;
    /**
     * 事件时间毫秒数
    */
    public int              nUTCMS;
    /**
     * 事件的确认ID
    */
    public int              nACK;
    /**
     * 车辆状态
    */
    public byte[]           szStatus = new byte[32];
    /**
     * 任务号
    */
    public byte[]           szTaskID = new byte[64];
    /**
     * 油箱容积，单位0.1L
    */
    public int              nOilTankage;
    /**
     * 当前油箱的油量, 单位0.1L
    */
    public int              nCurrentFuel;
    /**
     * 当前油耗，L/100公里 单位0.1L
    */
    public int              nConsumptionOil;
    /**
     * 总的消耗油量，单位0.1L
    */
    public int              nTotalUsedFuel;
    /**
     * 总里程，单位0.1 Km
    */
    public int              nTotalDistance;
    /**
     * 保养距离，单位0.1Km
    */
    public int              nServiceDistance;
    /**
     * 报警标志(保养里程小于2000km时告警标示),参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_VEHICLE_STATE_ALARM_FLAG}
    */
    public int              emAlarmFlag;
    /**
     * 当前档位 -1:未知 0-P 1-R 2-N 3-D 4-S
    */
    public int              nCurrentGear;
    /**
     * 当前速度，单位：0.1Km/h
    */
    public int              nSpeed;
    /**
     * 箱盖状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_COVER_STATUS}
    */
    public int              emCoverStatus;
    /**
     * 举升状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LIFT_STATUS}
    */
    public int              emLiftStatus;
    /**
     * 载重状态,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_LOAD_STATUS}
    */
    public int              emLoadStatus;
    /**
     * GPS信息,参见结构体定义 {@link NetSDKLib.NET_GPS_STATUS_INFO}
    */
    public NetSDKLib.NET_GPS_STATUS_INFO stuGPSStatus = new NetSDKLib.NET_GPS_STATUS_INFO();
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_DEV_EVENT_VEHICLE_STATE_INFO() {
    }
}

