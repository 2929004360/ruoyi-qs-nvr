package com.ruoyi.dahua.lib.enumeration;

public enum EM_CB_STATES {
    EM_CB_STATES_UNKNOWN(0, "Unknown"),// "Unknown"
    EM_CB_STATES_CREATED(1, "Created"),// "Created"
    EM_CB_STATES_READY(2, "Ready"),// "Ready"
    EM_CB_STATES_RUNNING(3, "Running"),// "Running"
    EM_CB_STATES_FINISHED(4, "Finished"),// "Finished"
    EM_CB_STATES_ERROR(5, "Error"),// "Error"
    EM_CB_STATES_ABORTED(6, "Aborted"),// "Aborted"
    EM_CB_STATES_PAUSE(7, "Pause");// "Pause"

	private int value;
	private String note;

	private EM_CB_STATES(int givenValue, String note) {
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

