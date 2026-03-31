package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 流量使用策略
 * @date 2023/03/16 14:28:39
 */
public enum EM_CFG_3GFLUXTACTIC {
	/**
	 * 未知类型
	 */
    EM_3GFLUXTACTIC_UNKNOWN(-1, "未知类型"),
	/**
	 * 按月包流量
	 */
    EM_3GFLUXTACTIC_BYFLUX(0, "按月包流量"),
	/**
	 * 按月包时长
	 */
    EM_3GFLUXTACTIC_BYTIME(1, "按月包时长");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_3GFLUXTACTIC(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_3GFLUXTACTIC enumType : EM_CFG_3GFLUXTACTIC.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_3GFLUXTACTIC enumType : EM_CFG_3GFLUXTACTIC.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

