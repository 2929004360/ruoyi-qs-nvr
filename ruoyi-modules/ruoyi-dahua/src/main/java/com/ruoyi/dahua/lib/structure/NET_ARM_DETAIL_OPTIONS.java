package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 260611
 * @description 布撤防详细信息
 * @origin autoTool
 * @date 2023/08/10 09:52:28
 */
public class NET_ARM_DETAIL_OPTIONS extends NetSDKLib.SdkStructure {
	/**
	 * 情景模式 {@link com.ruoyi.dahua.lib.enumeration.NET_EM_SCENE_MODE}
	 */
    public int              emProfile;
	/**
	 * 触发方式 {@link com.ruoyi.dahua.lib.enumeration.EM_AREAARM_TRIGGERMODE}
	 */
    public int              emTriggerMode;
	/**
	 * 触发者名称，如用户名称，键盘名称等
	 */
    public byte[]           szName = new byte[64];
	/**
	 * 网络用户IP地址或网络地址，对于TriggerMode为Remote时，需要填写此项
	 */
    public byte[]           szClientAddress = new byte[64];
	/**
	 * 触发者编号，如用户编号、键盘地址、遥控器编号、定时器编号或key类型防区号
	 */
    public int              nID;
	/**
	 * 保留字节
	 */
    public byte[]           szResvered = new byte[1020];

	public NET_ARM_DETAIL_OPTIONS() {
	}
}

