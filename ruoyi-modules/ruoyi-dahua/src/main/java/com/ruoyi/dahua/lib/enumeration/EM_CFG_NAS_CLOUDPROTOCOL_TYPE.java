package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 云存储协议类型
 * @date 2022/09/08 19:33:10
 */
public enum EM_CFG_NAS_CLOUDPROTOCOL_TYPE {
	/**
	 * 未知
	 */
    EM_CFG_NAS_CLOUDPROTOCOL_TYPE_UNKNOWN(0, "未知"),
	/**
	 * BaiduCloud
	 */
    EM_CFG_NAS_CLOUDPROTOCOL_TYPE_BAIDU_CLOUD(1, " BaiduCloud "),
	/**
	 * GoogleDrive
	 */
    EM_CFG_NAS_CLOUDPROTOCOL_TYPE_GOOGLE_DRIVE(2, " GoogleDrive "),
	/**
	 * Dropbox
	 */
    EM_CFG_NAS_CLOUDPROTOCOL_TYPE_DROPBOX(3, " Dropbox ");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_CFG_NAS_CLOUDPROTOCOL_TYPE(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_CFG_NAS_CLOUDPROTOCOL_TYPE enumType : EM_CFG_NAS_CLOUDPROTOCOL_TYPE.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_CFG_NAS_CLOUDPROTOCOL_TYPE enumType : EM_CFG_NAS_CLOUDPROTOCOL_TYPE.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

