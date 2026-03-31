package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 事件类型描述
 * @date 2022/08/18 10:00:46
 */
public enum EM_EVENT_TYPE_DESCEIBEINFO {
	/**
	 * 正常抓图事件
	 */
    EM_EVENT_NORMAL(0, "正常抓图事件"),
	/**
	 * 邮件联动抓图事件
	 */
    EM_EVENT_EMAIL(1, "邮件联动抓图事件");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_EVENT_TYPE_DESCEIBEINFO(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_EVENT_TYPE_DESCEIBEINFO enumType : EM_EVENT_TYPE_DESCEIBEINFO.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_EVENT_TYPE_DESCEIBEINFO enumType : EM_EVENT_TYPE_DESCEIBEINFO.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

