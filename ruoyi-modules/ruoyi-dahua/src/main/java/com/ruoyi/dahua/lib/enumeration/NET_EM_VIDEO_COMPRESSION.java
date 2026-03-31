package com.ruoyi.dahua.lib.enumeration;

/**
 * 视频压缩格式
 *
 * @author 47040
 * @since Created at 2021/5/26 22:16
 */
public enum NET_EM_VIDEO_COMPRESSION {
    EM_VIDEO_FORMAT_UNKNOWN(-1, "未知"),
    EM_VIDEO_FORMAT_MPEG4(0, "MPEG4"),
    EM_VIDEO_FORMAT_MS_MPEG4(1, "MS-MPEG4"),
    EM_VIDEO_FORMAT_MPEG2(2, "MPEG2"),
    EM_VIDEO_FORMAT_MPEG1(3, "MPEG1"),
    EM_VIDEO_FORMAT_H263(4, "H.263"),
    EM_VIDEO_FORMAT_MJPG(5, "MJPG"),
    EM_VIDEO_FORMAT_FCC_MPEG4(6, "FCC-MPEG4"),
    EM_VIDEO_FORMAT_H264(7, "H.264"),
    EM_VIDEO_FORMAT_H265(8, "H.265"),
    EM_VIDEO_FORMAT_SVAC(9, "SVAC");

    private final int value;
    private final String note;

    NET_EM_VIDEO_COMPRESSION(int givenValue, String note) {
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
        for (NET_EM_VIDEO_COMPRESSION enumType : NET_EM_VIDEO_COMPRESSION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_VIDEO_COMPRESSION enumType : NET_EM_VIDEO_COMPRESSION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_VIDEO_COMPRESSION getEnum(int value) {
        for (NET_EM_VIDEO_COMPRESSION e : NET_EM_VIDEO_COMPRESSION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_VIDEO_COMPRESSION.EM_VIDEO_FORMAT_UNKNOWN;
    }
}

