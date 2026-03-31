package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 布防的情景模式
 * @date 2023/03/15 16:05:03
 */
public enum NET_EM_SCENE_MODE {
	/**
	 * 未知模式
	 */
    NET_EM_SCENE_MODE_UNKNOWN(0, "未知模式"),
	/**
	 * 外出模式
	 */
    NET_EM_SCENE_MODE_OUTDOOR(1, "外出模式"),
	/**
	 * 在家模式
	 */
    NET_EM_SCENE_MODE_INDOOR(2, "在家模式"),
	/**
	 * 全局模式
	 */
    NET_EM_SCENE_MODE_WHOLE(3, "全局模式"),
	/**
	 * 立即模式
	 */
    NET_EM_SCENE_MODE_RIGHTNOW(4, "立即模式"),
	/**
	 * 自动模式
	 */
    NET_EM_SCENE_MODE_AUTO(5, "自动模式"),
	/**
	 * 强制模式
	 */
    NET_EM_SCENE_MODE_FORCE(6, "强制模式"),
	/**
	 * 自定义模式
	 */
    NET_EM_SCENE_MODE_CUSTOM(7, "自定义模式"),
	/**
	 * 就寝模式
	 */
    NET_EM_SCENE_MODE_SLEEPING(8, "就寝模式");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_SCENE_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_SCENE_MODE enumType : NET_EM_SCENE_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_SCENE_MODE enumType : NET_EM_SCENE_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

