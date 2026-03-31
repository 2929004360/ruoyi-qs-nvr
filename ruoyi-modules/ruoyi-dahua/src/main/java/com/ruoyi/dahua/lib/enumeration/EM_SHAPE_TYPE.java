package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 形状类型
 * @date 2022/07/21 15:30:00
 */
public enum EM_SHAPE_TYPE {
	/**
	 * 无效的类型
	 */
    EM_SHAPE_TYPE_INVALID(0, "无效的类型"),
	/**
	 * 矩形
	 */
    EM_SHAPE_TYPE_RECT(1, "矩形"),
	/**
	 * 多边形
	 */
    EM_SHAPE_TYPE_POLYGON(2, "多边形");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SHAPE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SHAPE_TYPE enumType : EM_SHAPE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SHAPE_TYPE enumType : EM_SHAPE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_SHAPE_TYPE getEnum(int value) {
		for (EM_SHAPE_TYPE e : EM_SHAPE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_SHAPE_TYPE.EM_SHAPE_TYPE_INVALID;
	}
}

