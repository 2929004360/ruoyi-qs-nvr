package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 智能家居情景模式
 * @date 2023/03/15 20:39:47
 */
public enum EM_SMARTHOME_SCENE_MODE {
	/**
	 * 未知
	 */
    EM_SMARTHOME_SCENE_MODE_UNKNOWN(0, "未知"),
	/**
	 * 在家
	 */
    EM_SMARTHOME_SCENE_MODE_AT_HOME(1, "在家"),
	/**
	 * 离开
	 */
    EM_SMARTHOME_SCENE_MODE_LEAVE_HOME(2, "离开"),
	/**
	 * 睡眠
	 */
    EM_SMARTHOME_SCENE_MODE_IN_SLEEPING(3, "睡眠");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SMARTHOME_SCENE_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SMARTHOME_SCENE_MODE enumType : EM_SMARTHOME_SCENE_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SMARTHOME_SCENE_MODE enumType : EM_SMARTHOME_SCENE_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

