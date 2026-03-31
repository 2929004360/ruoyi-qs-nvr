package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @description 区域防区操作用户
 * @date 2022/07/19 10:09:08
 */
public enum EM_AREAARM_USER {
	/**
	 * 未知
	 */
    EM_AREAARM_USER_UNKNOWN(0, "未知"),
	/**
	 *
	 */
    EM_AREAARM_USER_SUPERVISOR(1, ""),
	/**
	 * 管理用户
	 */
    EM_AREAARM_USER_MANAGER(2, "管理用户"),
	/**
	 * 主用户
	 */
    EM_AREAARM_USER_MASTER(3, "主用户"),
	/**
	 * 普通用户
	 */
    EM_AREAARM_USER_USER(4, "普通用户"),
	/**
	 * 临时用户
	 */
    EM_AREAARM_USER_TEMPORARY(5, "临时用户"),
	/**
	 * 胁迫用户
	 */
    EM_AREAARM_USER_DURESS(6, "胁迫用户"),
	/**
	 * 巡逻用户
	 */
    EM_AREAARM_USER_PATROL(7, "巡逻用户");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_AREAARM_USER(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_AREAARM_USER enumType : EM_AREAARM_USER.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_AREAARM_USER enumType : EM_AREAARM_USER.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_AREAARM_USER getEnum(int value) {
		for (EM_AREAARM_USER e : EM_AREAARM_USER.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_AREAARM_USER.EM_AREAARM_USER_UNKNOWN;
	}
}

