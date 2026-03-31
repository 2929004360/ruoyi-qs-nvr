package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 屏幕文本颜色
 * @date 2022/11/28 16:20:21
 */
public enum EM_SCREEN_TEXT_COLOR {
	/**
	 * 未知
	 */
    EM_SCREEN_TEXT_COLOR_UNKNOWN(-1, "未知"),
	/**
	 * 绿色
	 */
    EM_SCREEN_TEXT_COLOR_GREEN(0, "绿色"),
	/**
	 * 红色
	 */
    EM_SCREEN_TEXT_COLOR_RED(1, "红色"),
	/**
	 * 黄色
	 */
    EM_SCREEN_TEXT_COLOR_YELLOW(2, "黄色"),
	/**
	 * 白色
	 */
    EM_SCREEN_TEXT_COLOR_WHITE(3, "白色");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SCREEN_TEXT_COLOR(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SCREEN_TEXT_COLOR enumType : EM_SCREEN_TEXT_COLOR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SCREEN_TEXT_COLOR enumType : EM_SCREEN_TEXT_COLOR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

