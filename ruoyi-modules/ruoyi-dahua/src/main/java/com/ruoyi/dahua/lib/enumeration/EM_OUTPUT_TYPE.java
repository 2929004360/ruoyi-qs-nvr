package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 输出通道类型
 * @date 2023/03/15 16:46:55
 */
public enum EM_OUTPUT_TYPE {
	/**
	 * 未知
	 */
    EM_OUTPUT_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 警号
	 */
    EM_OUTPUT_TYPE_SIREN(1, "警号"),
	/**
	 * 输出通道
	 */
    EM_OUTPUT_TYPE_ALARMOUT(2, "输出通道");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_OUTPUT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_OUTPUT_TYPE enumType : EM_OUTPUT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_OUTPUT_TYPE enumType : EM_OUTPUT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

