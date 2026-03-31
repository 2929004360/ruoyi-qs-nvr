package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 自动维护星期
 * @date 2021/08/24
 */
public enum EM_AUTO_PROTECT_WEEK {
	// 未知
    EM_AUTO_PROTECT_UNKNOWN(0, "未知"),
	// 星期一
    EM_AUTO_PROTECT_MONDAY(1, "星期一"),
	// 星期二
    EM_AUTO_PROTECT_TUESDAY(2, "星期二"),
	// 星期三
    EM_AUTO_PROTECT_WEDNESDAY(3, "星期三"),
	// 星期四
    EM_AUTO_PROTECT_THURSDAY(4, "星期四"),
	// 星期五
    EM_AUTO_PROTECT_FIRDAY(5, "星期五"),
	// 星期六
    EM_AUTO_PROTECT_SATURDAY(6, "星期六"),
	// 星期天
    EM_AUTO_PROTECT_SUNDAY(7, "星期天"),
	// 每天
    EM_AUTO_PROTECT_EVERYDAY(8, "每天");

	private int value;
	private String note;

	private EM_AUTO_PROTECT_WEEK(int givenValue, String note) {
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
		for (EM_AUTO_PROTECT_WEEK enumType : EM_AUTO_PROTECT_WEEK.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_AUTO_PROTECT_WEEK enumType : EM_AUTO_PROTECT_WEEK.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

