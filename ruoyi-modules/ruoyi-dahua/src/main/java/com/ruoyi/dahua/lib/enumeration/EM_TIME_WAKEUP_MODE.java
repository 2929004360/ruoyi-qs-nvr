package com.ruoyi.dahua.lib.enumeration;

/**
 * 定时唤醒的方式
*/
public enum EM_TIME_WAKEUP_MODE
{
    /**
     * 未知
    */
    NET_EM_TIME_WAKEUP_UNKNOWN(0, "未知"),
    /**
     * 按时间段唤醒
    */
    NET_EM_TIME_WAKEUP_SEACTION(1, "按时间段唤醒"),
    /**
     * 按时间间隔唤醒
    */
    NET_EM_TIME_WAKEUP_INTERVAL(2, "按时间间隔唤醒"),
    /**
     * 按照时间点唤醒
    */
    NET_EM_TIME_WAKEUP_POINT(3, "按照时间点唤醒");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_TIME_WAKEUP_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TIME_WAKEUP_MODE enumType : EM_TIME_WAKEUP_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TIME_WAKEUP_MODE enumType : EM_TIME_WAKEUP_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

