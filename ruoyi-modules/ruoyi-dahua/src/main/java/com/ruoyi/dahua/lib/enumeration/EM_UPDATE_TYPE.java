package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 更新类型
 * @date 2022/05/11 13:27:51
 */
public enum EM_UPDATE_TYPE {
	/**
	 * 未知
	 */
    EM_UPDATE_TYPE_UNKNOWN(-1, "未知"),
	/**
	 * 删除区域或车位
	 */
    EM_UPDATE_TYPE_DELETE(0, "删除区域或车位"),
	/**
	 * 修改区域或车位
	 */
    EM_UPDATE_TYPE_MODIFY(1, "修改区域或车位"),
	/**
	 * 增加区域或车位
	 */
    EM_UPDATE_TYPE_ADD(2, "增加区域或车位");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_UPDATE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_UPDATE_TYPE enumType : EM_UPDATE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_UPDATE_TYPE enumType : EM_UPDATE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_UPDATE_TYPE getEnum(int value) {
		for (EM_UPDATE_TYPE e : EM_UPDATE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_UPDATE_TYPE.EM_UPDATE_TYPE_UNKNOWN;
	}
}

