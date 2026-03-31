package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 智能停车数据类型
 * @date 2022/10/28 11:30:56
 */
public enum NET_EM_INTELLIPARKING_DATA_TYPE {
	/**
	 * 数据是规划式停车场车位ID的使用情况，PlanArea字段有效
	 */
    NET_EM_INTELLIPARKING_DATA_TYPE_PLANAREA(0, "数据是规划式停车场车位ID的使用情况，PlanArea字段有效"),
	/**
	 * 数据是开放式停车场停车信息，OpenArea字段有效
	 */
    NET_EM_INTELLIPARKING_DATA_TYPE_OPENAREA(1, "数据是开放式停车场停车信息，OpenArea字段有效");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_INTELLIPARKING_DATA_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_INTELLIPARKING_DATA_TYPE enumType : NET_EM_INTELLIPARKING_DATA_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_INTELLIPARKING_DATA_TYPE enumType : NET_EM_INTELLIPARKING_DATA_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

