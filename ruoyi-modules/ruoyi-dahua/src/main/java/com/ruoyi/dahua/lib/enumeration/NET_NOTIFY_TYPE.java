package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 通知类型
 * @date 2023/03/15 21:15:00
 */
public enum NET_NOTIFY_TYPE {
	/**
	 * 未知
	 */
    NET_NOTIFY_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 短信
	 */
    NET_NOTIFY_TYPE_SMS(1, "短信"),
	/**
	 * 电话
	 */
    NET_NOTIFY_TYPE_CALL(2, "电话"),
	/**
	 * 短信&电话
	 */
    NET_NOTIFY_TYPE_ALL(3, "短信&电话");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_NOTIFY_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_NOTIFY_TYPE enumType : NET_NOTIFY_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_NOTIFY_TYPE enumType : NET_NOTIFY_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

