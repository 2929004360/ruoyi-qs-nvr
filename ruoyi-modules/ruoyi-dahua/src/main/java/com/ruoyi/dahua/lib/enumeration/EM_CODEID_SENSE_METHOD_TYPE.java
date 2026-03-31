package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 传感器方式
 * @date 2023/03/16 14:52:52
 */
public enum EM_CODEID_SENSE_METHOD_TYPE {
	/**
	 * 未知的
	 */
    EM_CODEID_SENSE_METHOD_TYPE_UNKOWN(0, "未知的"),
	/**
	 * 门磁
	 */
    EM_CODEID_SENSE_METHOD_TYPE_DOOR_MAGNETISM(1, "门磁"),
	/**
	 * 燃气传感
	 */
    EM_CODEID_SENSE_METHOD_TYPE_GAS_SENSOR(2, "燃气传感"),
	/**
	 * 幕帘传感器
	 */
    EM_CODEID_SENSE_METHOD_TYPE_CURTAIN_SENSOR(3, "幕帘传感器"),
	/**
	 * 移动传感器
	 */
    EM_CODEID_SENSE_METHOD_TYPE_MOBILE_SENSOR(4, "移动传感器"),
	/**
	 * 被动红外传感器
	 */
    EM_CODEID_SENSE_METHOD_TYPE_PASSIVEINFRA(5, "被动红外传感器"),
	/**
	 * 紧急按钮
	 */
    EM_CODEID_SENSE_METHOD_TYPE_URGENCY_BUTTON(6, "紧急按钮"),
	/**
	 * 烟雾传感器
	 */
    EM_CODEID_SENSE_METHOD_TYPE_SMOKING_SENSOR(7, "烟雾传感器"),
	/**
	 * 双鉴传感器(红外+微波)
	 */
    EM_CODEID_SENSE_METHOD_TYPE_DOUBLEMETHOD(8, "双鉴传感器(红外+微波)"),
	/**
	 * 水浸传感器
	 */
    EM_CODEID_SENSE_METHOD_TYPE_WATER_SENSOR(9, "水浸传感器"),
	/**
	 * 三技术
	 */
    EM_CODEID_SENSE_METHOD_TYPE_THREEMETHOD(10, "三技术");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CODEID_SENSE_METHOD_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CODEID_SENSE_METHOD_TYPE enumType : EM_CODEID_SENSE_METHOD_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CODEID_SENSE_METHOD_TYPE enumType : EM_CODEID_SENSE_METHOD_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

