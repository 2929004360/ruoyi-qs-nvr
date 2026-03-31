package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 雷达报警类型
 * @date 2021/07/21
 */
public enum EM_RADAR_ALARM_TYPE {
	// 未知
    EM_RADAR_ALARM_TYPE_UNKNOWN(0, "未知"),
	// 报警
    EM_RADAR_ALARM_TYPE_ALARM(1, "报警"),
	// 预警
    EM_RADAR_ALARM_TYPE_WARNING(2, "预警");

	private int value;
	private String note;

	private EM_RADAR_ALARM_TYPE(int givenValue, String note) {
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
		for (EM_RADAR_ALARM_TYPE enumType : EM_RADAR_ALARM_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RADAR_ALARM_TYPE enumType : EM_RADAR_ALARM_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

