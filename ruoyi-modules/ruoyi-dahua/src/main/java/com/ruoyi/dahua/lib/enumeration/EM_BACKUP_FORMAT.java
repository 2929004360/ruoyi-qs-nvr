package com.ruoyi.dahua.lib.enumeration;
/**
 * 
 * @author 119178
 *
 */
public enum EM_BACKUP_FORMAT {
    EM_BACKUP_FORMAT_ASF(0, "asf"),// "asf"
    EM_BACKUP_FORMAT_DAV(1, "dav"),// "dav"
    EM_BACKUP_FORMAT_MP4(2, "mp4");// "mp4"

	private int value;
	private String note;

	private EM_BACKUP_FORMAT(int givenValue, String note) {
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
		for (EM_TRAFFICSTROBE_STATUS enumType : EM_TRAFFICSTROBE_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TRAFFICSTROBE_STATUS enumType : EM_TRAFFICSTROBE_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

