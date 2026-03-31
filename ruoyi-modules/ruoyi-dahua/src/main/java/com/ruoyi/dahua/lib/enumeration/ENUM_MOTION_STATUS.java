package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 运动状态
 * @date 2022/03/31
 */
public enum ENUM_MOTION_STATUS {
	// 未知
    ENUM_MOTION_STATUS_UNKNOWN(0, "未知"),
	// 静止
    ENUM_MOTION_STATUS_STATIC(1, "静止"),
	// 运动
    ENUM_MOTION_STATUS_MOVE(2, "运动");

	private int value;
	private String note;

	private ENUM_MOTION_STATUS(int givenValue, String note) {
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
		for (ENUM_MOTION_STATUS enumType : ENUM_MOTION_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (ENUM_MOTION_STATUS enumType : ENUM_MOTION_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

