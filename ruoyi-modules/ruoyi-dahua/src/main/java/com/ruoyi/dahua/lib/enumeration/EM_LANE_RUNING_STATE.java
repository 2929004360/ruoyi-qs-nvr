package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 车道交通运行状态
 * @date 2021/09/01
 */
public enum EM_LANE_RUNING_STATE {
	// 未知
    EM_LANE_RUNING_UNKNOWN(0, "未知"),
	// 畅通
    EM_LANE_RUNING_UNBLOCKED(1, "畅通"),
	// 基本畅通
    EM_LANE_RUNING_BASIC_UNBLOCKED(2, "基本畅通"),
	// 轻度拥堵
    EM_LANE_RUNING_LIGHT_CONGESTION(3, "轻度拥堵"),
	// 中度拥堵
    EM_LANE_RUNING_MIDDLE_CONGESTION(4, "中度拥堵"),
	// 严重拥堵
    EM_LANE_RUNING_SERIOUS_CONGESTION(5, "严重拥堵");

	private int value;
	private String note;

	private EM_LANE_RUNING_STATE(int givenValue, String note) {
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
		for (EM_LANE_RUNING_STATE enumType : EM_LANE_RUNING_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_LANE_RUNING_STATE enumType : EM_LANE_RUNING_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

