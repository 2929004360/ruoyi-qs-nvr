package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 智能模块断线事件(对应 DH_ALARM_INTELLI_MODULE_OFFLINE)
 * @origin autoTool
 * @date 2023/11/27 20:26:57
 */
public class NET_ALARM_INTELLI_MODULE_OFFLINE_INFO extends NetSDKLib.SdkStructure {
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
     * / 保留字节
     */
    public byte[]           szReserved = new byte[1024];

    public NET_ALARM_INTELLI_MODULE_OFFLINE_INFO() {
    }
}

