package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 电池温度状态
 * @date 2022/03/31
 */
public enum EM_BATTERY_TEMPER_STATE {
	// 未知
    EM_BATTERY_TEMPER_STATE_UNKNOWN(0, "未知"),
	// 低温
    EM_BATTERY_TEMPER_STATE_LOW_TEMPER(1, "低温"),
	// 常温
    EM_BATTERY_TEMPER_STATE_NORMAL_TEMPER(2, "常温"),
	// 高温
    EM_BATTERY_TEMPER_STATE_HIGH_TEMPER(3, "高温"),
	// 超温
    EM_BATTERY_TEMPER_STATE_OVER_TEMPER(4, "超温"),
	// 异常
    EM_BATTERY_TEMPER_STATE_ABNORMAL_TEMPER(5, "异常");

	private int value;
	private String note;

	private EM_BATTERY_TEMPER_STATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BATTERY_TEMPER_STATE enumType : EM_BATTERY_TEMPER_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BATTERY_TEMPER_STATE enumType : EM_BATTERY_TEMPER_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

