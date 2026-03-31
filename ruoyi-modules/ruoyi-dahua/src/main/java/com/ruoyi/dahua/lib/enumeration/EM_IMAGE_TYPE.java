package com.ruoyi.dahua.lib.enumeration;

/**
 * 图片类型
 */
public enum EM_IMAGE_TYPE {
	// 未知
    EM_IMAGE_UNKNOWN(-1, "未知"),
	// 安检员图片
    EM_IMAGE_INSPECTOR(0, "安检员图片"),
	// 包裹图片
    EM_IMAGE_PACKAGE(1, "包裹图片");

	private int value;
	private String note;

	private EM_IMAGE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_IMAGE_TYPE enumType : EM_IMAGE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_IMAGE_TYPE enumType : EM_IMAGE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

