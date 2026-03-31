package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @description 报警事件类型DH_ALARM_AUDIO_ANOMALY(音频异常事件)对应的数据描述信息
 * @date 2022/06/20 17:39:31
 */
public class ALARM_AUDIO_ANOMALY extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwStructSize;
	/**
	 * 事件动作, 0:Start, 1:Stop
	 */
    public int              dwAction;
	/**
	 * 音频通道号
	 */
    public int              dwChannelID;
	/**
	 * 声音强度
	 */
    public int              nDecibel;
	/**
	 * 声音频率
	 */
    public int              nFrequency;
    /**
     * 事件公共扩展字段结构体
     */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
    /**
     * 报警类型：0-未知 1- 音频丢失侦测，2- 声强陡升侦测，3- 声强陡降侦测
    */
    public int              nAlarmType;
    /**
     * 保留字段
    */
    public byte[]           szReserved = new byte[512];

	public ALARM_AUDIO_ANOMALY() {
		this.dwStructSize = this.size();
	}
}

