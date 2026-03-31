package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 红绿灯方案/可变车道方案
 * @date 2021/09/28
 */
public enum EM_CTRL_SCHEME {
	// 未知
    EM_CTRL_SCHEME_UNKNOWN(0, "未知"),
	// 进入备份模式(红绿灯)
    EM_CTRL_SCHEME_REDYELLOW_BACKUP(150, "进入备份模式(红绿灯)"),
	// 正常退出备份模式(红绿灯)
    EM_CTRL_SCHEME_REDYELLOW_BACKUP_NORMAL_QUIT(151, "正常退出备份模式(红绿灯)"),
	// 异常退出备份模式(红绿灯)
    EM_CTRL_SCHEME_REDYELLOW_BACKUP_ABNORMAL_QUIT(152, "异常退出备份模式(红绿灯)"),
	// 异常退出备份模式(可变车道)
    EM_CTRL_SCHEME_VARLANE_BACKUP(153, "异常退出备份模式(可变车道)"),
	// 正常退出备份模式(可变车道)
    EM_CTRL_SCHEME_VARLANE_BACKUP_NORMAL_QUIT(154, "正常退出备份模式(可变车道)"),
	// 异常退出备份模式(可变车道)
    EM_CTRL_SCHEME_VARLANE_BACKUP_ABNORMAL_QUIT(155, "异常退出备份模式(可变车道)");

	private int value;
	private String note;

	private EM_CTRL_SCHEME(int givenValue, String note) {
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
		for (EM_CTRL_SCHEME enumType : EM_CTRL_SCHEME.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CTRL_SCHEME enumType : EM_CTRL_SCHEME.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

