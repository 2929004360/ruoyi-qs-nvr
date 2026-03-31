package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 屏幕文本滚动模式
 * @date 2022/11/28 16:20:32
 */
public enum EM_SCREEN_TEXT_ROLL_MODE {
	/**
	 * 未知
	 */
    EM_SCREEN_TEXT_ROLL_MODE_UNKNOWN(-1, "未知"),
	/**
	 * 不滚动
	 */
    EM_SCREEN_TEXT_ROLL_MODE_NO(0, "不滚动"),
	/**
	 * 左右滚动
	 */
    EM_SCREEN_TEXT_ROLL_MODE_LEFT_RIGHT(1, "左右滚动"),
	/**
	 * 上下翻页滚动
	 */
    EM_SCREEN_TEXT_ROLL_MODE_UP_DOWN(2, "上下翻页滚动"),
	/**
	 * 截取(超出部分直接截断显示)
	 */
    EM_SCREEN_TEXT_ROLL_MODE_CUT_OUT(3, "截取(超出部分直接截断显示)");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SCREEN_TEXT_ROLL_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SCREEN_TEXT_ROLL_MODE enumType : EM_SCREEN_TEXT_ROLL_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SCREEN_TEXT_ROLL_MODE enumType : EM_SCREEN_TEXT_ROLL_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

