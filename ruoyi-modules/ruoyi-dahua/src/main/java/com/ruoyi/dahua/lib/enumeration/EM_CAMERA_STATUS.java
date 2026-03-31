package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 相机状态
 * @date 2021/08/25
 */
public enum EM_CAMERA_STATUS {
	// 未知
    EM_CAMERA_UNKNOWN(0, "未知"),
	// 红灯
    EM_CAMERA_RED(1, "红灯"),
	// 黄灯
    EM_CAMERA_YELLOW(2, "黄灯"),
	// 绿灯
    EM_CAMERA_GREEN(3, "绿灯"),
	// 红闪
    EM_CAMERA_RED_FLASH(4, "红闪"),
	// 黄闪
    EM_CAMERA_YELLOW_FLASH(5, "黄闪"),
	// 绿闪
    EM_CAMERA_GREEN_FLASH(6, "绿闪"),
	// 灭灯
    EM_CAMERA_LIGHT_OFF(7, "灭灯");

	private int value;
	private String note;

	private EM_CAMERA_STATUS(int givenValue, String note) {
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
		for (EM_CAMERA_STATUS enumType : EM_CAMERA_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CAMERA_STATUS enumType : EM_CAMERA_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

