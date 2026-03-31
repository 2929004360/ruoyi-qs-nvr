package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 260611
 * @description 设备标定信息操作类型
 * @date 2023/05/24 10:24:56
 */
public enum EM_CALIBRATEINFO_OPERATE_TYPE {
	/**
     * 获取指定点标定信息,	pStuInParam = NET_IN_GET_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_GET_CALIBRATEINFO_INFO
     */
    EM_CALIBRATEINFO_OPERATE_GET(0, "获取指定点标定信息,	pStuInParam = NET_IN_GET_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_GET_CALIBRATEINFO_INFO"),
	/**
     * 设置指定点标定信息,	pStuInParam = NET_IN_SET_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_SET_CALIBRATEINFO_INFO
     */
    EM_CALIBRATEINFO_OPERATE_SET(1, "设置指定点标定信息,	pStuInParam = NET_IN_SET_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_SET_CALIBRATEINFO_INFO"),
	/**
     * 删除指定点标定信息,	pStuInParam = NET_IN_DELETE_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_DELETE_CALIBRATEINFO_INFO
     */
    EM_CALIBRATEINFO_OPERATE_DELETE(2, "删除指定点标定信息,	pStuInParam = NET_IN_DELETE_CALIBRATEINFO_INFO,	  pStuOutParam = NET_OUT_DELETE_CALIBRATEINFO_INFO"),
	/**
     * 获取所有标定信息,	pStuInParam = NET_IN_GETALL_CALIBRATEINFO_INFO,   pStuOutParam = NET_OUT_GETALL_CALIBRATEINFO_INFO
     */
    EM_CALIBRATEINFO_OPERATE_GETALL(3, "获取所有标定信息,	pStuInParam = NET_IN_GETALL_CALIBRATEINFO_INFO,   pStuOutParam = NET_OUT_GETALL_CALIBRATEINFO_INFO");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CALIBRATEINFO_OPERATE_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CALIBRATEINFO_OPERATE_TYPE enumType : EM_CALIBRATEINFO_OPERATE_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CALIBRATEINFO_OPERATE_TYPE enumType : EM_CALIBRATEINFO_OPERATE_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}

	public static EM_CALIBRATEINFO_OPERATE_TYPE getEnum(int value) {
		for (EM_CALIBRATEINFO_OPERATE_TYPE e : EM_CALIBRATEINFO_OPERATE_TYPE.values()) {
			if (e.getValue() == value)
				return e;
		}
		return EM_CALIBRATEINFO_OPERATE_TYPE.EM_CALIBRATEINFO_OPERATE_GET;
	}
}

