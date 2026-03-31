package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 无线数据加密方式
 * @date 2023/03/16 11:15:26
 */
public enum EM_CFG_WIRELESS_DATA_ENCRYPT {
	/**
	 * UnKnown
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_UNKNOWN(0, "UnKnown"),
	/**
	 * NONE
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_NONE(1, "NONE"),
	/**
	 * WEP
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_WEP(2, "WEP"),
	/**
	 * TKIP
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_TKIP(3, "TKIP"),
	/**
	 * AES(CCMP)
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_AES(4, "AES(CCMP)"),
	/**
	 * TKIP+AES
	 */
    EM_CFG_WIRELESS_DATA_ENCRYPT_TKIP_AES(5, "TKIP+AES");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_WIRELESS_DATA_ENCRYPT(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_WIRELESS_DATA_ENCRYPT enumType : EM_CFG_WIRELESS_DATA_ENCRYPT.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_WIRELESS_DATA_ENCRYPT enumType : EM_CFG_WIRELESS_DATA_ENCRYPT.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

