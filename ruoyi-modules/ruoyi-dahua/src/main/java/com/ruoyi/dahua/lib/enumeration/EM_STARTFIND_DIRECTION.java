package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 统计方向查询条件
 * @date 2022/11/03 10:19:57
 */
public enum EM_STARTFIND_DIRECTION {
	/**
	 * 未知
	 */
    EM_STARTFIND_DIRECTION_UNKNOWN(0, "未知"),
	/**
	 * 全部
	 */
    EM_STARTFIND_DIRECTION_BOTH(1, "全部"),
	/**
	 * 左到右
	 */
    EM_STARTFIND_DIRECTION_LEFTTORIGHT(2, "左到右"),
	/**
	 * 右到左
	 */
    EM_STARTFIND_DIRECTION_RIGHTTOLEFT(3, "右到左");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_STARTFIND_DIRECTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_STARTFIND_DIRECTION enumType : EM_STARTFIND_DIRECTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_STARTFIND_DIRECTION enumType : EM_STARTFIND_DIRECTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

