package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 通过全景图唯一标识符删除工装合规样本错误码信息
 * @date 2022/10/08 20:13:32
 */
public enum EM_DELETE_BY_SOURCEUID_ERRCODE {
	/**
	 * 未知
	 */
    EM_DELETE_BY_SOURCEUID_ERRCODE_UNKNWON(-1, "未知"),
	/**
	 * 成功
	 */
    EM_DELETE_BY_SOURCEUID_ERRCODE_SUCCESS(0, "成功"),
	/**
	 * 工装不存在
	 */
    EM_DELETE_BY_SOURCEUID_ERRCODE_NOT_EXIST(1, "工装不存在"),
	/**
	 * 数据库操作失败
	 */
    EM_DELETE_BY_SOURCEUID_ERRCODE_DB_ERROR(2, "数据库操作失败");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_DELETE_BY_SOURCEUID_ERRCODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_DELETE_BY_SOURCEUID_ERRCODE enumType : EM_DELETE_BY_SOURCEUID_ERRCODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_DELETE_BY_SOURCEUID_ERRCODE enumType : EM_DELETE_BY_SOURCEUID_ERRCODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_DELETE_BY_SOURCEUID_ERRCODE getEnum(int value) {
		for (EM_DELETE_BY_SOURCEUID_ERRCODE e : EM_DELETE_BY_SOURCEUID_ERRCODE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_DELETE_BY_SOURCEUID_ERRCODE.EM_DELETE_BY_SOURCEUID_ERRCODE_UNKNWON;
	}
}

