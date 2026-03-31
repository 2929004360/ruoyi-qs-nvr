package com.ruoyi.dahua.lib.enumeration;

/**
 * 比对报警规则
*/
public enum EM_ALARM_RULE_TYPE
{
    /**
     * 未知
    */
    EM_ALARM_RULE_UNKNOWN(-1, "未知"),
    /**
     * 不报警
    */
    EM_ALARM_RULE_NONE(0, "不报警"),
    /**
     * 比对成功报警
    */
    EM_ALARM_RULE_SUCCESS(1, "比对成功报警"),
    /**
     * 比对失败报警
    */
    EM_ALARM_RULE_FAIL(2, "比对失败报警");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ALARM_RULE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ALARM_RULE_TYPE enumType : EM_ALARM_RULE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ALARM_RULE_TYPE enumType : EM_ALARM_RULE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

