package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 比对结果
 * @date 2021/08/15
 */
public enum EM_COMPARE_RESULT {
	// 未知
    EM_COMPARE_RESULT_UNKNOWN(-1, "未知"),
	// 成功
    EM_COMPARE_RESULT_SUCCESS(0, "成功"),
	// 其他错误
    EM_COMPARE_RESULT_OTHERERROR(1, "其他错误"),
    // 不在人脸库中
    EM_COMPARE_RESULT_NOTINDATABASE(2, "不在人脸库中"),
    // 超过单日进门次数限制
    EM_COMPARE_RESULT_EXCEED_SINGLE_DAY_ENTER_TIMES(3, "超过单日进门次数限制"),
    // 无进门记录，离开失败
    EM_COMPARE_RESULT_NO_ENTER_RECORD_LEAVE_FAIL(4, "无进门记录，离开失败"),
    // 区域人员已满
    EM_COMPARE_RESULT_AREA_FULL(5, "区域人员已满"),
	// 防反潜验证失败(没有正常出门记录再次进门)
    EM_COMPARE_RESULT_REPEATENTER_CHECK_FAIL(6, "防反潜验证失败(没有正常出门记录再次进门)");

	private int value;
	private String note;

	private EM_COMPARE_RESULT(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_COMPARE_RESULT enumType : EM_COMPARE_RESULT.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_COMPARE_RESULT enumType : EM_COMPARE_RESULT.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

