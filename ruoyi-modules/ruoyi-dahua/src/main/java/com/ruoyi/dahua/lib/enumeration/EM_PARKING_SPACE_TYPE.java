package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 车位有车事件类型
 * @origin autoTool
 * @date 2023/06/20 10:13:16
 */
public enum EM_PARKING_SPACE_TYPE {
	/**
     * 未知
     */
    EM_PARKING_SPACE_TYPE_UNKNOWN(-1, "未知"),
	/**
     * 正常驶入
     */
    EM_PARKING_SPACE_TYPE_NORMAL(0, "正常驶入"),
	/**
     * 泊车变更事件
     */
    EM_PARKING_SPACE_TYPE_CHANGE(1, "泊车变更事件");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_PARKING_SPACE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_PARKING_SPACE_TYPE enumType : EM_PARKING_SPACE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_PARKING_SPACE_TYPE enumType : EM_PARKING_SPACE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_PARKING_SPACE_TYPE getEnum(int value) {
		for (EM_PARKING_SPACE_TYPE e : EM_PARKING_SPACE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_PARKING_SPACE_TYPE.EM_PARKING_SPACE_TYPE_UNKNOWN;
	}
}

