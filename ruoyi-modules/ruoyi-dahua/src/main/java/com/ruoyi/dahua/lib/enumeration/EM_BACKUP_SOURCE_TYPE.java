package com.ruoyi.dahua.lib.enumeration;

public enum EM_BACKUP_SOURCE_TYPE {
    EM_SOURCE_TYPE_REALTIME(0, "RealTime"),// "RealTime", /*实时源*/,源类型有效
    EM_SOURCE_TYPE_LOCAL(1, "Local"),// "Local", /*本地源*/
    EM_SOURCE_TYPE_NETWORK(2, "Network");// "Network", /*网络源*/

	private int value;
	private String note;

	private EM_BACKUP_SOURCE_TYPE(int givenValue, String note) {
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

