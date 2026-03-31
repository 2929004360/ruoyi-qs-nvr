package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 用户所在组
 * @date 2023/03/16 09:04:49
 */
public enum EM_ALARM_USER_GROUP {
	/**
	 * 未知
	 */
    EM_ALARM_USER_GROUP_UNKNOWN(0, "未知"),
	/**
	 * 管理员
	 */
    EM_ALARM_USER_GROUP_ADMIN(1, "管理员"),
	/**
	 * 安装员
	 */
    EM_ALARM_USER_GROUP_INSTALLER(2, "安装员"),
	/**
	 * 制造商
	 */
    EM_ALARM_USER_GROUP_MANUFACTURER(3, "制造商"),
	/**
	 * 操作员
	 */
    EM_ALARM_USER_GROUP_OPERATOR(4, "操作员");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ALARM_USER_GROUP(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ALARM_USER_GROUP enumType : EM_ALARM_USER_GROUP.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ALARM_USER_GROUP enumType : EM_ALARM_USER_GROUP.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

