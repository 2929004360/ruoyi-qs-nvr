package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 物体过滤类型
 * @date 2021/07/06
 */
public enum EM_CFG_OBJECT_FILTER_TYPE {
	// 未知
    EM_CFG_OBJECT_FILTER_TYPE_UNKNOWN(0, "未知"),
	// 人
    EM_CFG_OBJECT_FILTER_TYPE_HUMAN(1, "人"),
	// 车
    EM_CFG_OBJECT_FILTER_TYPE_VEHICLE(2, "车");

	private int value;
	private String note;

	private EM_CFG_OBJECT_FILTER_TYPE(int givenValue, String note) {
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
		for (EM_CFG_OBJECT_FILTER_TYPE enumType : EM_CFG_OBJECT_FILTER_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_OBJECT_FILTER_TYPE enumType : EM_CFG_OBJECT_FILTER_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

