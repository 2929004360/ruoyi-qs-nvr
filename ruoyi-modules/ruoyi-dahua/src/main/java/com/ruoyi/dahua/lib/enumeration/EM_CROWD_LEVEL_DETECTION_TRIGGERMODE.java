package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 触发模式
 * @origin autoTool
 * @date 2023/06/19 14:48:39
 */
public enum EM_CROWD_LEVEL_DETECTION_TRIGGERMODE {
	/**
     * 未知
     */
    EM_CROWD_LEVEL_DETECTION_TRIGGERMODE_UNKNOWN(0, "未知"),
	/**
     * 自适应模式
     */
    EM_CROWD_LEVEL_DETECTION_TRIGGERMODE_ADAPTIVE(1, "自适应模式"),
	/**
     * 自定义模式
     */
    EM_CROWD_LEVEL_DETECTION_TRIGGERMODE_CUSTOM(2, "自定义模式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CROWD_LEVEL_DETECTION_TRIGGERMODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CROWD_LEVEL_DETECTION_TRIGGERMODE enumType : EM_CROWD_LEVEL_DETECTION_TRIGGERMODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CROWD_LEVEL_DETECTION_TRIGGERMODE enumType : EM_CROWD_LEVEL_DETECTION_TRIGGERMODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_CROWD_LEVEL_DETECTION_TRIGGERMODE getEnum(int value) {
		for (EM_CROWD_LEVEL_DETECTION_TRIGGERMODE e : EM_CROWD_LEVEL_DETECTION_TRIGGERMODE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_CROWD_LEVEL_DETECTION_TRIGGERMODE.EM_CROWD_LEVEL_DETECTION_TRIGGERMODE_UNKNOWN;
	}
}

