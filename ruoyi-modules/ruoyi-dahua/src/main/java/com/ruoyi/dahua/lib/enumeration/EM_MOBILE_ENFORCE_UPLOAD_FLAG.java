package com.ruoyi.dahua.lib.enumeration;

/**
 * 文件上传标志
 *
 * @author 47040
 * @since Created at 2021/5/31 10:16
 */
public enum EM_MOBILE_ENFORCE_UPLOAD_FLAG {
    EM_MOBILE_ENFORCE_UPLOAD_FLAG_UNKNWON(-1, "未知"),
    EM_MOBILE_ENFORCE_UPLOAD_FLAG_NOTUPLOAD(0, "未上传"),
    EM_MOBILE_ENFORCE_UPLOAD_FLAG_UPLOADED(1, "已上传"),
    EM_MOBILE_ENFORCE_UPLOAD_FLAG_BACKEDUP(3, "已备份");

    private final int value;
    private final String note;

    EM_MOBILE_ENFORCE_UPLOAD_FLAG(int givenValue, String note) {
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
        for (EM_MOBILE_ENFORCE_UPLOAD_FLAG enumType : EM_MOBILE_ENFORCE_UPLOAD_FLAG.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MOBILE_ENFORCE_UPLOAD_FLAG enumType : EM_MOBILE_ENFORCE_UPLOAD_FLAG.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_MOBILE_ENFORCE_UPLOAD_FLAG getEnum(int value) {
        for (EM_MOBILE_ENFORCE_UPLOAD_FLAG e : EM_MOBILE_ENFORCE_UPLOAD_FLAG.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_MOBILE_ENFORCE_UPLOAD_FLAG.EM_MOBILE_ENFORCE_UPLOAD_FLAG_UNKNWON;
    }
}

