package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 门禁控制能力
 * @origin autoTool
 * @date 2023/08/24 14:19:16
 */
public class CFG_CAP_ACCESSCONTROL extends NetSDKLib.SdkStructure {
	/**
	 * 门禁组数、
	 */
    public int              nAccessControlGroups;
	/**
	 * 是否支持门禁报警日志记录在记录集中
	 */
    public int              bSupAccessControlAlarmRecord;
	/**
	 * AccessControlCustomPassword记录集中密码的保存方式
	 * {@link com.ruoyi.dahua.lib.enumeration.EM_CUSTOM_PASSWORD_ENCRYPTION_MODE}
	 */
    public int              emCustomPasswordEncryption;
	/**
	 * 是否支持信息功能 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORTFINGERPRINT}
	 */
    public int              emSupportFingerPrint;
	/**
	 * 是否只支持单门授权(发卡)
	 */
    public int              bOnlySingleDoorAuth;
	/**
	 * 是否支持授权异步返回
	 */
    public int              bAsynAuth;
	/**
	 * 假日计划
	 */
    public NET_SPECIAL_DAYS_SCHEDULE stSpecialDaysSchedule = new NET_SPECIAL_DAYS_SCHEDULE();
	/**
	 * 是否支持多人多类型组合开门
	 */
    public int              bSupportMultiUserMultiTypeAuth;
	/**
	 * 是否支持门禁快速导入功能 {@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_FAST_IMPORT_TYPE}
	 */
    public int              bSupportFastImport;
	/**
	 * 是否支持门禁快速复核功能{@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_FAST_CHECK_TYPE}
	 */
    public int              bSupportFastCheck;
	/**
	 * 是否支持梯控功能
	 */
    public int              bSupportCallLift;
	/**
	 * 支持的梯控制方式类型的集合{@link com.ruoyi.dahua.lib.enumeration.EM_SUPPORT_LIFT_CONTROL_TYPES}
	 */
    public int[]            emSupportLiftControlTypes = new int[32];
	/**
	 * 支持的梯控方式类型的有效数
	 */
    public int              nSupportLiftControlTypesNum;
	/**
	 * 支持静电检测功能
	 */
    public int              bSupportESD;
	/**
	 * 是否支持假日常开常闭功能
	 */
    public int              bSupportSpecialDaysAlwaysOpenOrClose;
	/**
	 * 能力bit位控制：0-不支持人员采集新接口, 1-支持人脸使用新接口, 2-支持身份证采集使用新接口, 4-支持指纹采集使用新接口,
	 * 8-支持卡采集使用新接口 16-支持虹膜采集使用新接口, 32-支持掌纹采集使用新接口
	 */
    public int              nSupportPersonCollection;
	/**
	 * 是否支持平台下发假日计划
	 */
    public int              bSupportSpecialDaysConfig;
	/**
	 * 是否支持按照门通道设置门磁的常开/常闭类型
	 */
    public int              bSupportSetDoorChannelSensorType;
	/**
	 * 保留字段
	 */
    public byte[]           szResvered = new byte[1016];

	public CFG_CAP_ACCESSCONTROL() {
	}
}

