package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 控制智能机箱开关配置, 对应 NET_EM_CFG_CONTROL_CASE
 * @date 2023/05/11 13:59:24
 */
public class NET_CFG_CONTROL_CASE_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 结构体大小
	 */
    public int              dwSize;
	/**
	 * 交互机电源
	 */
    public int              bSwitcherPower;
	/**
	 * 相机电源
	 */
    public int              bIpc;
	/**
	 * 风扇
	 */
    public int              bFan;
	/**
	 * 照明开关
	 */
    public int              bLight;
	/**
	 * 智慧空开
	 */
    public int              bAirSwitch;
	/**
	 * 锁空开
	 */
    public int              bLockStatus;
	/**
	 * 4G_5G 使能
	 */
    public int              b4G_5G;
	/**
	 * 输出电压控制开关
	 */
    public NET_CONTROL_CASE_OUTV_INFO stuOutV = new NET_CONTROL_CASE_OUTV_INFO();
	/**
	 * ZIGBEE开关
	 */
    public NET_CONTROL_CASE_ZIGBEE_INFO stuZigBee = new NET_CONTROL_CASE_ZIGBEE_INFO();

	public NET_CFG_CONTROL_CASE_INFO() {
		this.dwSize = this.size();
	}
}

