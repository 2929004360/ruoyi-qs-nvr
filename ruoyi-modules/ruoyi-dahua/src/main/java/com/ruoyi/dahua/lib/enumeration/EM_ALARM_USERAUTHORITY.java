package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 用户的权限列表
 * @date 2023/03/16 09:04:48
 */
public enum EM_ALARM_USERAUTHORITY {
	/**
	 * 未知
	 */
    EM_ALARM_USERAUTHORITY_UNKNOWN(0, "未知"),
	/**
	 * 布防
	 */
    EM_ALARM_USERAUTHORITY_ARMING(1, "布防"),
	/**
	 * 强制布防
	 */
    EM_ALARM_USERAUTHORITY_FORCEARMING(2, "强制布防"),
	/**
	 * 撤防
	 */
    EM_ALARM_USERAUTHORITY_DISARM(3, "撤防"),
	/**
	 * 旁路
	 */
    EM_ALARM_USERAUTHORITY_BYPASS(4, "旁路"),
	/**
	 * 永久旁路
	 */
    EM_ALARM_USERAUTHORITY_PERMANENTBYPASS(5, "永久旁路"),
	/**
	 * 消警
	 */
    EM_ALARM_USERAUTHORITY_ALARMCONFIRM(6, "消警"),
	/**
	 * 查询日志
	 */
    EM_ALARM_USERAUTHORITY_QUERYLOG(7, "查询日志"),
	/**
	 * 调试模式
	 */
    EM_ALARM_USERAUTHORITY_DEBUGMODE(8, "调试模式"),
	/**
	 * 更改基本程序，如：升级程序
	 */
    EM_ALARM_USERAUTHORITY_UPGRADE(9, "更改基本程序，如：升级程序"),
	/**
	 * 添加/更改配置参数
	 */
    EM_ALARM_USERAUTHORITY_SYSTEMCONFIG(10, "添加/更改配置参数"),
	/**
	 * 用户管理
	 */
    EM_ALARM_USERAUTHORITY_USERMANAGEMENT(11, "用户管理");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ALARM_USERAUTHORITY(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ALARM_USERAUTHORITY enumType : EM_ALARM_USERAUTHORITY.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ALARM_USERAUTHORITY enumType : EM_ALARM_USERAUTHORITY.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

