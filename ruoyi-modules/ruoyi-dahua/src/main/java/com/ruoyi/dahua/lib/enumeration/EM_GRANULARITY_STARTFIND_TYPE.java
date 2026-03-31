package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 查询要求返回的统计信息粒度
 * @date 2022/11/03 10:19:57
 */
public enum EM_GRANULARITY_STARTFIND_TYPE {
	/**
	 * 未知
	 */
    EM_GRANULARITY_TYPE_UNKNOWN(0, "未知"),
	/**
	 * 按5分钟(时报表)
	 */
    EM_GRANULARITY_TYPE_MINUTE(1, "按5分钟(时报表)"),
	/**
	 * 按小时(日报表)
	 */
    EM_GRANULARITY_TYPE_HOUR(2, "按小时(日报表)"),
	/**
	 * 按天(月报表)
	 */
    EM_GRANULARITY_TYPE_DAY(3, "按天(月报表)"),
	/**
	 * 按月(年报表)
	 */
    EM_GRANULARITY_TYPE_MONTH(4, "按月(年报表)");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_GRANULARITY_STARTFIND_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_GRANULARITY_STARTFIND_TYPE enumType : EM_GRANULARITY_STARTFIND_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_GRANULARITY_STARTFIND_TYPE enumType : EM_GRANULARITY_STARTFIND_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

