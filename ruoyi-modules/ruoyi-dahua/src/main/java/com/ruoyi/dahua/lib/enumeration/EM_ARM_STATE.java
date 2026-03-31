package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 布撤防状态
 * @date 2021/11/08
 */
public enum EM_ARM_STATE {
	// 未知
    EM_ARM_STATE_UNKNOWN(0, "未知"),
	// Total布防
    EM_ARM_STATE_TOTAL_ARMING(1, "Total布防"),
	// partial1布防
    EM_ARM_STATE_PARTIAL1_ARMING(2, "partial1布防"),
	// partial2布防
    EM_ARM_STATE_PARTIAL2_ARMING(3, "partial2布防"),
	// partial1+2布防
    EM_ARM_STATE_PARTIAL1_PARTIAL2_ARMING(4, "partial1+2布防"),
	// 强制布防
    EM_ARM_STATE_FORCEARMING(5, "强制布防"),
	// 撤防
    EM_ARM_STATE_DISARMING(6, "撤防");

	private int value;
	private String note;

	private EM_ARM_STATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ARM_STATE enumType : EM_ARM_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ARM_STATE enumType : EM_ARM_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

