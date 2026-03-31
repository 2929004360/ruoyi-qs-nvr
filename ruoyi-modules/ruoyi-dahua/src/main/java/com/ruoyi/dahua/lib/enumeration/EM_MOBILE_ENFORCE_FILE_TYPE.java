package com.ruoyi.dahua.lib.enumeration;

/**
 * 文件后缀类型
 *
 * @author 47040
 * @since Created at 2021/5/31 14:34
 */
public enum EM_MOBILE_ENFORCE_FILE_TYPE {
    EM_MOBILE_ENFORCE_FILE_TYPE_UNKNOWN(0, "未知"),
    EM_MOBILE_ENFORCE_FILE_TYPE_DAV(1, "dav"),
    EM_MOBILE_ENFORCE_FILE_TYPE_MP4(2, "mp4"),
    EM_MOBILE_ENFORCE_FILE_TYPE_AAC(3, "aac"),
    EM_MOBILE_ENFORCE_FILE_TYPE_MP3(4, "mp3"),
    EM_MOBILE_ENFORCE_FILE_TYPE_JPG(5, "jpg");

    private final int value;
    private final String note;

    EM_MOBILE_ENFORCE_FILE_TYPE(int givenValue, String note) {
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
        for (EM_MOBILE_ENFORCE_FILE_TYPE enumType : EM_MOBILE_ENFORCE_FILE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MOBILE_ENFORCE_FILE_TYPE enumType : EM_MOBILE_ENFORCE_FILE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_MOBILE_ENFORCE_FILE_TYPE getEnum(int value) {
        for (EM_MOBILE_ENFORCE_FILE_TYPE e : EM_MOBILE_ENFORCE_FILE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_MOBILE_ENFORCE_FILE_TYPE.EM_MOBILE_ENFORCE_FILE_TYPE_UNKNOWN;
    }
}

