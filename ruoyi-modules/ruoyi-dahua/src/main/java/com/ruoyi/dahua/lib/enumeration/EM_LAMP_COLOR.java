package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 灯组灯色
 * @date 2021/09/01
 */
public enum EM_LAMP_COLOR {
	// 未知
    EM_LAMP_COLOR_UNKOWN(-1, "未知"),
	// 无灯
    EM_LAMP_COLOR_NO(0, "无灯"),
	// 灭灯
    EM_LAMP_COLOR_LIGHT_OFF(1, "灭灯"),
	// 亮灯
    EM_LAMP_COLOR_LIGHT_ON(2, "亮灯"),
	// 闪烁
    EM_LAMP_COLOR_LIGHT_FLASH(3, "闪烁");

	private int value;
	private String note;

	private EM_LAMP_COLOR(int givenValue, String note) {
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
		for (EM_LAMP_COLOR enumType : EM_LAMP_COLOR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_LAMP_COLOR enumType : EM_LAMP_COLOR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

