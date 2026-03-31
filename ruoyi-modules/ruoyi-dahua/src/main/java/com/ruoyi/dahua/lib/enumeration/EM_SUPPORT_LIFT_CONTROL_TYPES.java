package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 支持的梯控制方式类型
 * @origin autoTool
 * @date 2023/08/24 14:19:16
 */
public enum EM_SUPPORT_LIFT_CONTROL_TYPES {
	/**
	 * 未知
	 */
    EM_SUPPORT_LIFT_CONTROL_TYPES_UNKNOWN(0, "未知"),
	/**
	 * 本地485协议梯控方式
	 */
    EM_SUPPORT_LIFT_CONTROL_TYPES_LOCAL485(1, "本地485协议梯控方式"),
	/**
	 * 从门口机联动主门口进行召梯
	 */
    EM_SUPPORT_LIFT_CONTROL_TYPES_LINKMAINVTO(2, "从门口机联动主门口进行召梯"),
	/**
	 * 联动梯控主机召梯
	 */
    EM_SUPPORT_LIFT_CONTROL_TYPES_LINkLIFTCONTROLHOST(3, "联动梯控主机召梯");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_SUPPORT_LIFT_CONTROL_TYPES(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_SUPPORT_LIFT_CONTROL_TYPES enumType : EM_SUPPORT_LIFT_CONTROL_TYPES.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_SUPPORT_LIFT_CONTROL_TYPES enumType : EM_SUPPORT_LIFT_CONTROL_TYPES.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

