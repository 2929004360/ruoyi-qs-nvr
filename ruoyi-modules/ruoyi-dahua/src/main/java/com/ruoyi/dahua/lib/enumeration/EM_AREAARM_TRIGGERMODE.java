package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @description 区域防区操作方式
 * @date 2022/07/19 10:09:08
 */
public enum EM_AREAARM_TRIGGERMODE {
	/**
	 * 未知
	 */
    EM_AREAARM_TRIGGERMODE_UNKNOWN(0, "未知"),
	/**
	 * 键盘
	 */
    EM_AREAARM_TRIGGERMODE_KEYPAD(1, "键盘"),
	/**
	 * 遥控器
	 */
    EM_AREAARM_TRIGGERMODE_REMOTECONTROL(2, "遥控器"),
	/**
	 * 用户操作
	 */
    EM_AREAARM_TRIGGERMODE_USER(3, "用户操作"),
	/**
	 * 本地
	 */
    EM_AREAARM_TRIGGERMODE_LOCAL(4, "本地"),
	/**
	 * 定时器
	 */
    EM_AREAARM_TRIGGERMODE_TIMER(5, "定时器"),
	/**
	 * Key类型防区
	 */
    EM_AREAARM_TRIGGERMODE_KEY(6, "Key类型防区"),
	/**
	 * 远程操作(电话反控、短信反控、手机App、平台客户端等)
	 */
    EM_AREAARM_TRIGGERMODE_REMOTE(7, "远程操作(电话反控、短信反控、手机App、平台客户端等)"),
	/**
	 * DSS平台客户端
	 */
    EM_AREAARM_TRIGGERMODE_DSS(8, "DSS平台客户端"),
	/**
	 * DSSPro平台客户端
	 */
    EM_AREAARM_TRIGGERMODE_DSSPRO(9, "DSSPro平台客户端"),
	/**
	 * 手机客户端
	 */
    EM_AREAARM_TRIGGERMODE_DMSS(10, "手机客户端"),
	/**
	 * Smart pss、ICC平台
	 */
    EM_AREAARM_TRIGGERMODE_PLATFORM(11, "Smart pss、ICC平台");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_AREAARM_TRIGGERMODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_AREAARM_TRIGGERMODE enumType : EM_AREAARM_TRIGGERMODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_AREAARM_TRIGGERMODE enumType : EM_AREAARM_TRIGGERMODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_AREAARM_TRIGGERMODE getEnum(int value) {
		for (EM_AREAARM_TRIGGERMODE e : EM_AREAARM_TRIGGERMODE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_AREAARM_TRIGGERMODE.EM_AREAARM_TRIGGERMODE_UNKNOWN;
	}
}

