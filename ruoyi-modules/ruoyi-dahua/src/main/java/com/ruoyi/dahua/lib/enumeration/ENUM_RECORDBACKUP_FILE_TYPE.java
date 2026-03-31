package com.ruoyi.dahua.lib.enumeration;

/**
 * 远程录像备份录像类型
 *
 * @author 47040
 * @since Created at 2021/5/27 20:25
 */
public enum ENUM_RECORDBACKUP_FILE_TYPE {
    ENUM_RECORDBACKUP_FILE_COMMON(0, "普通录像"),
    ENUM_RECORDBACKUP_FILE_ALARM(1, "报警录像"),
    ENUM_RECORDBACKUP_FILE_MOTIONDETECT(2, "动检录像");

    private final int value;
    private final String note;

    ENUM_RECORDBACKUP_FILE_TYPE(int givenValue, String note) {
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
        for (ENUM_RECORDBACKUP_FILE_TYPE enumType : ENUM_RECORDBACKUP_FILE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (ENUM_RECORDBACKUP_FILE_TYPE enumType : ENUM_RECORDBACKUP_FILE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static ENUM_RECORDBACKUP_FILE_TYPE getEnum(int value) {
        for (ENUM_RECORDBACKUP_FILE_TYPE e : ENUM_RECORDBACKUP_FILE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return ENUM_RECORDBACKUP_FILE_TYPE.ENUM_RECORDBACKUP_FILE_COMMON;
    }
}

