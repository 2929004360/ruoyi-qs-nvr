package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 行程码状态
 * @date 2021/08/15
 */
public enum EM_TRAVEL_CODE_COLOR {
	// 未知
    EM_TRAVEL_CODE_COLOR_UNKNOWN(0, "未知"),
	// 红
    EM_TRAVEL_CODE_COLOR_RED(1, "红"),
	// 绿
    EM_TRAVEL_CODE_COLOR_GREEN(2, "绿"),
	// 黄
    EM_TRAVEL_CODE_COLOR_YELLOW(3, "黄"),
	// 橙
    EM_TRAVEL_CODE_COLOR_ORANGE(4, "橙");

	private int value;
	private String note;

	private EM_TRAVEL_CODE_COLOR(int givenValue, String note) {
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
		for (EM_TRAVEL_CODE_COLOR enumType : EM_TRAVEL_CODE_COLOR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TRAVEL_CODE_COLOR enumType : EM_TRAVEL_CODE_COLOR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

