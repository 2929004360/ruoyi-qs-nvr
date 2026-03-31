package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 设备接入网络服务类型
 * @date 2023/04/20 08:58:40
 */
public enum EM_NET_SERVER_TYPE {
	/**
	 * 未知
	 */
    EM_NET_SERVER_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 自动
	 */
    EM_NET_SERVER_TYPE_AUTO(0, "自动"),
	/**
	 * TCP
	 */
    EM_NET_SERVER_TYPE_TCP(1, "TCP"),
	/**
	 * UDP
	 */
    EM_NET_SERVER_TYPE_UDP(2, "UDP"),
	/**
	 * 组播
	 */
    EM_NET_SERVER_TYPE_MULTICAST(3, "组播");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_NET_SERVER_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_NET_SERVER_TYPE enumType : EM_NET_SERVER_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_NET_SERVER_TYPE enumType : EM_NET_SERVER_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

