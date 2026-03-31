package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 是否小车占大车位
 * @origin autoTool
 * @date 2023/06/20 10:13:15
 */
public enum EM_SMALL_OCCUPY_LARGE {
	/**
     * 未知
     */
    EM_SMALL_OCCUPY_LARGE_UNKNOWN(0, "未知"),
	/**
     * 未小车占大车位
     */
    EM_SMALL_OCCUPY_LARGE_NO(1, "未小车占大车位"),
	/**
     * 小车占大车位
     */
    EM_SMALL_OCCUPY_LARGE_YES(2, "小车占大车位");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SMALL_OCCUPY_LARGE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SMALL_OCCUPY_LARGE enumType : EM_SMALL_OCCUPY_LARGE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SMALL_OCCUPY_LARGE enumType : EM_SMALL_OCCUPY_LARGE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_SMALL_OCCUPY_LARGE getEnum(int value) {
		for (EM_SMALL_OCCUPY_LARGE e : EM_SMALL_OCCUPY_LARGE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_SMALL_OCCUPY_LARGE.EM_SMALL_OCCUPY_LARGE_UNKNOWN;
	}
}

