package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 灯光模式
 * @date 2023/05/23 09:40:10
 */
public enum EM_CFG_LC_MODE {
	/**
	 * 未知
	 */
    EM_CFG_LC_MODE_UNKNOWN(0, "未知"),
	/**
	 * 手动
	 */
    EM_CFG_LC_MODE_MANUAL(1, "手动"),
	/**
	 * 倍率优先
	 */
    EM_CFG_LC_MODE_ZOOMPRIO(2, "倍率优先"),
	/**
	 * 定时(废弃)
	 */
    EM_CFG_LC_MODE_TIMING(3, "定时(废弃)"),
	/**
	 * 自动
	 */
    EM_CFG_LC_MODE_AUTO(4, "自动"),
	/**
	 * 关闭灯光
	 */
    EM_CFG_LC_MODE_OFF(5, "关闭灯光"),
	/**
	 * 支持多种灯光(废弃)
	 */
    EM_CFG_LC_MODE_EXCLUSIVEMANUAL(6, "支持多种灯光(废弃)"),
	/**
	 * 智能灯光(废弃)
	 */
    EM_CFG_LC_MODE_SMARTLIGHT(7, "智能灯光(废弃)"),
	/**
	 * 事件联动(废弃)
	 */
    EM_CFG_LC_MODE_LINKING(8, "事件联动(废弃)"),
	/**
	 * 光敏
	 */
    EM_CFG_LC_MODE_DUSKTODAWN(9, "光敏"),
	/**
	 * 强制打开灯光
	 */
    EM_CFG_LC_MODE_FORCEON(10, "强制打开灯光");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_LC_MODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_LC_MODE enumType : EM_CFG_LC_MODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_LC_MODE enumType : EM_CFG_LC_MODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

