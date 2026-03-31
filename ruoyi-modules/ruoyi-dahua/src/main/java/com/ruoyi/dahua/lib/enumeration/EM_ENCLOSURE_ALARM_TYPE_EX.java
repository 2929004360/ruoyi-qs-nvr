package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 详细类型
 * @date 2022/12/06 19:32:31
 */
public enum EM_ENCLOSURE_ALARM_TYPE_EX {
	/**
	 * 未知
	 */
    ENCLOSURE_ALARM_EX_UNKNOWN(0, "未知"),
	/**
	 * 驶入
	 */
    ENCLOSURE_ALARM_EX_DRIVEIN(1, "驶入"),
	/**
	 * 驶出
	 */
    ENCLOSURE_ALARM_EX_DRIVEOUT(2, "驶出"),
	/**
	 * 超速
	 */
    ENCLOSURE_ALARM_EX_OVERSPEED(3, "超速"),
	/**
	 * 超速消失
	 */
    ENCLOSURE_ALARM_EX_SPEEDCLEAR(4, "超速消失");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ENCLOSURE_ALARM_TYPE_EX(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ENCLOSURE_ALARM_TYPE_EX enumType : EM_ENCLOSURE_ALARM_TYPE_EX.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ENCLOSURE_ALARM_TYPE_EX enumType : EM_ENCLOSURE_ALARM_TYPE_EX.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

