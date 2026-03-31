package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 智能模块温度异常事件(对应 DH_ALARM_INTELLI_MODULE_HIGH_TEMP)
 * @origin autoTool
 * @date 2023/11/27 20:25:48
 */
public class NET_ALARM_INTELLI_MODULE_HIGH_TEMP_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 通道号
     */
    public int              nChannelID;
    /**
     * / 事件类型 0:脉冲,1:开始, 2:停止
     */
    public int              nAction;
    /**
     * / 事件发生的时间
     */
    public NET_TIME_EX      stuUTC = new NET_TIME_EX();
    /**
     * / 由模块类型和芯片ID组成，模块芯片ID即槽位号
     */
    public byte[]           szChip = new byte[16];
    /**
     * / CPU当前温度
     */
    public int              nCPUTemperature;
    /**
     * / GPU当前温度
     */
    public int              nGPUTemperature;
    /**
     * / 保留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_INTELLI_MODULE_HIGH_TEMP_INFO() {
    }
}

