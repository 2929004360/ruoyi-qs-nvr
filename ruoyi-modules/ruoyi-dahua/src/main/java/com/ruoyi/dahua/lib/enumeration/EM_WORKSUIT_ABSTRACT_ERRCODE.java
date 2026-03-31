package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 工装建模错误码
 * @date 2022/10/09 11:22:52
 */
public enum EM_WORKSUIT_ABSTRACT_ERRCODE {
	/**
	 * 未知
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_UNKNWON(0, "未知"),
	/**
	 * 建模失败
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_FAILED(1, "建模失败"),
	/**
	 * 系统异常（如Licence失效、建模分析器未启动导致的失败）
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_SYSERR(2, "系统异常（如Licence失效、建模分析器未启动导致的失败）"),
	/**
	 * 数据库操作失败
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_DB_ERROR(3, "数据库操作失败"),
	/**
	 * 工装数量超过限制
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_OVER_NUM(4, "工装数量超过限制"),
	/**
	 * 剩余存储空间大小不足
	 */
    EM_WORKSUIT_ABSTRACT_ERRCODE_NO_STORAGE(5, "剩余存储空间大小不足");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_WORKSUIT_ABSTRACT_ERRCODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_WORKSUIT_ABSTRACT_ERRCODE enumType : EM_WORKSUIT_ABSTRACT_ERRCODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_WORKSUIT_ABSTRACT_ERRCODE enumType : EM_WORKSUIT_ABSTRACT_ERRCODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

