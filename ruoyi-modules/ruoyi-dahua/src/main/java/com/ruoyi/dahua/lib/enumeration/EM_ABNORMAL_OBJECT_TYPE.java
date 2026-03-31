package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 目标类型
 * @date 2022/12/19 11:30:59
 */
public enum EM_ABNORMAL_OBJECT_TYPE {
	/**
	 * 未知
	 */
    ABNORMAL_OBJECT_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 猪
	 */
    ABNORMAL_OBJECT_TYPE_PIG(1, "猪");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ABNORMAL_OBJECT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ABNORMAL_OBJECT_TYPE enumType : EM_ABNORMAL_OBJECT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ABNORMAL_OBJECT_TYPE enumType : EM_ABNORMAL_OBJECT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

