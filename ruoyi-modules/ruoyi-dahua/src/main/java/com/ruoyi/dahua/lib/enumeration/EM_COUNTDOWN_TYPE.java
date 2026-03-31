package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 倒计时方式
 * @date 2021/08/24
 */
public enum EM_COUNTDOWN_TYPE {
	// 未知
    EM_COUNTDOWN_TYPE_UNKNOWN(0, "未知"),
	// 学习式
    EM_COUNTDOWN_TYPE_LEARNING(1, "学习式"),
	// 脉冲式
    EM_COUNTDOWN_TYPE_PLUSE(2, "脉冲式"),
	// 全程485式
    EM_COUNTDOWN_TYPE_FULL485(3, "全程485式"),
	// 半程485式
    EM_COUNTDOWN_TYPE_HALFWAY485(4, "半程485式");

	private int value;
	private String note;

	private EM_COUNTDOWN_TYPE(int givenValue, String note) {
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
		for (EM_COUNTDOWN_TYPE enumType : EM_COUNTDOWN_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_COUNTDOWN_TYPE enumType : EM_COUNTDOWN_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

