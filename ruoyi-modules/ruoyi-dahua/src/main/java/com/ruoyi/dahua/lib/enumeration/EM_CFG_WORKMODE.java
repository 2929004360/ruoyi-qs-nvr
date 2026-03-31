package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 工作模式选择
 * @date 2023/03/16 14:23:46
 */
public enum EM_CFG_WORKMODE {
	/**
	
	*/
    EM_WORKMODE_UNKNOWN(-1, ""),
	/**
	 * CDMA1x
	 */
    EM_WORKMODE_CDMA1X(0, " CDMA1x "),
	/**
	 * EVDO
	 */
    EM_WORKMODE_EVDO(1, " EVDO "),
	/**
	 * TD-SCDMA
	 */
    EM_WORKMODE_TDSCDMA(2, " TD-SCDMA "),
	/**
	 * WCDMA
	 */
    EM_WORKMODE_WCDMA(3, " WCDMA "),
	/**
	 * EDGE
	 */
    EM_WORKMODE_EDGE(4, " EDGE "),
	/**
	 * TDD-LTE
	 */
    EM_WORKMODE_TDDLTE(5, " TDD-LTE "),
	/**
	 * FDD-LTE
	 */
    EM_WORKMODE_FDDLTE(6, " FDD-LTE "),
	/**
	 * TD-LTE
	 */
    EM_WORKMODE_TDLTE(7, " TD-LTE "),
	/**
	 * Auto
	 */
    EM_WORKMODE_AUTO(8, " Auto ");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_WORKMODE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_WORKMODE enumType : EM_CFG_WORKMODE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_WORKMODE enumType : EM_CFG_WORKMODE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

