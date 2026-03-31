package com.ruoyi.dahua.lib.enumeration;

/**
 * 录象类型(定时、动态检测、报警)
 *
 * @author 47040
 * @since Created at 2021/5/25 23:00
 */
public enum REC_TYPE {
    NET_REC_TYPE_TIM(0, "定时"),
    NET_REC_TYPE_MTD(1, "动态"),
    NET_REC_TYPE_ALM(2, "报警"),
    NET_REC_TYPE_NUM(3, "数量");

    private final int value;
    private final String note;

    REC_TYPE(int givenValue, String note) {
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
        for (REC_TYPE enumType : REC_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (REC_TYPE enumType : REC_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static REC_TYPE getEnum(int value) {
        for (REC_TYPE e : REC_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return REC_TYPE.NET_REC_TYPE_TIM;
    }
}

