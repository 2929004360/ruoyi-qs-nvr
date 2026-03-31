package com.ruoyi.dahua.lib.enumeration;
/**
 * 
 * @author 119178
 *
 */
public enum EM_SOURCE_MODE {
    EM_SOURCE_MODE_UNKNOWN(0, "未知"),// 未知
    EM_SOURCE_MODE_SINGLE(1, "Single/单个备份模式"),// "Single",/*单个备份模式*/
    EM_SOURCE_MODE_COMBIN(2, "Combin/合并备份模式");// "Combin",/*合并备份模式*/

	private int value;
	private String note;

	private EM_SOURCE_MODE(int givenValue, String note) {
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
		for (EM_TRAFFICSTROBE_STATUS enumType : EM_TRAFFICSTROBE_STATUS.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_TRAFFICSTROBE_STATUS enumType : EM_TRAFFICSTROBE_STATUS.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

