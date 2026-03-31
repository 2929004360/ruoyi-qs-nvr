package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 码流类型
 * @date 2022/07/20 14:11:30
 */
public enum CFG_EM_STREAM_TYPES {
	/**
	 * 其它
	 */
    CFG_EM_STREAMTYPE_ERR(0, "其它"),
	/**
	 * Main -主码流
	 */
    CFG_EM_STREAMTYPE_MAIN(1, "Main -主码流"),
	/**
	 * Extra1 -辅码流1
	 */
    CFG_EM_STREAMTYPE_EXTRA_1(2, "Extra1 -辅码流1"),
	/**
	 * Extra2 -辅码流2
	 */
    CFG_EM_STREAMTYPE_EXTRA_2(3, "Extra2 -辅码流2"),
	/**
	 * Extra3 -辅码流3
	 */
    CFG_EM_STREAMTYPE_EXTRA_3(4, "Extra3 -辅码流3"),
	/**
	 * Snapshot -抓图码流
	 */
    CFG_EM_STREAMTYPE_SNAPSHOT(5, "Snapshot -抓图码流"),
	/**
	 * Talkback -对讲码流
	 */
    CFG_EM_STREAMTYPE_TALKBACK(6, "Talkback -对讲码流");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	CFG_EM_STREAM_TYPES(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (CFG_EM_STREAM_TYPES enumType : CFG_EM_STREAM_TYPES.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (CFG_EM_STREAM_TYPES enumType : CFG_EM_STREAM_TYPES.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

