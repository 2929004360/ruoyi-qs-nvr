package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 马赛克类型
 * @date 2022/07/21 15:29:16
 */
public enum EM_MOSAIC_TYPE {
	/**
	 * 无效的马赛克类型
	 */
    EM_MOSAIC_TYPE_INVALID(0, "无效的马赛克类型"),
	/**
	 * 不打马赛克
	 */
    EM_MOSAIC_TYPE_NO(1, "不打马赛克"),
	/**
	 * [8x8大小] 马赛克
	 */
    EM_MOSAIC_TYPE_8(2, "[8x8大小] 马赛克"),
	/**
	 * [16x16大小] 马赛克
	 */
    EM_MOSAIC_TYPE_16(3, "[16x16大小] 马赛克"),
	/**
	 * [24x24大小] 马赛克
	 */
    EM_MOSAIC_TYPE_24(4, "[24x24大小] 马赛克"),
	/**
	 * [32x32大小] 马赛克
	 */
    EM_MOSAIC_TYPE_32(5, "[32x32大小] 马赛克");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_MOSAIC_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_MOSAIC_TYPE enumType : EM_MOSAIC_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_MOSAIC_TYPE enumType : EM_MOSAIC_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_MOSAIC_TYPE getEnum(int value) {
		for (EM_MOSAIC_TYPE e : EM_MOSAIC_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_MOSAIC_TYPE.EM_MOSAIC_TYPE_INVALID;
	}
}

