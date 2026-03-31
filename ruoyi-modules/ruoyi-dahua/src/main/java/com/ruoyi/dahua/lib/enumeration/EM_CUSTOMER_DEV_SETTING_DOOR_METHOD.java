package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 开门方式
 * @date 2022/06/02 10:15:21
 */
public enum EM_CUSTOMER_DEV_SETTING_DOOR_METHOD {
	/**
	 * 未知
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_UNKNOWN(-1, "未知"),
	/**
	 * CTID 码开门
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_CTID(0, "CTID 码开门"),
	/**
	 * CTID 码 + 人脸组合开门
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_CTID_AND_FACE(1, "CTID 码 + 人脸组合开门"),
	/**
	 * CTID 码或人脸开门
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_CTID_OR_FACE(2, "CTID 码或人脸开门"),
	/**
	 * 腕带码或人脸开门
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_WRISTBAND_OR_FACE(3, "腕带码或人脸开门"),
	/**
	 * 腕带码或人脸或证件开门
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_WRISTBAND_OR_FACE_OR_IDCARD(4, "腕带码或人脸或证件开门"),
	/**
	 * 码1或码2开门 (码1、码2的含义由平台和设备双方约定)
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_CODE1_OR_CODE2(5, "码1或码2开门 (码1、码2的含义由平台和设备双方约定)"),
	/**
	 * 人脸或腕带码或码2
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_WRISTBAND_OR_FACE_OR_CODE2(6, "人脸或腕带码或码2"),
	/**
	 * 人脸+（码1或码2）
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_FACE_AND_CODE1_OR_CODE2(7, "人脸+（码1或码2）"),
	/**
	 * （人脸或证件）+（码1或者码2）
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_FACE_OR_IDCARD_AND_CODE1_OR_CODE2(8, "（人脸或证件）+（码1或者码2）"),
	/**
	 * 人脸或码1或码2或者腕带码
	 */
    EM_CUSTOMER_DEV_SETTING_DOOR_METHOD_FACE_OR_IDCARD_OR_CODE1_OR_CODE2(9, "人脸或码1或码2或者腕带码");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CUSTOMER_DEV_SETTING_DOOR_METHOD(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CUSTOMER_DEV_SETTING_DOOR_METHOD enumType : EM_CUSTOMER_DEV_SETTING_DOOR_METHOD.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CUSTOMER_DEV_SETTING_DOOR_METHOD enumType : EM_CUSTOMER_DEV_SETTING_DOOR_METHOD.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

