package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 支持的去重方向
 * @origin autoTool
 * @date 2023/09/22 13:39:22
 */
public enum EM_DIRECTION_ACCURATE {
	/**
	 * 未知
	 */
    EM_DIRECTION_ACCURATE_UNKNOWN(0, "未知"),
	/**
	 * 进入
	 */
    EM_DIRECTION_ACCURATE_ENTER(1, "进入"),
	/**
	 * 离开
	 */
    EM_DIRECTION_ACCURATE_EXIT(2, "离开");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_DIRECTION_ACCURATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_DIRECTION_ACCURATE enumType : EM_DIRECTION_ACCURATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_DIRECTION_ACCURATE enumType : EM_DIRECTION_ACCURATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_DIRECTION_ACCURATE getEnum(int value) {
		for (EM_DIRECTION_ACCURATE e : EM_DIRECTION_ACCURATE.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_DIRECTION_ACCURATE.EM_DIRECTION_ACCURATE_UNKNOWN;
	}
}

