package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 251823
 * @description 根据条件查询备份任务的查询方式
 * @date 2022/01/14
 */
public enum EM_RECORD_BACKUP_FIND_TYPE {
	// 未知
    EM_RECORD_BACKUP_FIND_TYPE_UNKNOWN(0, "未知"),
	// 按照任务号查询
    EM_RECORD_BACKUP_FIND_TYPE_BY_TASKID(1, "按照任务号查询"),
	// 按照通道和录制时间段查询
    EM_RECORD_BACKUP_FIND_TYPE_BY_CHN_AND_TIME(2, "按照通道和录制时间段查询"),
    // 按照主动注册的设备ID查
    EM_RECORD_BACKUP_FIND_TYPE_BY_DEVICE_ID(3, "按照主动注册的设备ID查");

	private int value;
	private String note;

	private EM_RECORD_BACKUP_FIND_TYPE(int givenValue, String note) {
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
		for (EM_RECORD_BACKUP_FIND_TYPE enumType : EM_RECORD_BACKUP_FIND_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_RECORD_BACKUP_FIND_TYPE enumType : EM_RECORD_BACKUP_FIND_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

