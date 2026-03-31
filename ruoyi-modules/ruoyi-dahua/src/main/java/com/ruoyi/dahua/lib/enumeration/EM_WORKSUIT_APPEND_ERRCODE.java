package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 工装合规导入返回的错误码信息
 * @date 2022/10/08 19:50:19
 */
public enum EM_WORKSUIT_APPEND_ERRCODE {
	/**
	 * 未知
	 */
    EM_WORKSUIT_APPEND_ERRCODE_UNKNOWN(-1, "未知"),
	/**
	 * 成功
	 */
    EM_WORKSUIT_APPEND_ERRCODE_SUCCESS(0, "成功"),
	/**
	 * 组ID不存在或为空
	 */
    EM_WORKSUIT_APPEND_ERRCODE_INVALID_ID(1, "组ID不存在或为空"),
	/**
	 * 数据库操作失败
	 */
    EM_WORKSUIT_APPEND_ERRCODE_DB_ERROR(2, "数据库操作失败"),
	/**
	 * 图片大小不对
	 */
    EM_WORKSUIT_APPEND_ERRCODE_IMAGE_SIZE(3, "图片大小不对"),
	/**
	 * 图片分辨率不对
	 */
    EM_WORKSUIT_APPEND_ERRCODE_IMAGE_RESO(4, "图片分辨率不对"),
	/**
	 * 图片文件写入失败
	 */
    EM_WORKSUIT_APPEND_ERRCODE_IMAGE_WRITE(5, "图片文件写入失败");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_WORKSUIT_APPEND_ERRCODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_WORKSUIT_APPEND_ERRCODE enumType : EM_WORKSUIT_APPEND_ERRCODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_WORKSUIT_APPEND_ERRCODE enumType : EM_WORKSUIT_APPEND_ERRCODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}

	public static EM_WORKSUIT_APPEND_ERRCODE getEnum(int value) {
		for (EM_WORKSUIT_APPEND_ERRCODE e : EM_WORKSUIT_APPEND_ERRCODE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_WORKSUIT_APPEND_ERRCODE.EM_WORKSUIT_APPEND_ERRCODE_UNKNOWN;
	}
}

