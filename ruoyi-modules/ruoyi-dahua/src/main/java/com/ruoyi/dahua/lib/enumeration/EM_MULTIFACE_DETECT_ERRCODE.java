package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 大图检测小图结果错误码
 * @date 2023/04/20 15:11:18
 */
public enum EM_MULTIFACE_DETECT_ERRCODE {
	/**
	 * 未知
	 */
    EM_MULTIFACE_DETECT_UNKNOWN(-1, "未知"),
	/**
	 * 成功
	 */
    EM_MULTIFACE_DETECT_SUCCESS(0, "成功"),
	/**
	 * 解码失败
	 */
    EM_MULTIFACE_DETECT_DECODE_FAIL(1, "解码失败"),
	/**
	 * 未检测到有效目标
	 */
    EM_MULTIFACE_DETECT_NO_OBJECT(2, "未检测到有效目标");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_MULTIFACE_DETECT_ERRCODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_MULTIFACE_DETECT_ERRCODE enumType : EM_MULTIFACE_DETECT_ERRCODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_MULTIFACE_DETECT_ERRCODE enumType : EM_MULTIFACE_DETECT_ERRCODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

