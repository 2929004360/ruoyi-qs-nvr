package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 是否当前vk
 * @date 2022/11/03 09:43:17
 */
public enum EM_IS_CURRENT_VK {
	/**
	 * 未知
	 */
    EM_IS_CURRENT_VK_UNKNOWN(0, "未知"),
	/**
	 * 当前
	 */
    EM_IS_CURRENT_VK_CURRENT(1, "当前"),
	/**
	 * 之前
	 */
    EM_IS_CURRENT_VK_PRE(2, "之前");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_IS_CURRENT_VK(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_IS_CURRENT_VK enumType : EM_IS_CURRENT_VK.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_IS_CURRENT_VK enumType : EM_IS_CURRENT_VK.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

