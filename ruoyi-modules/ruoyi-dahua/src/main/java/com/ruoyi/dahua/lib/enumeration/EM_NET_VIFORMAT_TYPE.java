package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 采集源分辨率
 * @date 2022/07/22 13:41:06
 */
public enum EM_NET_VIFORMAT_TYPE {
	/**
	 * 未知
	 */
    EM_NET_VIFORMAT_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 表示正常分辨率
	 */
    EM_NET_VIFORMAT_TYPE_NORMAL(0, "表示正常分辨率"),
	/**
	 * 表示不支持的分辨率
	 */
    EM_NET_VIFORMAT_TYPE_UNSUPPORT(1, "表示不支持的分辨率"),
	/**
	 * 表示未接采集源
	 */
    EM_NET_VIFORMAT_TYPE_NOT_CONNECTED(2, "表示未接采集源");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_NET_VIFORMAT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_NET_VIFORMAT_TYPE enumType : EM_NET_VIFORMAT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_NET_VIFORMAT_TYPE enumType : EM_NET_VIFORMAT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_NET_VIFORMAT_TYPE getEnum(int value) {
		for (EM_NET_VIFORMAT_TYPE e : EM_NET_VIFORMAT_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_NET_VIFORMAT_TYPE.EM_NET_VIFORMAT_TYPE_UNKNOWN;
	}
}

