package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:22
 * 安全门人员报警级别
 */
public enum EM_SECURITYGATE_ALARM_LEVEL {
    EM_SECURITYGATE_ALARM_LEVEL_UNKNOWN(0, "未知"),// 未知
    EM_SECURITYGATE_ALARM_LEVEL_NON(1, "无报警"),// 无报警
    EM_SECURITYGATE_ALARM_LEVEL_LOW(2, "低危报警级别"),// 低危报警级别
    EM_SECURITYGATE_ALARM_LEVEL_MIDDLE(3, "中危报警级别"),// 中危报警级别
    EM_SECURITYGATE_ALARM_LEVEL_HIGH(4, "高危报警级别");// 高危报警级别

    private final int value;
    private final String note;

    EM_SECURITYGATE_ALARM_LEVEL(int givenValue, String note) {
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
        for (EM_SECURITYGATE_ALARM_LEVEL enumType : EM_SECURITYGATE_ALARM_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_ALARM_LEVEL enumType : EM_SECURITYGATE_ALARM_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_ALARM_LEVEL getEnum(int value) {
        for (EM_SECURITYGATE_ALARM_LEVEL e : EM_SECURITYGATE_ALARM_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_ALARM_LEVEL.EM_SECURITYGATE_ALARM_LEVEL_UNKNOWN;
    }
}

