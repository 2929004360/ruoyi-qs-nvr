package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 火灾配置类型
 * @date 2023/05/24 10:24:48
 */
public enum NET_EM_FIREWARNING_MODE_TYPE {
	/**
     * 云台预置点模式（默认）
     */
    NET_EM_FIREWARNING_TYPE_PTZPRESET(0, "云台预置点模式（默认）"),
	/**
     * 空间排除模式
     */
    NET_EM_FIREWARNING_TYPE_SPACEEXCLUDE(1, "空间排除模式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_FIREWARNING_MODE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_FIREWARNING_MODE_TYPE enumType : NET_EM_FIREWARNING_MODE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_FIREWARNING_MODE_TYPE enumType : NET_EM_FIREWARNING_MODE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static NET_EM_FIREWARNING_MODE_TYPE getEnum(int value) {
		for (NET_EM_FIREWARNING_MODE_TYPE e : NET_EM_FIREWARNING_MODE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return NET_EM_FIREWARNING_MODE_TYPE.NET_EM_FIREWARNING_TYPE_PTZPRESET;
	}
}

