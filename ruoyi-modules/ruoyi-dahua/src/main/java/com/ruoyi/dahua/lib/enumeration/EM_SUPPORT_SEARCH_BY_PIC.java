package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 是否支持以图搜图
 * @origin autoTool
 * @date 2023/07/31 09:25:34
 */
public enum EM_SUPPORT_SEARCH_BY_PIC {
	/**
     * 未知, IVSS, NVR, DVR大概率支持目标以图搜图，IPC-FR不支持
     */
    EM_SUPPORT_SEARCH_BY_PIC_UNKNOWN(0, "未知, IVSS, NVR, DVR大概率支持目标以图搜图，IPC-FR不支持"),
	/**
     * 不支持
     */
    EM_SUPPORT_SEARCH_BY_PIC_UNSUPPORT(1, "不支持"),
	/**
     * 支持
     */
    EM_SUPPORT_SEARCH_BY_PIC_SUPPORT(2, "支持");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SUPPORT_SEARCH_BY_PIC(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SUPPORT_SEARCH_BY_PIC enumType : EM_SUPPORT_SEARCH_BY_PIC.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SUPPORT_SEARCH_BY_PIC enumType : EM_SUPPORT_SEARCH_BY_PIC.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_SUPPORT_SEARCH_BY_PIC getEnum(int value) {
		for (EM_SUPPORT_SEARCH_BY_PIC e : EM_SUPPORT_SEARCH_BY_PIC.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EM_SUPPORT_SEARCH_BY_PIC.EM_SUPPORT_SEARCH_BY_PIC_UNKNOWN;
	}
}

