package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 精准人数统计类型
 * @origin autoTool
 * @date 2023/09/22 13:39:21
 */
public enum EM_RULE_TYPE_ACCURATE {
	/**
	 * 未知
	 */
    EM_RULE_TYPE_ACCURATE_UNKNOWN(0, "未知"),
	/**
	 * 客流ReID
	 */
    EM_RULE_TYPE_ACCURATE_NUMBERSTAT(1, "客流ReID"),
	/**
	 * 目标去重
	 */
    EM_RULE_TYPE_ACCURATE_TARGETRECOGNITION(2, "目标去重"),
	/**
	 * 客流聚档
	 */
    EM_RULE_TYPE_ACCURATE_NUMBERSTATCLUSTER(3, "客流聚档");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_RULE_TYPE_ACCURATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_RULE_TYPE_ACCURATE enumType : EM_RULE_TYPE_ACCURATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RULE_TYPE_ACCURATE enumType : EM_RULE_TYPE_ACCURATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_RULE_TYPE_ACCURATE getEnum(int value) {
		for (EM_RULE_TYPE_ACCURATE e : EM_RULE_TYPE_ACCURATE.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_RULE_TYPE_ACCURATE.EM_RULE_TYPE_ACCURATE_UNKNOWN;
	}
}

