package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 播报文本类型
 * @date 2022/11/28 16:20:42
 */
public enum EM_BROADCAST_TEXT_TYPE {
	/**
	 * 未知
	 */
    EM_BROADCAST_TEXT_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 普通
	 */
    EM_BROADCAST_TEXT_TYPE_ORDINARY(0, "普通"),
	/**
	 * 车牌号
	 */
    EM_BROADCAST_TEXT_TYPE_PLATE_NUMBER(1, "车牌号"),
	/**
	 * 时间
	 */
    EM_BROADCAST_TEXT_TYPE_TIME(2, "时间"),
	/**
	 * 数字字符串
	 */
    EM_BROADCAST_TEXT_TYPE_NUMBER_STRING(3, "数字字符串");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_BROADCAST_TEXT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BROADCAST_TEXT_TYPE enumType : EM_BROADCAST_TEXT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BROADCAST_TEXT_TYPE enumType : EM_BROADCAST_TEXT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

