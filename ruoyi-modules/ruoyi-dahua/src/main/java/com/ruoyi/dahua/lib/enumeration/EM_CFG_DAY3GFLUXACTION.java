package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 流量报警策略
 * @date 2023/03/16 14:23:46
 */
public enum EM_CFG_DAY3GFLUXACTION {
	/**
	 * 无动作
	 */
    EM_CFG_DAY3GFLUXACTION_NOTHING(0, "无动作"),
	/**
	 * 3G下线
	 */
    EM_CFG_DAY3GFLUXACTION_3GNETDOWN(1, "3G下线");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_DAY3GFLUXACTION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_DAY3GFLUXACTION enumType : EM_CFG_DAY3GFLUXACTION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_DAY3GFLUXACTION enumType : EM_CFG_DAY3GFLUXACTION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

