package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 251823
 * @description 视频码流
 * @date 2021/11/07
 */
public enum EM_VIDEO_STREAM {
	// 未知
    EM_VIDEO_STREAM_UNKNOWN(0, "未知"),
	// 主码流
    EM_VIDEO_STREAM_MAIN(1, "主码流"),
	// 辅码流1
    EM_VIDEO_STREAM_EXTRA1(2, "辅码流1"),
	// 辅码流2
    EM_VIDEO_STREAM_EXTRA2(3, "辅码流2"),
	// 辅码流3
    EM_VIDEO_STREAM_EXTRA3(4, "辅码流3"),
	// 自动选择合适码流
    EM_VIDEO_STREAM_AUTO(5, "自动选择合适码流"),
	// 预览裸数据码流
    EM_VIDEO_STREAM_PREVIEW(6, "预览裸数据码流"),
	// 无视频码流(纯音频流)
    EM_VIDEO_STREAM_NO_VIDEO_JUST_AUDIO(7, "无视频码流(纯音频流)");

	private int value;
	private String note;

	private EM_VIDEO_STREAM(int givenValue, String note) {
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
		for (EM_VIDEO_STREAM enumType : EM_VIDEO_STREAM.values()) {
			if (givenValue == enumType.getValue()) {
				return enumType.getNote();
			}
		}
		return null;
	}

	public static int getValueByNote(String givenNote) {
		for (EM_VIDEO_STREAM enumType : EM_VIDEO_STREAM.values()) {
			if (givenNote.equals(enumType.getNote())) {
				return enumType.getValue();
			}
		}
		return -1;
	}
}

