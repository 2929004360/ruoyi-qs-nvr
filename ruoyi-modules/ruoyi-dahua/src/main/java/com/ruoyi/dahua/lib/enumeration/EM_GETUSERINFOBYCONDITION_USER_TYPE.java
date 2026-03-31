package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 码流类型
 * @date 2023/03/16 09:04:49
 */
public enum EM_GETUSERINFOBYCONDITION_USER_TYPE {
	/**
	 * 未知
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 键盘用户
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_KEYPAD(1, "键盘用户"),
	/**
	 * IC卡用户
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_ICCARD(2, "IC卡用户"),
	/**
	 * 遥控器用户
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_REMOTECONTROL(3, "遥控器用户"),
	/**
	 * 手机用户
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_MOBILE(4, "手机用户"),
	/**
	 * key用户
	 */
    EM_GETUSERINFOBYCONDITION_USER_TYPE_KEY(5, "key用户");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_GETUSERINFOBYCONDITION_USER_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_GETUSERINFOBYCONDITION_USER_TYPE enumType : EM_GETUSERINFOBYCONDITION_USER_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_GETUSERINFOBYCONDITION_USER_TYPE enumType : EM_GETUSERINFOBYCONDITION_USER_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

