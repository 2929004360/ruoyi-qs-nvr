package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 防区类型
 * @date 2023/04/18 10:57:31
 */
public enum EM_CFG_DEFENCEAREATYPE {
	/**
	 * 未知类型
	 */
    EM_CFG_DefenceAreaType_Unknown(0, "未知类型"),
	/**
	 * 即时防区
	 */
    EM_CFG_DefenceAreaType_InTime(1, "即时防区"),
	/**
	 * 延时防区
	 */
    EM_CFG_DefenceAreaType_Delay(2, "延时防区"),
	/**
	 * 24小时防区
	 */
    EM_CFG_DefenceAreaType_FullDay(3, "24小时防区"),
	/**
	 * 跟随防区
	 */
    EM_CFG_DefenceAreaType_Follow(4, "跟随防区"),
	/**
	 * 医疗紧急防区
	 */
    EM_CFG_DefenceAreaType_Medical(5, "医疗紧急防区"),
	/**
	 * 恐慌防区
	 */
    EM_CFG_DefenceAreaType_Panic(6, "恐慌防区"),
	/**
	 * 火警防区
	 */
    EM_CFG_DefenceAreaType_Fire(7, "火警防区"),
	/**
	 * 24小时有声防区
	 */
    EM_CFG_DefenceAreaType_FullDaySound(8, "24小时有声防区"),
	/**
	 * 24小时无声防区
	 */
    EM_CFG_DefenceAreaType_FullDaySlient(9, "24小时无声防区"),
	/**
	 * 出入防区1
	 */
    EM_CFG_DefenceAreaType_Entrance1(10, "出入防区1"),
	/**
	 * 出入防区2
	 */
    EM_CFG_DefenceAreaType_Entrance2(11, "出入防区2"),
	/**
	 * 内部防区
	 */
    EM_CFG_DefenceAreaType_InSide(12, "内部防区"),
	/**
	 * 外部防区
	 */
    EM_CFG_DefenceAreaType_OutSide(13, "外部防区"),
	/**
	 * 人员检测防区
	 */
    EM_CFG_DefenceAreaType_PeopleDetect(14, "人员检测防区"),
	/**
	 * 匪警防区
	 */
    EM_CFG_DefenceAreaType_Robbery(15, "匪警防区");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_DEFENCEAREATYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_DEFENCEAREATYPE enumType : EM_CFG_DEFENCEAREATYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_DEFENCEAREATYPE enumType : EM_CFG_DEFENCEAREATYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

