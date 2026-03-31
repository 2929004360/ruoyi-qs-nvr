package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 计算特征向量结果
 * @origin autoTool
 * @date 2023/06/28 14:07:32
 */
public enum EM_ABSTRACT_STATE {
	/**
     * 未知
     */
    EM_ABSTRACT_UNKNOWN(0, "未知"),
	/**
     * 重建成功
     */
    EM_ABSTRACT_SUCCESS(1, "重建成功"),
	/**
     * 重建失败
     */
    EM_ABSTRACT_FALSE(2, "重建失败"),
	/**
     * 重建过程中(仅针对GroupID)
     */
    EM_ABSTRACT_PROCESS(3, "重建过程中(仅针对GroupID)");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ABSTRACT_STATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ABSTRACT_STATE enumType : EM_ABSTRACT_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ABSTRACT_STATE enumType : EM_ABSTRACT_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_ABSTRACT_STATE getEnum(int value) {
		for (EM_ABSTRACT_STATE e : EM_ABSTRACT_STATE.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_ABSTRACT_STATE.EM_ABSTRACT_UNKNOWN;
	}
}

