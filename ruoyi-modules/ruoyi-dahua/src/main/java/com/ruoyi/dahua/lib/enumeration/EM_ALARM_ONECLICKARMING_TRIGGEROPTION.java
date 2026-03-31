package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 触发类型
 * @date 2023/03/16 09:04:49
 */
public enum EM_ALARM_ONECLICKARMING_TRIGGEROPTION {
	/**
	 * 未知
	 */
    EM_ALARM_ONECLICKARMING_TRIGGEROPTION_UNKNOWN(0, "未知"),
	/**
	 * 脉冲
	 */
    EM_ALARM_ONECLICKARMING_TRIGGEROPTION_IMPULSE(1, "脉冲"),
	/**
	 * 双稳定
	 */
    EM_ALARM_ONECLICKARMING_TRIGGEROPTION_BISTABLE(2, "双稳定");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ALARM_ONECLICKARMING_TRIGGEROPTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ALARM_ONECLICKARMING_TRIGGEROPTION enumType : EM_ALARM_ONECLICKARMING_TRIGGEROPTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ALARM_ONECLICKARMING_TRIGGEROPTION enumType : EM_ALARM_ONECLICKARMING_TRIGGEROPTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

