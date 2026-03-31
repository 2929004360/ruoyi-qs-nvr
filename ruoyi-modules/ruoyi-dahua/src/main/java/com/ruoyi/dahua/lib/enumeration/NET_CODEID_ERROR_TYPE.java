package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 对码错误类型
 * @date 2023/03/16 16:26:31
 */
public enum NET_CODEID_ERROR_TYPE {
	/**
	 * 对码正确
	 */
    NET_CODEID_ERROR_TYPE_RIGHT(0, "对码正确"),
	/**
	 * 已经存在
	 */
    NET_CODEID_ERROR_TYPE_ALREADYEXIST(1, "已经存在"),
	/**
	 * 其他错误
	 */
    NET_CODEID_ERROR_TYPE_OTHER(2, "其他错误");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_CODEID_ERROR_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_CODEID_ERROR_TYPE enumType : NET_CODEID_ERROR_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_CODEID_ERROR_TYPE enumType : NET_CODEID_ERROR_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

