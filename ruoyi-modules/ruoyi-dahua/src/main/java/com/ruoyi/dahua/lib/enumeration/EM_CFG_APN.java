package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 接入的网络名称
 * @date 2023/03/16 14:23:46
 */
public enum EM_CFG_APN {
	/**
	 * 中国电信
	 */
    EM_CFG_APN_CTNET(0, "中国电信"),
	/**
	 * 中国移动
	 */
    EM_CFG_APN_CMNET(1, "中国移动"),
	/**
	 * 中国联通
	 */
    EM_CFG_APN_UNINET(2, "中国联通");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_APN(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_APN enumType : EM_CFG_APN.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_APN enumType : EM_CFG_APN.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

