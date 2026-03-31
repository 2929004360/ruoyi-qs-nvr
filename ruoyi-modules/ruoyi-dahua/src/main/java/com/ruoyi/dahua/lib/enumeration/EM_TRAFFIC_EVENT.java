package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 设备状态
 * @date 2021/09/02
 */
public enum EM_TRAFFIC_EVENT {
	// 未知
    EM_TRAFFIC_EVENT_UNKNOWN(0, "未知"),
	// 交通事故
    EM_TRAFFIC_EVENT_ACCIDENT(1, "交通事故"),
	// 道路障碍
    EM_TRAFFIC_EVENT_ROAD_BARRIERS(2, "道路障碍"),
	// 路面积水
    EM_TRAFFIC_EVENT_ROAD_AREAWATER(3, "路面积水"),
	// 路面湿滑
    EM_TRAFFIC_EVENT_ROAD_SLIPPERY(4, "路面湿滑"),
	// 路面结冰
    EM_TRAFFIC_EVENT_ROAD_ICY(5, "路面结冰"),
	// 道路施工
    EM_TRAFFIC_EVENT_ROAD_CONSTRUCTION(6, "道路施工");

	private int value;
	private String note;

	private EM_TRAFFIC_EVENT(int givenValue, String note) {
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
		for (EM_TRAFFIC_EVENT enumType : EM_TRAFFIC_EVENT.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TRAFFIC_EVENT enumType : EM_TRAFFIC_EVENT.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

