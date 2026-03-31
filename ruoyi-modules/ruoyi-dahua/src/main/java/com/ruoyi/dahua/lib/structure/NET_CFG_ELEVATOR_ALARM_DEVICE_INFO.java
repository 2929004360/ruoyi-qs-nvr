package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 电梯楼层报警相关配置, 对应枚举 NET_EM_CFG_ELEVATOR_ALARM_CONFIG
*/
public class NET_CFG_ELEVATOR_ALARM_DEVICE_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 温度异常报警相关配置,参见结构体定义 {@link NET_CFG_TEMPERATURE_ABNORMAL_INFO}
    */
    public NET_CFG_TEMPERATURE_ABNORMAL_INFO stuTemperatureAbnormal = new NET_CFG_TEMPERATURE_ABNORMAL_INFO();
    /**
     * 超速报警相关配置,参见结构体定义 {@link NET_CFG_OVER_SPEED_INFO}
    */
    public NET_CFG_OVER_SPEED_INFO stuOverSpeed = new NET_CFG_OVER_SPEED_INFO();
    /**
     * 晃动检测相关配置,参见结构体定义 {@link NET_CFG_SWAY_ABNORMAL_INFO}
    */
    public NET_CFG_SWAY_ABNORMAL_INFO stuSwayAbnormal = new NET_CFG_SWAY_ABNORMAL_INFO();
    /**
     * 冲顶报警使能
    */
    public int              bMoveOverTopEnabled;
    /**
     * 蹲底报警使能
    */
    public int              bMoveUnderBottomEnabled;
    /**
     * 非平层停梯报警使能
    */
    public int              bStopBetweenFloorsEnabled;
    /**
     * 开门走梯报警使能
    */
    public int              bOpenDoorMoveEnabled;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[256];

    public NET_CFG_ELEVATOR_ALARM_DEVICE_INFO() {
    }
}

