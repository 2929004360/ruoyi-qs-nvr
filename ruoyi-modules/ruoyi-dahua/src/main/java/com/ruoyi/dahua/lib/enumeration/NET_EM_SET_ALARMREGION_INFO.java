package com.ruoyi.dahua.lib.enumeration;

import com.ruoyi.dahua.lib.structure.*;

/**
 * @author 251823
 * @description 设置的操作类型
 * @date 2023/03/15 15:58:57
 */
public enum NET_EM_SET_ALARMREGION_INFO {
	/**
	 * 未知
	 */
    NET_EM_SET_ALARMREGION_INFO_UNKNOWN(0, "未知"),
	/**
	 * 设置布防模式， 此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为{@link NET_IN_SET_ALARMMODE}，
	 * pstuOutParam类型为{@link NET_OUT_SET_ALARMMODE}
	 */
    NET_EM_SET_ALARMREGION_INFO_ARMMODE(1, "设置布防模式，	此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_SET_ALARMMODE， pstuOutParam类型为NET_OUT_SET_ALARMMODE"),
	/**
	 * 设置旁路模式， 此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为{@link NET_IN_SET_BYPASSMODE}，
	 * pstuOutParam类型为{@link NET_OUT_SET_BYPASSMODE}
	 */
    NET_EM_SET_ALARMREGION_INFO_BYPASSMODE(2, "设置旁路模式，	此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_SET_BYPASSMODE， pstuOutParam类型为NET_OUT_SET_BYPASSMODE"),
	/**
	 * 设置输出状态， 此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为{@link NET_IN_SET_OUTPUT_STATE}，
	 * pstuOutParam类型为{@link NET_OUT_SET_OUTPUT_STATE}
	 */
    NET_EM_SET_ALARMREGION_INFO_OUTPUTSTATE(3, "设置输出状态，	此时CLIENT_SetAlarmRegionInfo接口中的pstuInParam类型为NET_IN_SET_OUTPUT_STATE， pstuOutParam类型为NET_OUT_SET_OUTPUT_STATE");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	NET_EM_SET_ALARMREGION_INFO(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (NET_EM_SET_ALARMREGION_INFO enumType : NET_EM_SET_ALARMREGION_INFO.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (NET_EM_SET_ALARMREGION_INFO enumType : NET_EM_SET_ALARMREGION_INFO.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static NET_EM_SET_ALARMREGION_INFO getEnum(int value) {
		for (NET_EM_SET_ALARMREGION_INFO e : NET_EM_SET_ALARMREGION_INFO.values()) {
			if (e.getValue() == value)
				return e;
		}
		return NET_EM_SET_ALARMREGION_INFO.NET_EM_SET_ALARMREGION_INFO_UNKNOWN;
	}
}

