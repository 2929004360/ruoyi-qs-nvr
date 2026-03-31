package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 灯组故障等级
 * @date 2021/09/28
 */
public enum EM_LIGHTGROUP_FAULT_LEVEL {
	// 未知
    EM_LIGHTGROUP_FAULT_UNKNOWN(0, "未知"),
	// 忽略所有灯组故障
    EM_LIGHTGROUP_FAULT_INGORE(1, "忽略所有灯组故障 "),
	// 忽略车行灯黄灯、绿灯和人行灯故障
    EM_LIGHTGROUP_FAULT_INGORE_YELLOWGREENPEDESTRIAN(2, "忽略车行灯黄灯、绿灯和人行灯故障 "),
	// 忽略车行灯黄灯、红灯和人行灯故障
    EM_LIGHTGROUP_FAULT_INGORE_YELLOWREDPEDESTRIAN(3, "忽略车行灯黄灯、红灯和人行灯故障"),
	// 忽略车行灯黄灯和人行灯故障
    EM_LIGHTGROUP_FAULT_INGORE_YELLOWPEDESTRIAN(4, "忽略车行灯黄灯和人行灯故障 "),
	// 不忽略任何灯组故障
    EM_LIGHTGROUP_FAULT_UNINGORE(5, "不忽略任何灯组故障");

	private int value;
	private String note;

	private EM_LIGHTGROUP_FAULT_LEVEL(int givenValue, String note) {
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
		for (EM_LIGHTGROUP_FAULT_LEVEL enumType : EM_LIGHTGROUP_FAULT_LEVEL.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_LIGHTGROUP_FAULT_LEVEL enumType : EM_LIGHTGROUP_FAULT_LEVEL.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

