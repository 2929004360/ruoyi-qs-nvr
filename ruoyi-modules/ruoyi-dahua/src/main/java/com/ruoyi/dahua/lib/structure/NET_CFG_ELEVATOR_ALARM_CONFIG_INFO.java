package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 电梯楼层报警相关配置, 对应枚举 NET_EM_CFG_ELEVATOR_ALARM_CONFIG
*/
public class NET_CFG_ELEVATOR_ALARM_CONFIG_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 结构体大小, 必须赋值
    */
    public int              dwSize;
    /**
     * 每个通道的配置,参见结构体定义 {@link NET_CFG_ELEVATOR_ALARM_DEVICE_INFO}
    */
    public NET_CFG_ELEVATOR_ALARM_DEVICE_INFO[] stuElevatorAlarmDevice = new NET_CFG_ELEVATOR_ALARM_DEVICE_INFO[2];
    /**
     * 通道数量
    */
    public int              nDeviceNum;

    public NET_CFG_ELEVATOR_ALARM_CONFIG_INFO() {
        this.dwSize = this.size();
        for(int i = 0; i < stuElevatorAlarmDevice.length; i++){
            stuElevatorAlarmDevice[i] = new NET_CFG_ELEVATOR_ALARM_DEVICE_INFO();
        }
    }
}

