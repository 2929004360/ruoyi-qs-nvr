package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 是否加密
 * @date 2022/11/03 09:43:17
 */
public enum EM_IS_ENCRYPT {
	/**
	 * 未知
	 */
    EM_IS_ENCRYPT_UNKNOWN(0, "未知"),
	/**
	 * 已加密
	 */
    EM_IS_ENCRYPT_YES(1, "已加密"),
	/**
	 * 未加密
	 */
    EM_IS_ENCRYPT_NO(2, "未加密");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_IS_ENCRYPT(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_IS_ENCRYPT enumType : EM_IS_ENCRYPT.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_IS_ENCRYPT enumType : EM_IS_ENCRYPT.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

