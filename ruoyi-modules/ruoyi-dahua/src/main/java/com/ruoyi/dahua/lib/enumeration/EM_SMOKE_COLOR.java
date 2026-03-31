package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 烟雾颜色类型
 * @date 2022/03/31
 */
public enum EM_SMOKE_COLOR {
	// 未知
    EM_SMOKE_COLOR_UNKNOWN(0, "未知"),
	// White
    EM_SMOKE_COLOR_WHITE(1, "White"),
	// black
    EM_SMOKE_COLOR_BLACK(2, "black"),
	// red
    EM_SMOKE_COLOR_RED(3, "red"),
	// yellow
    EM_SMOKE_COLOR_YELLOW(4, "yellow");

	private int value;
	private String note;

	private EM_SMOKE_COLOR(int givenValue, String note) {
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
		for (EM_SMOKE_COLOR enumType : EM_SMOKE_COLOR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SMOKE_COLOR enumType : EM_SMOKE_COLOR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

