package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 灯组类型
 * @date 2021/09/01
 */
public enum EM_LAMP_TYPE {
	// 未知
    EM_LAMP_TYPE_UNKNOWN(0, "未知"),
	// 直行方向指示信号灯
    EM_LAMP_TYPE_STRAIGHT(1, "直行方向指示信号灯"),
	// 左转方向指示信号灯
    EM_LAMP_TYPE_LEFT(2, "左转方向指示信号灯"),
	// 右转方向指示信号灯
    EM_LAMP_TYPE_RIGHT(3, "右转方向指示信号灯"),
	// 机动车信号灯
    EM_LAMP_TYPE_MOTORVEHICLE(4, "机动车信号灯"),
	// 左转非机动车信号灯
    EM_LAMP_TYPE_LEFT_NOMOTOR(5, "左转非机动车信号灯"),
	// 右转非机动车信号灯
    EM_LAMP_TYPE_RIGHT_NOMOTOR(6, "右转非机动车信号灯"),
	// 非机动车信号灯
    EM_LAMP_TYPE_NOMOTOR(7, "非机动车信号灯"),
	// 人行横道信号灯
    EM_LAMP_TYPE_SIDEWALK(8, "人行横道信号灯"),
	// 掉头信号灯
    EM_LAMP_TYPE_TURNAROUND(9, "掉头信号灯"),
	// 车道信号灯
    EM_LAMP_TYPE_LANE(10, "车道信号灯"),
	// 道口信号灯
    EM_LAMP_TYPE_CROSSING(11, "道口信号灯"),
	// 闪光警告信号灯
    EM_LAMP_TYPE_FLASH_WARN(12, "闪光警告信号灯"),
	// 有轨电车专用信号灯（直行）
    EM_LAMP_TYPE_TRAM_DEDICATED_STRAIGHT(13, "有轨电车专用信号灯（直行）"),
	// 有轨电车专用信号灯（左转）
    EM_LAMP_TYPE_TRAM_DEDICATED_LEFT(14, "有轨电车专用信号灯（左转）"),
	// 有轨电车专用信号灯（右转）
    EM_LAMP_TYPE_TRAM_DEDICATED_RIGHT(15, "有轨电车专用信号灯（右转）");

	private int value;
	private String note;

	private EM_LAMP_TYPE(int givenValue, String note) {
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
		for (EM_LAMP_TYPE enumType : EM_LAMP_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_LAMP_TYPE enumType : EM_LAMP_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

