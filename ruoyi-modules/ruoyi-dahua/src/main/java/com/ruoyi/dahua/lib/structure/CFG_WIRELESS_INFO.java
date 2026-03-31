package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 无线网络连接设置
 * @date 2023/03/16 14:23:46
 */
public class CFG_WIRELESS_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 2G网络使能
	 */
    public int              bEnable;
	/**
	 * 保活时间, 单位为秒，0表示一直连接，不自动断开
	 */
    public int              nKeepAlive;
	/**
	 * 接入的2G网络名称 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_APN}
	 */
    public int              emAPN;
	/**
	 * 用户名
	 */
    public byte[]           szUseName = new byte[64];
	/**
	 * 密码
	 */
    public byte[]           szPassword = new byte[64];
	/**
	 * 每日流量控制策略 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_DAY3GFLUXTACTIC}
	 */
    public int              emDay3GFluxTactic;
	/**
	 * 每日流量使用上限, MB或者分钟
	 */
    public int              dwDay3GFluxUp;
	/**
	 * 当日的已用流量, MB或者分钟
	 */
    public int              dwDay3GFluxUse;
	/**
	 * 流量报警策略 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_DAY3GFLUXACTION}
	 */
    public int              emDay3GFluxAction;
	/**
	 * 拨号时间段
	 */
    public NetSDKLib.TIME_SECTION_WEEK_DAY_6[] stuTimeSection = new NetSDKLib.TIME_SECTION_WEEK_DAY_6[7];
	/**
	 * 鉴权模式 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_AUTHMODE}
	 */
    public int              emAuthMode;
	/**
	 * 接入网络名
	 */
    public byte[]           szAPNName = new byte[32];
	/**
	 * 实际使用流量, [0,65535]MB或者分钟
	 */
    public int              n3GFlux;
	/**
	 * 流量使用策略{@link com.ruoyi.dahua.lib.enumeration.EM_CFG_3GFLUXTACTIC}
	 */
    public int              em3GFluxTactic;
	/**
	 * 流量使用上限
	 */
    public int              n3GFluxUp;
	/**
	 * 工作模式选择 {@link com.ruoyi.dahua.lib.enumeration.EM_CFG_WORKMODE}
	 */
    public int              emWorkMode;
	/**
	 * 拨号号码
	 */
    public byte[]           szDailNumber = new byte[32];
	/**
	 * 是否已经被语音或短信激活
	 */
    public int              bActivate;

	public CFG_WIRELESS_INFO() {
		for (int i = 0; i < stuTimeSection.length; i++) {
			stuTimeSection[i] = new NetSDKLib.TIME_SECTION_WEEK_DAY_6();
		}
	}
}

