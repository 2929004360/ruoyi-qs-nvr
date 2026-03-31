package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 工装合规组类型
 * @date 2022/10/08 17:05:54
 */
public enum EM_WORKSUIT_GROUP_TYPE {
	/**
	 * 未知
	 */
    EM_WORKSUIT_GROUP_TYPE_UNKNWON(0, "未知"),
	/**
	 * 注册库(布控名单)
	 */
    EM_WORKSUIT_GROUP_TYPE_BLOCK(1, "注册库(布控名单)");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_WORKSUIT_GROUP_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_WORKSUIT_GROUP_TYPE enumType : EM_WORKSUIT_GROUP_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_WORKSUIT_GROUP_TYPE enumType : EM_WORKSUIT_GROUP_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

