package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 旁路模式类型
 * @date 2023/03/15 16:33:42
 */
public enum EM_BYPASSMODE_TYPE {
	/**
	 * 未知
	 */
    EM_BYPASSMODE_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 不使用
	 */
    EM_BYPASSMODE_TYPE_OFF(1, "不使用"),
	/**
	 * 正常
	 */
    EM_BYPASSMODE_TYPE_ACTIVE(2, "正常"),
	/**
	 * 旁路
	 */
    EM_BYPASSMODE_TYPE_BYPASSED(3, "旁路"),
	/**
	 * 隔离
	 */
    EM_BYPASSMODE_TYPE_ISOLATED(4, "隔离"),
	/**
	 * 测试
	 */
    EM_BYPASSMODE_TYPE_TEST(5, "测试");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_BYPASSMODE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BYPASSMODE_TYPE enumType : EM_BYPASSMODE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BYPASSMODE_TYPE enumType : EM_BYPASSMODE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

