package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 无线设备工作模式
 * @date 2023/03/16 14:52:52
 */
public enum EM_WIRELESS_DEVICE_MODE {
	/**
	 * 模式未识别
	 */
    EM_WIRELESS_DEVICE_MODE_UNKNOWN(0, "模式未识别"),
	/**
	 * Normal 普通模式
	 */
    EM_WIRELESS_DEVICE_MODE_NORMAL(1, "Normal 普通模式"),
	/**
	 * Polling 巡检模式 只有Type为RemoteControl时才能处于巡检模式
	 */
    EM_WIRELESS_DEVICE_MODE_POLLING(2, "Polling 巡检模式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_WIRELESS_DEVICE_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_WIRELESS_DEVICE_MODE enumType : EM_WIRELESS_DEVICE_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_WIRELESS_DEVICE_MODE enumType : EM_WIRELESS_DEVICE_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

