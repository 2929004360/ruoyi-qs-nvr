package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 围栏报警类型
 * @date 2022/12/06 19:32:31
 */
public enum EM_ENCLOSURE_TYPE_EX {
	/**
	 * 未知
	 */
    EM_ENCLOSURE_TYPE_EX_UNKNOWN(0, "未知"),
	/**
	 * 限速区
	 */
    EM_ENCLOSURE_TYPE_EX_LIMITSPEED(1, "限速区"),
	/**
	 * 驾驶区
	 */
    EM_ENCLOSURE_TYPE_EX_DRIVEALLOW(2, "驾驶区"),
	/**
	 * 禁止区
	 */
    EM_ENCLOSURE_TYPE_EX_FORBIDDRIVE(3, "禁止区"),
	/**
	 * 装货区
	 */
    EM_ENCLOSURE_TYPE_EX_LOADGOODS(4, "装货区"),
	/**
	 * 卸货区
	 */
    EM_ENCLOSURE_TYPE_EX_UPLOADGOODS(5, "卸货区");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ENCLOSURE_TYPE_EX(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ENCLOSURE_TYPE_EX enumType : EM_ENCLOSURE_TYPE_EX.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ENCLOSURE_TYPE_EX enumType : EM_ENCLOSURE_TYPE_EX.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

