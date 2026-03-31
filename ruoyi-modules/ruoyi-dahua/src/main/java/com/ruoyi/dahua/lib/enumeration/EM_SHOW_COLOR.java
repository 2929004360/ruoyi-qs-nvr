package com.ruoyi.dahua.lib.enumeration;

/**
 * 设备需要展示的颜色
 */
public enum EM_SHOW_COLOR {
	/**
	 * 未知
	 */
    EM_SHOW_COLOR_UNKNOWN(0, "未知"),
	/**
	 * 蓝
	 */
    EM_SHOW_COLOR_BLUE(1, "蓝"),
	/**
	 * 黄
	 */
    EM_SHOW_COLOR_YELLOW(2, "黄"),
	/**
	 * 橙
	 */
    EM_SHOW_COLOR_ORANGE(3, "橙"),
	/**
	 * 红
	 */
    EM_SHOW_COLOR_RED(4, "红");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SHOW_COLOR(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SHOW_COLOR enumType : EM_SHOW_COLOR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SHOW_COLOR enumType : EM_SHOW_COLOR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_SHOW_COLOR getEnum(int value) {
		for (EM_SHOW_COLOR e : EM_SHOW_COLOR.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_SHOW_COLOR.EM_SHOW_COLOR_UNKNOWN;
	}
}

