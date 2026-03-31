package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 数据类型
 * @date 2022/10/28 11:06:44
 */
public enum NET_EM_VS_DATA_TYPE {
	/**
	 * 数据是当前人数的横坐标
	 */
    NET_EM_VS_DATA_TYPE_X(0, "数据是当前人数的横坐标");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_VS_DATA_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_VS_DATA_TYPE enumType : NET_EM_VS_DATA_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_VS_DATA_TYPE enumType : NET_EM_VS_DATA_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

