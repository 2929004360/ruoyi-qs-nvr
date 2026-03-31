package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description EAP身份验证方法
 * @date 2023/03/16 11:15:26
 */
public enum EM_CFG_EAP_AUTH_TYPE {
	/**
	 * UnKnown
	 */
    EM_CFG_EAP_AUTH_TYPE_UNKNOWN(0, "UnKnown"),
	/**
	 * NONE
	 */
    EM_CFG_EAP_AUTH_TYPE_NONE(1, "NONE"),
	/**
	 * PAP
	 */
    EM_CFG_EAP_AUTH_TYPE_PAP(2, "PAP"),
	/**
	 * MSCHAP
	 */
    EM_CFG_EAP_AUTH_TYPE_MSCHAP(3, "MSCHAP"),
	/**
	 * MSCHAPV2
	 */
    EM_CFG_EAP_AUTH_TYPE_MSCHAPV2(4, "MSCHAPV2"),
	/**
	 * GTC
	 */
    EM_CFG_EAP_AUTH_TYPE_GTC(5, "GTC");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_EAP_AUTH_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_EAP_AUTH_TYPE enumType : EM_CFG_EAP_AUTH_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_EAP_AUTH_TYPE enumType : EM_CFG_EAP_AUTH_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

