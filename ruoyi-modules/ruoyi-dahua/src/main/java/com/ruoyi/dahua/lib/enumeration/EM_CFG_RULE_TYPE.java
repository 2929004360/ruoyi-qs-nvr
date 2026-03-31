package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 规则类型
 * @date 2022/12/19 09:10:21
 */
public enum EM_CFG_RULE_TYPE {
	/**
	 * 未知
	 */
    EM_CFG_RULE_UNKNOWN(0, "未知"),
	/**
	 * 人数统计
	 */
    EM_CFG_RULE_NUMBER_STAT(1, "人数统计"),
	/**
	 * 区域内人数统计
	 */
    EM_CFG_RULE_MAN_NUM_DETECTION(2, "区域内人数统计");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_RULE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_RULE_TYPE enumType : EM_CFG_RULE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_RULE_TYPE enumType : EM_CFG_RULE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

