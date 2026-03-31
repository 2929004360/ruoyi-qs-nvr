package com.ruoyi.dahua.lib.enumeration;

public enum EM_BACKUP_TARGET_MODE {
    EM_TARGET_MODE_SYNC(0, "Sync"),// "Sync", /*同步备份*/
    EM_TARGET_MODE_TURN(1, "Turn"),// "Turn", /*轮训备份*/
    EM_TARGET_MODE_CYCLE(2, "Cycle");// "Cycle", /*循环备份*/

	private int value;
	private String note;

	private EM_BACKUP_TARGET_MODE(int givenValue, String note) {
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

