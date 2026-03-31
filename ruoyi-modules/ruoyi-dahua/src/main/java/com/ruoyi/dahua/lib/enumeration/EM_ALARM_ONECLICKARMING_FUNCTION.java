package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 功能
 * @date 2023/03/16 09:04:49
 */
public enum EM_ALARM_ONECLICKARMING_FUNCTION {
	/**
	 * 未知
	 */
    EM_ALARM_ONECLICKARMING_FUNCTION_UNKNOWN(0, "未知"),
	/**
	 * 开关
	 */
    EM_ALARM_ONECLICKARMING_FUNCTION_ONOFF(1, "开关"),
	/**
	 * 只开
	 */
    EM_ALARM_ONECLICKARMING_FUNCTION_ONONLY(2, "只开"),
	/**
	 * 只关
	 */
    EM_ALARM_ONECLICKARMING_FUNCTION_OFFONLY(3, "只关");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ALARM_ONECLICKARMING_FUNCTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ALARM_ONECLICKARMING_FUNCTION enumType : EM_ALARM_ONECLICKARMING_FUNCTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ALARM_ONECLICKARMING_FUNCTION enumType : EM_ALARM_ONECLICKARMING_FUNCTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

