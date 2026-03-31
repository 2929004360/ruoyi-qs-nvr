package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 异常行为
 * @date 2022/12/19 11:30:59
 */
public enum EM_OBJECT_ABNORMAL_TYPE {
	/**
	 * 未知
	 */
    EM_OBJECT_ABNORMAL_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 逆向运动
	 */
    EM_OBJECT_ABNORMAL_TYPE_RETROGRADE(1, "逆向运动");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_OBJECT_ABNORMAL_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_OBJECT_ABNORMAL_TYPE enumType : EM_OBJECT_ABNORMAL_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_OBJECT_ABNORMAL_TYPE enumType : EM_OBJECT_ABNORMAL_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

