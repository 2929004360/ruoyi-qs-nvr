package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 用户状态
 * @date 2023/03/16 09:04:48
 */
public enum EM_GETUSERINFOBYCONDITION_USER_STATUS {
	/**
	 * 未知
	 */
    EM_GETUSERINFOBYCONDITION_USER_STATUS_UNKNOWN(0, "未知"),
	/**
	 * 不存在，未配置过用户信息
	 */
    EM_GETUSERINFOBYCONDITION_USER_STATUS_NOPRESENT(1, "不存在，未配置过用户信息"),
	/**
	 * 未激活，已经配置过用户信息，但是未使能
	 */
    EM_GETUSERINFOBYCONDITION_USER_STATUS_INACTIVATED(2, "未激活，已经配置过用户信息，但是未使能"),
	/**
	 * 已激活
	 */
    EM_GETUSERINFOBYCONDITION_USER_STATUS_ACTIVATED(3, "已激活");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_GETUSERINFOBYCONDITION_USER_STATUS(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_GETUSERINFOBYCONDITION_USER_STATUS enumType : EM_GETUSERINFOBYCONDITION_USER_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_GETUSERINFOBYCONDITION_USER_STATUS enumType : EM_GETUSERINFOBYCONDITION_USER_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

