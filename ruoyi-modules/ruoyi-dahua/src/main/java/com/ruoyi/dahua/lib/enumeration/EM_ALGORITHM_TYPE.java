package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 加密算法类型
 * @date 2022/11/03 09:43:17
 */
public enum EM_ALGORITHM_TYPE {
	/**
	 * 未知加密算法
	 */
    EM_ALGORITHM_TYPE_UNKNOWN(0, "未知加密算法"),
	/**
	 * AES256-OFB加密算法
	 */
    EM_ALGORITHM_TYPE_AES256_OFB(1, "AES256-OFB加密算法"),
	/**
	 * SM加密算法
	 */
    EM_ALGORITHM_TYPE_SM4_OFB(2, "SM加密算法");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_ALGORITHM_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_ALGORITHM_TYPE enumType : EM_ALGORITHM_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_ALGORITHM_TYPE enumType : EM_ALGORITHM_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

