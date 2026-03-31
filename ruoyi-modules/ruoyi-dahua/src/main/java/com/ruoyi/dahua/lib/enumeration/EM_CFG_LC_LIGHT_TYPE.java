package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 灯光类型
 * @date 2023/05/23 09:40:10
 */
public enum EM_CFG_LC_LIGHT_TYPE {
	/**
	 * 未知
	 */
    EM_CFG_LC_LIGHT_TYPEUNKNOWN(0, "未知"),
	/**
	 * 红外灯
	 */
    EM_CFG_LC_LIGHT_TYPE_INFRAREDLIGHT(1, "红外灯"),
	/**
	 * 白光灯
	 */
    EM_CFG_LC_LIGHT_TYPE_WIHTELIGHT(2, "白光灯"),
	/**
	 * 激光灯
	 */
    EM_CFG_LC_LIGHT_TYPE_LASERLIGHT(3, "激光灯"),
	/**
	 * 智能混光灯(根据智能ID切换红外和白光灯)
	 */
    EM_CFG_LC_LIGHT_TYPE_AIMIXLIGHT(4, "智能混光灯(根据智能ID切换红外和白光灯)"),
	/**
	 * 指示灯
	 */
    EM_CFG_LC_LIGHT_TYPE_PILOTLIGHT(5, "指示灯");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_LC_LIGHT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_LC_LIGHT_TYPE enumType : EM_CFG_LC_LIGHT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_LC_LIGHT_TYPE enumType : EM_CFG_LC_LIGHT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

