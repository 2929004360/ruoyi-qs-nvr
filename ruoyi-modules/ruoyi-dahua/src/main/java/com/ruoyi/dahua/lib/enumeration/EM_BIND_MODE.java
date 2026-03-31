package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 绑定模式
 * @origin autoTool
 * @date 2023/09/15 10:59:48
 */
public enum EM_BIND_MODE {
	/**
     * / 未知
     */
    EM_BIND_MODE_UNKNOWN(0, "/ 未知"),
	/**
     * / 自动
     */
    EM_BIND_MODE_AUTOMATIC(1, "/ 自动"),
	/**
     * / 半自动
     */
    EM_BIND_MODE_SEMIAUTOMATIC(2, "/ 半自动"),
	/**
     * / 手动
     */
    EM_BIND_MODE_MANUAL(3, "/ 手动");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_BIND_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_BIND_MODE enumType : EM_BIND_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_BIND_MODE enumType : EM_BIND_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_BIND_MODE getEnum(int value) {
		for (EM_BIND_MODE e : EM_BIND_MODE.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_BIND_MODE.EM_BIND_MODE_UNKNOWN;
	}
}

