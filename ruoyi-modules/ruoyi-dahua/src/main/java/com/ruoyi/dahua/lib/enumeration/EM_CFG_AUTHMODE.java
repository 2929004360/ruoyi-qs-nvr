package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 鉴权模式
 * @date 2023/03/16 14:23:46
 */
public enum EM_CFG_AUTHMODE {
	/**
	 * 不需要鉴权
	 */
    EM_AUTHMODE_NO(0, "不需要鉴权"),
	/**
	 * PAP鉴权
	 */
    EM_AUTHMODE_PAP(1, "PAP鉴权"),
	/**
	 * CHAP鉴权
	 */
    EM_AUTHMODE_CHAP(2, "CHAP鉴权");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_AUTHMODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_AUTHMODE enumType : EM_CFG_AUTHMODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_AUTHMODE enumType : EM_CFG_AUTHMODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

