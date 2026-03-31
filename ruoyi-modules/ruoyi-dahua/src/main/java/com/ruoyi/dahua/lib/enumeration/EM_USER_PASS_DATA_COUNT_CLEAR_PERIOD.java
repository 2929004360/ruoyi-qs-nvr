package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @version 1.0
 * @description 定期人数清除周期
 * @date 2022/5/27 9:54
 */
public enum EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD {
    EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD_UNKNOWN(0, "未知"),// 未知
    EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD_HOUR(1, "按小时"),// 按小时
    EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD_DAILY(2, "按天");// 按天, 按照每天00点清除

    private int value;
    private String note;

     EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD(int givenValue, String note) {
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
        for (EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD enumType : EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD enumType : EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD.values()) {
            if (givenNote.equals(enumType.getNote()) ) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD getEnum(int value) {
        for (EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD e : EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD.EM_USER_PASS_DATA_COUNT_CLEAR_PERIOD_UNKNOWN;
    }
}

