package com.ruoyi.dahua.lib.enumeration;

/**
 * 抓图类型
 *
 * @author 47040
 * @since Created at 2021/5/25 23:07
 */
public enum SNAP_TYPE {
    SNAP_TYP_TIMING(0, "定时"),
    SNAP_TYP_ALARM(1, "报警"),
    SNAP_TYP_NUM(2, "数量");

    private final int value;
    private final String note;

    SNAP_TYPE(int givenValue, String note) {
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
        for (SNAP_TYPE enumType : SNAP_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (SNAP_TYPE enumType : SNAP_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static SNAP_TYPE getEnum(int value) {
        for (SNAP_TYPE e : SNAP_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return SNAP_TYPE.SNAP_TYP_TIMING;
    }
}

