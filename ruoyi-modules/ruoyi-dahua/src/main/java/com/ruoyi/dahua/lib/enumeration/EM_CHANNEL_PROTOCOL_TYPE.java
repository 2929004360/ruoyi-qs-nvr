package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 跟设备对接时的拉流协议类型
 * @date 2023/04/20 08:58:40
 */
public enum EM_CHANNEL_PROTOCOL_TYPE {
	/**
	 * 未知
	 */
    EM_CHANNEL_PROTOCOL_TYPE_UNKNOWN(0, "未知"),
	/**
	 * Onvif接入
	 */
    EM_CHANNEL_PROTOCOL_TYPE_ONVIF(1, "Onvif接入"),
	/**
	 * 国标协议接入
	 */
    EM_CHANNEL_PROTOCOL_TYPE_GB28181(2, "国标协议接入"),
	/**
	 * Rtsp协议接入
	 */
    EM_CHANNEL_PROTOCOL_TYPE_RTSP(3, "Rtsp协议接入"),
	/**
	 * GB35114协议接入
	 */
    EM_CHANNEL_PROTOCOL_TYPE_GB35114(4, "GB35114协议接入");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CHANNEL_PROTOCOL_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CHANNEL_PROTOCOL_TYPE enumType : EM_CHANNEL_PROTOCOL_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CHANNEL_PROTOCOL_TYPE enumType : EM_CHANNEL_PROTOCOL_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

