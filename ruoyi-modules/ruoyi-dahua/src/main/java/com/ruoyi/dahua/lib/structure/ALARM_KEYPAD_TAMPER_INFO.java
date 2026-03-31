package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 事件类型DH_ALARM_KEYPAD_TAMPER (键盘防拆事件)对应的数据块描述信息
 * @origin autoTool
 * @date 2023/08/02 14:32:52
 */
public class ALARM_KEYPAD_TAMPER_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 事件动作,1表示持续性事件开始,2表示持续性事件结束;
	 */
    public int              nAction;
	/**
	 * 通道号
	 */
    public int              nChannelID;
	/**
	 * 事件发生的时间
	 */
    public NET_TIME_EX      stuTime = new NET_TIME_EX();
	/**
	 * 事件公共扩展字段结构体
	 */
    public NET_EVENT_INFO_EXTEND stuEventInfoEx = new NET_EVENT_INFO_EXTEND();
	/**
	 * 保留字节
	 */
    public byte[]           szReserved = new byte[1024];

	public ALARM_KEYPAD_TAMPER_INFO() {
	}
}

