package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 音频数据来源
 * @date 2021/12/29
 */
public enum EM_AUDIO_SOURCE_FLAG {
	// 未知状态
    EM_AUDIO_SOURCE_FLAG_UNKNOWN(-1, "未知状态"),
	// 本地录音数据，可以发送给设备
    EM_AUDIO_SOURCE_FLAG_LOCAL(0, "本地录音数据，可以发送给设备"),
	// 远程音频数据，即从设备接收到音频数据
    EM_AUDIO_SOURCE_FLAG_REMOTE(1, "远程音频数据，即从设备接收到音频数据");

	private int value;
	private String note;

	private EM_AUDIO_SOURCE_FLAG(int givenValue, String note) {
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
		for (EM_AUDIO_SOURCE_FLAG enumType : EM_AUDIO_SOURCE_FLAG.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_AUDIO_SOURCE_FLAG enumType : EM_AUDIO_SOURCE_FLAG.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -2;
	}
}

