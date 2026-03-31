package com.ruoyi.dahua.lib.structure;
import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * 测温点报警设置
*/
public class NET_ALARM_SETTING_INFO extends NetSDKLib.SdkStructure
{
    /**
     * 报警条件,"Below", 低于;"Match", 匹配;"Above" 高于
    */
    public byte[]           szAlarmCondition = new byte[16];
    /**
     * 阈值温度持续时间,单位：秒
    */
    public int              nDuration;
    /**
     * 是否开启该点报警
    */
    public int              bEnable;
    /**
     * 温度误差,应该表示正负误差在0.1范围内
    */
    public float            fHysteresis;
    /**
     * 报警唯一编号
    */
    public int              nId;
    /**
     * 预报警温度持续时间;单位：秒
    */
    public int              nPreDuration;
    /**
     * 预报警阈值
    */
    public float            fPreThreshold;
    /**
     * 测温报警结果
    */
    public byte[]           szResult = new byte[16];
    /**
     * 斜率报警采样类型;平均值、最小值，最大值;,参见枚举定义 {@link com.ruoyi.dahua.lib.enumeration.EM_SLOPE_RESULT}
    */
    public int              emSlopeResult;
    /**
     * 报警阈值温度/温度斜率（每分钟）
    */
    public float            fThreshold;
    /**
     * 预留字节
    */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_SETTING_INFO() {
    }
}

