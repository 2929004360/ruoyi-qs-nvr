package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 工装合规组优先方案
 * @date 2022/10/08 17:05:54
 */
public enum EM_WORKSUIT_GROUP_POLICY {
	/**
	 * 未知
	 */
    EM_WORKSUIT_GROUP_POLICY_UNKNWON(-1, "未知"),
	/**
	 * 全身
	 */
    EM_WORKSUIT_GROUP_POLICY_WHOLE_BODY(0, "全身"),
	/**
	 * 上半身
	 */
    EM_WORKSUIT_GROUP_POLICY_UPPER_BODY(1, "上半身");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_WORKSUIT_GROUP_POLICY(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_WORKSUIT_GROUP_POLICY enumType : EM_WORKSUIT_GROUP_POLICY.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_WORKSUIT_GROUP_POLICY enumType : EM_WORKSUIT_GROUP_POLICY.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

