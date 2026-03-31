package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 47040
 * @since Created at 2021/5/26 20:44
 */
public enum CFG_VIDEO_COMPRESSION {
    VIDEO_FORMAT_MPEG4(0, "MPEG4"),
    VIDEO_FORMAT_MS_MPEG4(1, "MS-MPEG4"),
    VIDEO_FORMAT_MPEG2(2, "MPEG2"),
    VIDEO_FORMAT_MPEG1(3, "MPEG1"),
    VIDEO_FORMAT_H263(4, "H.263"),
    VIDEO_FORMAT_MJPG(5, "MJPG"),
    VIDEO_FORMAT_FCC_MPEG4(6, "FCC-MPEG4"),
    VIDEO_FORMAT_H264(7, "H.264"),
    VIDEO_FORMAT_H265(8, "H.265"),
    VIDEO_FORMAT_SVAC(9, "SVAC");

    private final int value;
    private final String note;

    CFG_VIDEO_COMPRESSION(int givenValue, String note) {
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
        for (CFG_VIDEO_COMPRESSION enumType : CFG_VIDEO_COMPRESSION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (CFG_VIDEO_COMPRESSION enumType : CFG_VIDEO_COMPRESSION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static CFG_VIDEO_COMPRESSION getEnum(int value) {
        for (CFG_VIDEO_COMPRESSION e : CFG_VIDEO_COMPRESSION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return CFG_VIDEO_COMPRESSION.VIDEO_FORMAT_H264;
    }
}

