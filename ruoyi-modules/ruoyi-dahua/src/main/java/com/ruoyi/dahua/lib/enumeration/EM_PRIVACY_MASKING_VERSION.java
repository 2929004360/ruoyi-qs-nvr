package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 隐私遮档版本号
 * @date 2022/07/21 13:40:56
 */
public enum EM_PRIVACY_MASKING_VERSION {
	/**
	 * 未知类型
	 */
    EM_PRIVACY_MASKING_UNKNOWN(0, "未知类型"),
	/**
	 * V1:第一版本隐私遮档算法（原始隐私遮档算法，使用配置）
	 */
    EM_PRIVACY_MASKING_V1(1, "V1:第一版本隐私遮档算法（原始隐私遮档算法，使用配置）"),
	/**
	 * V2:第二版本的隐私遮档算法(基于视场角的新算法，使用接口)
	 */
    EM_PRIVACY_MASKING_V2(2, "V2:第二版本的隐私遮档算法(基于视场角的新算法，使用接口)"),
	/**
	 * V3:球机和IPC协议统一的版本
	 */
    EM_PRIVACY_MASKING_V3(3, "V3:球机和IPC协议统一的版本");

	private int value;
	private String note;

	public String getNote() {
		return note;
	}

	public int getValue() {
		return value;
	}

	EM_PRIVACY_MASKING_VERSION(int givenValue, String note) {
		this.value = givenValue;
		this.note = note;
	}

	public static String getNoteByValue(int givenValue) {
		for (EM_PRIVACY_MASKING_VERSION enumType : EM_PRIVACY_MASKING_VERSION.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_PRIVACY_MASKING_VERSION enumType : EM_PRIVACY_MASKING_VERSION.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

