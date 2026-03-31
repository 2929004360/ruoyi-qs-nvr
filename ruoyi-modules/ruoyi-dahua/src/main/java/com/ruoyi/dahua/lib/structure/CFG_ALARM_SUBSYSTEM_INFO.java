package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警子系统配置 ==>CFG_CMD_ALARM_SUBSYSTEM
 * @date 2023/03/15 19:18:13
 */
public class CFG_ALARM_SUBSYSTEM_INFO extends NetSDKLib.SdkStructure {
	/**
	 * 名称
	 */
    public byte[]           szName = new byte[128];
	/**
	 * 本地防区数目
	 */
    public int              nZoneNum;
	/**
	 * 本地防区号
	 */
    public int[]            anZone = new int[256];
	/**
	 * 扩展防区数目
	 */
    public int              nExZoneNum;
	/**
	 * 扩展防区号
	 */
    public int[]            anExZone = new int[256];
	/**
	 * 延时撤防时间（进入延时）, 单位为秒
	 */
    public int              nDisableDelay;
	/**
	 * 延时布防时间（退出延时）, 单位为秒
	 */
    public int              nEnableDelay;
	/**
	 * 是否为公共子系统
	 */
    public int              bIsPublic;
	/**
	 * 公共所属的子系统数目
	 */
    public int              nPublicSubSystem;
	/**
	 * 公共所属的关联子系统
	 */
    public int[]            anPublicSubSystem = new int[256];
	/**
	 * 报警联动
	 */
    public CFG_ALARM_SUBSYSTEM_MSG_HANDLE stuEventHandler = new CFG_ALARM_SUBSYSTEM_MSG_HANDLE();
	/**
	 * 是否启用子系统
	 */
    public int              bEnable;

	public CFG_ALARM_SUBSYSTEM_INFO() {
	}
}

