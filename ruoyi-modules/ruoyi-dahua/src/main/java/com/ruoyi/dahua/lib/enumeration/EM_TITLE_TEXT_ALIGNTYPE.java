package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 标题文本对齐方式
 * @date 2022/05/23 08:51:37
 */
public enum EM_TITLE_TEXT_ALIGNTYPE {
	/**
	 * 无效的对齐方式
	 */
    EM_TEXT_ALIGNTYPE_INVALID(0, "无效的对齐方式"),
	/**
	 * 左对齐
	 */
    EM_TEXT_ALIGNTYPE_LEFT(1, "左对齐"),
	/**
	 * X坐标中对齐
	 */
    EM_TEXT_ALIGNTYPE_XCENTER(2, "X坐标中对齐"),
	/**
	 * Y坐标中对齐
	 */
    EM_TEXT_ALIGNTYPE_YCENTER(3, "Y坐标中对齐"),
	/**
	 * 居中
	 */
    EM_TEXT_ALIGNTYPE_CENTER(4, "居中"),
	/**
	 * 右对齐
	 */
    EM_TEXT_ALIGNTYPE_RIGHT(5, "右对齐"),
	/**
	 * 按照顶部对齐
	 */
    EM_TEXT_ALIGNTYPE_TOP(6, "按照顶部对齐"),
	/**
	 * 按照底部对齐
	 */
    EM_TEXT_ALIGNTYPE_BOTTOM(7, "按照底部对齐"),
	/**
	 * 按照左上角对齐
	 */
    EM_TEXT_ALIGNTYPE_LEFTTOP(8, "按照左上角对齐"),
	/**
	 * 换行对齐
	 */
    EM_TEXT_ALIGNTYPE_CHANGELINE(9, "换行对齐");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_TITLE_TEXT_ALIGNTYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_TITLE_TEXT_ALIGNTYPE enumType : EM_TITLE_TEXT_ALIGNTYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TITLE_TEXT_ALIGNTYPE enumType : EM_TITLE_TEXT_ALIGNTYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

