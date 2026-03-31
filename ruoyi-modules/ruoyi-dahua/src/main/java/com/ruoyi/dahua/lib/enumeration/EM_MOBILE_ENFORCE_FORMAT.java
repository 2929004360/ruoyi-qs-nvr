package com.ruoyi.dahua.lib.enumeration;

/**
 * 文件的真实类型
 *
 * @author 47040
 * @since Created at 2021/5/31 12:11
 */
public enum EM_MOBILE_ENFORCE_FORMAT {
    EM_MOBILE_ENFORCE_FORMAT_UNKNOWN(0, "未知"),
    EM_MOBILE_ENFORCE_FORMAT_VIDEO(1, "视频"),
    EM_MOBILE_ENFORCE_FORMAT_AUDIO(2, "音频"),
    EM_MOBILE_ENFORCE_FORMAT_IMAGE(3, "图片");

    private final int value;
    private final String note;

    EM_MOBILE_ENFORCE_FORMAT(int givenValue, String note) {
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
        for (EM_MOBILE_ENFORCE_FORMAT enumType : EM_MOBILE_ENFORCE_FORMAT.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MOBILE_ENFORCE_FORMAT enumType : EM_MOBILE_ENFORCE_FORMAT.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_MOBILE_ENFORCE_FORMAT getEnum(int value) {
        for (EM_MOBILE_ENFORCE_FORMAT e : EM_MOBILE_ENFORCE_FORMAT.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_MOBILE_ENFORCE_FORMAT.EM_MOBILE_ENFORCE_FORMAT_UNKNOWN;
    }
}

