package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 遮挡块形状
 * @date 2022/07/21 13:40:56
 */
public enum EM_PRIVACY_MASKING_TYPE {
	/**
	 * 未知类型
	 */
    EM_MASKING_TYPE_UNKNOWN(0, "未知类型"),
	/**
	 * 矩形
	 */
    EM_MASKING_TYPE_RECT(1, "矩形"),
	/**
	 * 多边形
	 */
    EM_MASKING_TYPE_POLYGON(2, "多边形");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_PRIVACY_MASKING_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_PRIVACY_MASKING_TYPE enumType : EM_PRIVACY_MASKING_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_PRIVACY_MASKING_TYPE enumType : EM_PRIVACY_MASKING_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

