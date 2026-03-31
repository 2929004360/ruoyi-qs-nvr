package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 设置输出状态
 *              输入参数。此时CLIENT_SetAlarmRegionInfo的emType参数为NET_EM_SET_ALARMREGION_INFO_OUTPUTSTATE
 * @date 2023/03/15 16:46:55
 */
public class NET_IN_SET_OUTPUT_STATE extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 输出类型 {@link com.ruoyi.dahua.lib.enumeration.EM_OUTPUT_TYPE}
	 */
    public int              emType;
	/**
	 * emType= EM_OUTPUT_TYPE_SIREN时表示警号号 emType= EM_OUTPUT_TYPE_ALARMOUT时表示通道号
	 */
    public int              nChannel;
	/**
	 * 输出动作 false:关闭 true:打开
	 */
    public byte             action;
	/**
	 * 字节对齐
	 */
    public byte[]           byReserved = new byte[3];

	public NET_IN_SET_OUTPUT_STATE() {
		this.dwSize = this.size();
	}
}

