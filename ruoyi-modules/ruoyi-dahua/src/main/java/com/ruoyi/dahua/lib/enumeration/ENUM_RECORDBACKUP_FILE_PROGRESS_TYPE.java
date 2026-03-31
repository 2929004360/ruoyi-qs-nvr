package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 最后传递成功的数据包类型
 * @date 2021/11/07
 */
public enum ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE {
	// 未知
    ENUM_RECORDBACKUP_FILE_PROGRESS_UNKNOWN(0, "未知"),
	// 文件头包
    ENUM_RECORDBACKUP_FILE_PROGRESS_HEADER_PACKAGE(1, "文件头包"),
	// 索引包
    ENUM_RECORDBACKUP_FILE_PROGRESS_INDEX_PACKAGE(2, "索引包"),
	// 码流包
    ENUM_RECORDBACKUP_FILE_PROGRESS_STREAM_PACKET(3, "码流包"),
	// 文件尾空包
    ENUM_RECORDBACKUP_FILE_PROGRESS_EMPTY_PACKET(4, "文件尾空包");

	private int value;
	private String note;

	private ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE(int givenValue, String note) {
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
		for (ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE enumType : ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE enumType : ENUM_RECORDBACKUP_FILE_PROGRESS_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

