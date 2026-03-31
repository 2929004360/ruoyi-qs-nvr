package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 车位类型
 * @date 2022/05/11 13:27:51
 */
public enum EM_SPACE_TYPE {
	/**
	 * 未知
	 */
    EM_SPACE_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 小车位
	 */
    EM_SPACE_TYPE_CAR(0, "小车位"),
	/**
	 * 大车位
	 */
    EM_SPACE_TYPE_BIG_CAR(1, "大车位"),
	/**
	 * 危化品车位
	 */
    EM_SPACE_TYPE_HAZARDOUS_CHEMICALS(2, "危化品车位"),
	/**
	 * 货车车位
	 */
    EM_SPACE_TYPE_TRUCK(3, "货车车位"),
	/**
	 * 小客车车位
	 */
    EM_SPACE_TYPE_SMALL_TRUCK(4, "小客车车位"),
	/**
	 * 大客车车位
	 */
    EM_SPACE_TYPE_BUS(5, "大客车车位");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SPACE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SPACE_TYPE enumType : EM_SPACE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SPACE_TYPE enumType : EM_SPACE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_SPACE_TYPE getEnum(int value) {
		for (EM_SPACE_TYPE e : EM_SPACE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_SPACE_TYPE.EM_SPACE_TYPE_UNKNOWN;
	}
}

