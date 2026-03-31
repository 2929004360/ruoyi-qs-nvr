package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 外部防拆配置
*/
public class NET_ACCESSORY_TAMPER_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 外部防拆配置使能是否存在
    */
    public int              abEnable;
    /**
     * 外部防拆配置使能
    */
    public int              bEnable;
    /**
     * 传感器类型,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SENSOR_TYPE}
    */
    public int              emType;
    /**
     * 外部防拆状态, 0:未知, 1:正常, 2:告警
    */
    public int              nStatus;
    /**
     * 预留字段
    */
    public byte[]           szReserved = new byte[64];

    public NET_ACCESSORY_TAMPER_INFO() {
    }
}

