package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 门磁类型
 * @date 2021/01/11
 */
public enum EM_ACCESS_SENSOR_TYPE {
	// 未知
    EM_ACCESS_SENSOR_TYPE_UNKNOWN(-1, "未知"),
	// 常开
    EM_ACCESS_SENSOR_TYPE_NO(0, "常开"),
	// 常闭
    EM_ACCESS_SENSOR_TYPE_NC(1, "常闭");

	private int value;
	private String note;

	private EM_ACCESS_SENSOR_TYPE(int givenValue, String note) {
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
		for (EM_ACCESS_SENSOR_TYPE enumType : EM_ACCESS_SENSOR_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ACCESS_SENSOR_TYPE enumType : EM_ACCESS_SENSOR_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

