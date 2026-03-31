package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @description 布撤防类型
 * @date 2022/07/19 10:54:34
 */
public enum EM_ARM_TYPE {
	/**
	 * 未知
	 */
    EM_ARM_TYPE_UNKNOWN(0, "未知"),
	/**
	 * Total布防
	 */
    EM_ARM_TYPE_TOTAL_ARMING(1, "Total布防"),
	/**
	 * partial1布防
	 */
    EM_ARM_TYPE_PARTIAL1_ARMING(2, "partial1布防"),
	/**
	 * partial2布防
	 */
    EM_ARM_TYPE_PARTIAL2_ARMING(3, "partial2布防"),
	/**
	 * partial1+2布防
	 */
    EM_ARM_TYPE_PARTIAL_ARMING(4, "partial1+2布防"),
	/**
	 * 强制布防
	 */
    EM_ARM_TYPE_DENFENCE_ARMING(5, "强制布防"),
	/**
	 * 撤防
	 */
    EM_ARM_TYPE_DISARMING(6, "撤防");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ARM_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ARM_TYPE enumType : EM_ARM_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ARM_TYPE enumType : EM_ARM_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_ARM_TYPE getEnum(int value) {
		for (EM_ARM_TYPE e : EM_ARM_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_ARM_TYPE.EM_ARM_TYPE_UNKNOWN;
	}
}

