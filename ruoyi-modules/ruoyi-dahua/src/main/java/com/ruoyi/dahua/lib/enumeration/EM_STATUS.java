package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 设备状态
 * @date 2021/09/01
 */
public enum EM_STATUS {
	// 未知
    EM_STATUS_UNKNOWN(-1, "未知"),
	// 无效
    EM_STATUS_INVALID(0, "无效"),
	// 工作正常
    EM_STATUS_NORMAL(1, "工作正常"),
	// 故障状态
    EM_STATUS_FAULT(2, "故障状态"),
	// 其他
    EM_STATUS_OTHER(3, "其他");

	private int value;
	private String note;

	private EM_STATUS(int givenValue, String note) {
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
		for (EM_STATUS enumType : EM_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_STATUS enumType : EM_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

