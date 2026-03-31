package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 设备给客户端传离线图片需要按照的顺序
 * @date 2022/11/24 20:18:58
 */
public enum EM_EVENT_ORDER {
	/**
	 * 未知
	 */
    EM_EVENT_ORDER_UNKNOWN(0, "未知"),
	/**
	 * 正序上传
	 */
    EM_EVENT_ORDER_ASCENT(1, "正序上传"),
	/**
	 * 倒序上传
	 */
    EM_EVENT_ORDER_DESCENT(2, "倒序上传");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_EVENT_ORDER(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_EVENT_ORDER enumType : EM_EVENT_ORDER.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_EVENT_ORDER enumType : EM_EVENT_ORDER.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

