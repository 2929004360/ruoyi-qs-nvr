package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description Low Rate Wireless Personal Area Network 低速无线私域网 begin 无线设备类型
 * @date 2023/03/16 14:52:52
 */
public enum NET_WIRELESS_DEVICE_TYPE {
	/**
	
	*/
    NET_WIRELESS_DEVICE_TYPE_UNKNOWN(0, ""),
	/**
	 * 无线键盘
	 */
    NET_WIRELESS_DEVICE_TYPE_KEYBOARD(1, "无线键盘"),
	/**
	 * 无线防区
	 */
    NET_WIRELESS_DEVICE_TYPE_DEFENCE(2, "无线防区"),
	/**
	 * 无线遥控
	 */
    NET_WIRELESS_DEVICE_TYPE_REMOTECONTROL(3, "无线遥控"),
	/**
	 * 无线门磁
	 */
    NET_WIRELESS_DEVICE_TYPE_MAGNETOMER(4, "无线门磁"),
	/**
	 * 无线警号
	 */
    NET_WIRELESS_DEVICE_TYPE_ALARMBELL(5, "无线警号"),
	/**
	 * 无线插座
	 */
    NET_WIRELESS_DEVICE_TYPE_SWITCHER(6, "无线插座"),
	/**
	 * 无线智能锁
	 */
    NET_WIRELESS_DEVICE_TYPE_SMARTLOCK(7, "无线智能锁"),
	/**
	 * 无线中继器
	 */
    NET_WIRELESS_DEVICE_TYPE_REPEATER(8, "无线中继器");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_WIRELESS_DEVICE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_WIRELESS_DEVICE_TYPE enumType : NET_WIRELESS_DEVICE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_WIRELESS_DEVICE_TYPE enumType : NET_WIRELESS_DEVICE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

