package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description EAP方法
 * @date 2023/03/16 11:15:26
 */
public enum EM_CFG_EAP_METHOD {
	/**
	 * UnKnown
	 */
    EM_CFG_EAP_METHOD_UNKNOWN(0, "UnKnown"),
	/**
	 * PEAP
	 */
    EM_CFG_EAP_METHOD_PEAP(1, "PEAP"),
	/**
	 * TLS
	 */
    EM_CFG_EAP_METHOD_TLS(2, "TLS"),
	/**
	 * TTLS
	 */
    EM_CFG_EAP_METHOD_TTLS(3, "TTLS");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_EAP_METHOD(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_EAP_METHOD enumType : EM_CFG_EAP_METHOD.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_EAP_METHOD enumType : EM_CFG_EAP_METHOD.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

