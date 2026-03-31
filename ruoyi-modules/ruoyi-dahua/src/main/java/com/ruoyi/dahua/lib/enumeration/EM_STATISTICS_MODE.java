package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @version 1.0
 * @description 统计模式
 * @date 2021/12/18
 */
public enum EM_STATISTICS_MODE {
	// 未知
    EM_STATISTICS_MODE_UNKNOWN(0, "未知"),
	// 区域
    EM_STATISTICS_MODE_AREA(1, "区域"),
	// 车位
    EM_STATISTICS_MODE_SPACE(2, "车位");

	private int value;
	private String note;

	private EM_STATISTICS_MODE(int givenValue, String note) {
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
		for (EM_STATISTICS_MODE enumType : EM_STATISTICS_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_STATISTICS_MODE enumType : EM_STATISTICS_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

