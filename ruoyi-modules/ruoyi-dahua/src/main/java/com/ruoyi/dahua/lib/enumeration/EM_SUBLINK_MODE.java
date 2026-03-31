package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 子连接连接业务模式
 * @date 2022/06/16 15:17:48
 */
public enum EM_SUBLINK_MODE {
	/**
	 * 通用模式，默认值
	 */
    EM_SUBLINK_MODE_NORMAL(0, "通用模式，默认值"),
	/**
	 * 低功耗模式
	 */
    EM_SUBLINK_MODE_LOWPOWER(1, "低功耗模式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SUBLINK_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SUBLINK_MODE enumType : EM_SUBLINK_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SUBLINK_MODE enumType : EM_SUBLINK_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

