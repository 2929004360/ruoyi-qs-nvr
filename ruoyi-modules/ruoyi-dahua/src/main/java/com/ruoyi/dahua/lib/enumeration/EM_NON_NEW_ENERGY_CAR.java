package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 是否为非新能源车辆
 * @origin autoTool
 * @date 2023/06/20 10:13:16
 */
public enum EM_NON_NEW_ENERGY_CAR {
	/**
     * 未知
     */
    EM_NON_NEW_ENERGY_CAR_UNKNOWN(0, "未知"),
	/**
     * 新能源车辆
     */
    EM_NON_NEW_ENERGY_CAR_NO(1, "新能源车辆"),
	/**
     * 非新能源车辆
     */
    EM_NON_NEW_ENERGY_CAR_YES(2, "非新能源车辆");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_NON_NEW_ENERGY_CAR(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_NON_NEW_ENERGY_CAR enumType : EM_NON_NEW_ENERGY_CAR.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_NON_NEW_ENERGY_CAR enumType : EM_NON_NEW_ENERGY_CAR.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_NON_NEW_ENERGY_CAR getEnum(int value) {
		for (EM_NON_NEW_ENERGY_CAR e : EM_NON_NEW_ENERGY_CAR.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_NON_NEW_ENERGY_CAR.EM_NON_NEW_ENERGY_CAR_UNKNOWN;
	}
}

