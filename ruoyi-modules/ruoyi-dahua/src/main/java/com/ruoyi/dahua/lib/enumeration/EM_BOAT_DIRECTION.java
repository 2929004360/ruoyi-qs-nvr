package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 船只行驶方向
 * @date 2022/06/16 11:14:19
 */
public enum EM_BOAT_DIRECTION {
	/**
	 * 未知
	 */
    EM_BOAT_DIRECTION_UNKNOWN(0, "未知"),
	/**
	 * 逆向
	 */
    EM_BOAT_DIRECTION_OPPOSITE(1, "逆向"),
	/**
	 * 正向
	 */
    EM_BOAT_DIRECTION_POSITIVE(2, "正向");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_BOAT_DIRECTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BOAT_DIRECTION enumType : EM_BOAT_DIRECTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BOAT_DIRECTION enumType : EM_BOAT_DIRECTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

