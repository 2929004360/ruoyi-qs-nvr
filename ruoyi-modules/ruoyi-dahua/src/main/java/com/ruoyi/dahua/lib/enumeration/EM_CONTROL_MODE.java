package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 控制模式
 * @date 2021/09/01
 */
public enum EM_CONTROL_MODE {
	// 未知
    EM_CONTROL_UNKNOWN(0, "未知"),
	// 黄闪控制
    EM_CONTROL_YELLOW_FLASH(1, "黄闪控制"),
	// 多时段控制
    EM_CONTROL_MULITI_TIME(2, "多时段控制"),
	// 手动控制
    EM_CONTROL_MANUAL(3, "手动控制"),
	// 感应控制
    EM_CONTROL_INDUCTION(4, "感应控制"),
	// 无电缆协调控制
    EM_CONTROL_WIRELESS_COORDINATION(5, "无电缆协调控制"),
	// 单点协调控制
    EM_CONTROL_SINGLE_OPTIMIZATION(6, "单点协调控制"),
	//公交信号优先
    EM_CONTROL_BUS_SINGAL(7, "公交信号优先"),
	// 紧急信号优先
    EM_CONTROL_EMERGENCY_SINGAL(8, "紧急信号优先"),
	// 其他
    EM_CONTROL_OTHER(9, "其他");

	private int value;
	private String note;

	private EM_CONTROL_MODE(int givenValue, String note) {
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
		for (EM_CONTROL_MODE enumType : EM_CONTROL_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CONTROL_MODE enumType : EM_CONTROL_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

