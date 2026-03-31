package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 无线设备认证方式
 * @date 2023/03/16 11:15:26
 */
public enum EM_CFG_WIRELESS_AUTHENTICATION {
	/**
	 * UnKnown
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_UNKNOWN(0, "UnKnown"),
	/**
	 * OPEN
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_OPEN(1, "OPEN"),
	/**
	 * SHARED
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_SHARED(2, "SHARED"),
	/**
	 * WPA
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA(3, "WPA"),
	/**
	 * WPA-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPAPSK(4, "WPA-PSK"),
	/**
	 * WPA2
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA2(5, "WPA2"),
	/**
	 * WPA2-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA2PSK(6, "WPA2-PSK"),
	/**
	 * WPA-NONE
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPANONE(7, "WPA-NONE"),
	/**
	 * WPA-PSK|WPA2-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPAPSK_WPA2PSK(8, "WPA-PSK|WPA2-PSK"),
	/**
	 * WPA|WPA2
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA_WPA2(9, "WPA|WPA2"),
	/**
	 * WPA | WPA-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA_WPAPSK(10, "WPA | WPA-PSK"),
	/**
	 * WPA2|WPA2-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA2_WPA2PSK(11, "WPA2|WPA2-PSK"),
	/**
	 * WPA|WPA-PSK|WPA2|WPA2-PSK
	 */
    EM_CFG_WIRELESS_AUTHENTICATION_WPA_WPAPSK_WPA2_WPA2PSK(12, "WPA|WPA-PSK|WPA2|WPA2-PSK");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_WIRELESS_AUTHENTICATION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_WIRELESS_AUTHENTICATION enumType : EM_CFG_WIRELESS_AUTHENTICATION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_WIRELESS_AUTHENTICATION enumType : EM_CFG_WIRELESS_AUTHENTICATION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

