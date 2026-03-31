package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 人和动物检测事件报警类型
 * @date 2022/12/19 11:22:21
 */
public enum EM_HUMAN_ANIMAL_ALARM_TYPE {
	/**
	 * 未知
	 */
    EM_HUMAN_ANIMAL_ALARM_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 从人数正常到人数异常
	 */
    EM_HUMAN_ANIMAL_ALARM_TYPE_NORMAL_TO_ABNORMAL(1, "从人数正常到人数异常"),
	/**
	 * 从人数异常到人数正常
	 */
    EM_HUMAN_ANIMAL_ALARM_TYPE_ABNORMAL_TO_NORMAL(2, "从人数异常到人数正常");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_HUMAN_ANIMAL_ALARM_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_HUMAN_ANIMAL_ALARM_TYPE enumType : EM_HUMAN_ANIMAL_ALARM_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_HUMAN_ANIMAL_ALARM_TYPE enumType : EM_HUMAN_ANIMAL_ALARM_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_HUMAN_ANIMAL_ALARM_TYPE getEnum(int value) {
		for (EM_HUMAN_ANIMAL_ALARM_TYPE e : EM_HUMAN_ANIMAL_ALARM_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_HUMAN_ANIMAL_ALARM_TYPE.EM_HUMAN_ANIMAL_ALARM_TYPE_UNKNOWN;
	}
}

