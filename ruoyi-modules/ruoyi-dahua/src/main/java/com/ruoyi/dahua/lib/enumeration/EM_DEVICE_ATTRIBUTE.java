package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 设备属性
 * @date 2023/04/19 18:56:54
 */
public enum EM_DEVICE_ATTRIBUTE {
	/**
	 * 未知
	 */
    EM_DEVICE_ATTRIBUTE_UNKNOWN(0, "未知"),
	/**
	 * 主属性
	 */
    EM_DEVICE_ATTRIBUTE_MASTER(1, "主属性"),
	/**
	 * 辅属性
	 */
    EM_DEVICE_ATTRIBUTE_ASSISTANT(2, "辅属性");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_DEVICE_ATTRIBUTE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_DEVICE_ATTRIBUTE enumType : EM_DEVICE_ATTRIBUTE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_DEVICE_ATTRIBUTE enumType : EM_DEVICE_ATTRIBUTE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

