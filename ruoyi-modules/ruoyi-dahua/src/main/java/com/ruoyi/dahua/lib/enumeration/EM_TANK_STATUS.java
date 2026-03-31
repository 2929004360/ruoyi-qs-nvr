package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 贮柜当前状态
 * @date 2022/10/28 11:34:32
 */
public enum EM_TANK_STATUS {
	/**
	 * 未知
	 */
    EM_TANK_STATUS_UNKNOWN(0, "未知"),
	/**
	 * 出料
	 */
    EM_TANK_STATUS_DISCHARGING(1, "出料"),
	/**
	 * 进料
	 */
    EM_TANK_STATUS_CHARGING(2, "进料"),
	/**
	 * 空闲
	 */
    EM_TANK_STATUS_IDLE(3, "空闲");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_TANK_STATUS(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_TANK_STATUS enumType : EM_TANK_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TANK_STATUS enumType : EM_TANK_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

