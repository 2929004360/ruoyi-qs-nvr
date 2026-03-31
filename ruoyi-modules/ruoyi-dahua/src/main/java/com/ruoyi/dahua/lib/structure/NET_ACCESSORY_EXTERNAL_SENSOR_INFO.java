package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 外部接入探测器信息
*/
public class NET_ACCESSORY_EXTERNAL_SENSOR_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 外部防拆配置,参见结构体定义 {@link NET_ACCESSORY_TAMPER_INFO}
    */
    public NET_ACCESSORY_TAMPER_INFO stuTamperInfo = new NET_ACCESSORY_TAMPER_INFO();
    /**
     * 探测器状态, -1:未知, 0:正常, 1:告警
    */
    public int              nStatus;
    /**
     * 输入探测器类型, "Tamper": 防拆, "Sensor":报警输入, "Keyswitch":钥匙开关
    */
    public byte[]           szInputType = new byte[32];
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[1020];

    public NET_ACCESSORY_EXTERNAL_SENSOR_INFO() {
    }
}

