package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 温度类型
 * @date 2022/05/12 16:32:08
 */
public enum EM_TEMPERATUREEX_TYPE {
	/**
	 * 未知
	 */
    EM_TEMPERATUREEX_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 全部
	 */
    EM_TEMPERATUREEX_TYPE_ALL(1, "全部"),
	/**
	 * 电源
	 */
    EM_TEMPERATUREEX_TYPE_POWER(2, "电源"),
	/**
	 * 机柜
	 */
    EM_TEMPERATUREEX_TYPE_CABINET(3, "机柜"),
	/**
	 * 环境
	 */
    EM_TEMPERATUREEX_TYPE_GLOBAL(4, "环境"),
	/**
	 * 主板
	 */
    EM_TEMPERATUREEX_TYPE_MAINBOARD(5, "主板"),
	/**
	 * 子卡
	 */
    EM_TEMPERATUREEX_TYPE_CARD(6, "子卡"),
	/**
	 * 背板
	 */
    EM_TEMPERATUREEX_TYPE_BACKBOARD(7, "背板"),
	/**
	 * 处理器
	 */
    EM_TEMPERATUREEX_TYPE_CPU(8, "处理器");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_TEMPERATUREEX_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_TEMPERATUREEX_TYPE enumType : EM_TEMPERATUREEX_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TEMPERATUREEX_TYPE enumType : EM_TEMPERATUREEX_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

