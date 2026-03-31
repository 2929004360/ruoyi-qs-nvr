package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 屏幕文本类型
 * @date 2022/11/28 16:20:10
 */
public enum EM_SCREEN_TEXT_TYPE {
	/**
	 * 未知
	 */
    EM_SCREEN_TEXT_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 普通
	 */
    EM_SCREEN_TEXT_TYPE_ORDINARY(0, "普通"),
	/**
	 * 本地时间
	 */
    EM_SCREEN_TEXT_TYPE_LOCAL_TIME(1, "本地时间"),
	/**
	 * 二维码
	 */
    EM_SCREEN_TEXT_TYPE_QR_CODE(2, "二维码"),
	/**
	 * 资源文件
	 */
    EM_SCREEN_TEXT_TYPE_RESOURCE(3, "资源文件");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SCREEN_TEXT_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SCREEN_TEXT_TYPE enumType : EM_SCREEN_TEXT_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SCREEN_TEXT_TYPE enumType : EM_SCREEN_TEXT_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

