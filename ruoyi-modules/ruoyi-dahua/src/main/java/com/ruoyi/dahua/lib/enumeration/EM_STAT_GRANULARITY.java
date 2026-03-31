package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 粒度模式
 * @date 2022/12/12 11:41:07
 */
public enum EM_STAT_GRANULARITY {
	/**
	 * 未知
	 */
    EM_STAT_GRANULARITY_UNKNOWN(0, "未知"),
	/**
	 * 分钟粒度
	 */
    EM_STAT_GRANULARITY_MINUTE(1, "分钟粒度");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_STAT_GRANULARITY(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_STAT_GRANULARITY enumType : EM_STAT_GRANULARITY.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_STAT_GRANULARITY enumType : EM_STAT_GRANULARITY.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

