package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 雷达检测对象类型
 * @date 2021/07/21
 */
public enum EM_RADAR_DETECT_OBJECT_TYPE {
	// 未知
    EM_RADAR_DETECT_OBJECT_UNKNOWN(0, "未知"),
	// 人
    EM_RADAR_DETECT_OBJECT_HUMAN(1, "人"),
	// 车
    EM_RADAR_DETECT_OBJECT_VEHICLE(2, "车");

	private int value;
	private String note;

	private EM_RADAR_DETECT_OBJECT_TYPE(int givenValue, String note) {
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
		for (EM_RADAR_DETECT_OBJECT_TYPE enumType : EM_RADAR_DETECT_OBJECT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RADAR_DETECT_OBJECT_TYPE enumType : EM_RADAR_DETECT_OBJECT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

