package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 每日流量控制策略
 * @date 2023/03/16 14:23:46
 */
public enum EM_CFG_DAY3GFLUXTACTIC {
	/**
	 * 按流量
	 */
    EM_CFG_DAY3GFLUXTACTIC_BYFLUX(0, "按流量"),
	/**
	 * 按时间
	 */
    EM_CFG_DAY3GFLUXTACTIC_BYTIME(1, "按时间");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_DAY3GFLUXTACTIC(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_DAY3GFLUXTACTIC enumType : EM_CFG_DAY3GFLUXTACTIC.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_DAY3GFLUXTACTIC enumType : EM_CFG_DAY3GFLUXTACTIC.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

