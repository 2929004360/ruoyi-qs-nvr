package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 状态名称
 * @date 2023/05/11 14:21:08
 */
public enum EM_STATEMANAGER_STATE {
	/**
	 * 智能终端箱的异常状态, 对应结构体NET_CASEABNORMAL_STATE_INFO
	 */
    EM_STATEMANAGER_STATE_CASEABNORMAL(0, "智能终端箱的异常状态"),
	/**
	 * 智能机箱的日常运行状态， 对应结构体NET_CASERUNNING_STATE_INFO
	 */
    EM_STATEMANAGER_STATE_CASERUNNING(1, "智能机箱的日常运行状态");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_STATEMANAGER_STATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_STATEMANAGER_STATE enumType : EM_STATEMANAGER_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_STATEMANAGER_STATE enumType : EM_STATEMANAGER_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

