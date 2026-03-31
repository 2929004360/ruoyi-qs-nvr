package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 人脸导入的状态
 * @date 2022/10/31 11:10:10
 */
public enum EM_FACE_APPEND_STATE {
	/**
	 * 未知
	 */
    EM_FACE_APPEND_STATE_UNKNOWN(0, "未知"),
	/**
	 * 开始导入
	 */
    EM_FACE_APPEND_STATE_START(1, "开始导入"),
	/**
	 * 正在导入
	 */
    EM_FACE_APPEND_STATE_RUN(2, "正在导入"),
	/**
	 * 导入结束
	 */
    EM_FACE_APPEND_STATE_STOP(3, "导入结束"),
	/**
	 * 导入取消
	 */
    EM_FACE_APPEND_STATE_CANCLE(4, "导入取消");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_FACE_APPEND_STATE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_FACE_APPEND_STATE enumType : EM_FACE_APPEND_STATE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_FACE_APPEND_STATE enumType : EM_FACE_APPEND_STATE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

