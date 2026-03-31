package com.ruoyi.dahua.lib.enumeration;

/**
 * 子连接连接方式
 */
public enum EM_SUBLINK_TYPE {
	/**
	 *  直连方式
	 */
    EM_SUBLINK_TYPE_PASSIVE(0, "直连方式"),
	/**
	 * 主动注册方式
	 */
    EM_SUBLINK_TYPE_PORT(1, "主动注册方式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SUBLINK_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SUBLINK_TYPE enumType : EM_SUBLINK_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SUBLINK_TYPE enumType : EM_SUBLINK_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_SUBLINK_TYPE getEnum(int value) {
		for (EM_SUBLINK_TYPE e : EM_SUBLINK_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_SUBLINK_TYPE.EM_SUBLINK_TYPE_PASSIVE;
	}
}

