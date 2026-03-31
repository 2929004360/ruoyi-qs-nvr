package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 区域内状态
 * @date 2022/03/31
 */
public enum ENUM_IN_REGION_STATUS {
	// 未知
    ENUM_IN_REGION_STATUS_UNKNOWN(0, "未知"),
	// 在区域内
    ENUM_IN_REGION_STATUS_YES(1, "在区域内"),
	// 不在区域内
    ENUM_IN_REGION_STATUS_NO(2, "不在区域内");

	private int value;
	private String note;

	private ENUM_IN_REGION_STATUS(int givenValue, String note) {
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
		for (ENUM_IN_REGION_STATUS enumType : ENUM_IN_REGION_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (ENUM_IN_REGION_STATUS enumType : ENUM_IN_REGION_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

