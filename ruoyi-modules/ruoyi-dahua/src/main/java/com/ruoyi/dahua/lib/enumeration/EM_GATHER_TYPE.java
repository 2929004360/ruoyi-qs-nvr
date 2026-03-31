package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 采集类型
 * @date 2022/12/30 10:55:59
 */
public enum EM_GATHER_TYPE {
	/**
	 * 未知类型
	 */
    EM_GATHER_TYPE_UNKNOWN(0, "未知类型"),
	/**
	 * 人脸采集
	 */
    EM_GATHER_TYPE_FACE(1, "人脸采集"),
	/**
	 * 证件采集
	 */
    EM_GATHER_TYPE_IDCARD(2, "证件采集");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_GATHER_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_GATHER_TYPE enumType : EM_GATHER_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_GATHER_TYPE enumType : EM_GATHER_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

