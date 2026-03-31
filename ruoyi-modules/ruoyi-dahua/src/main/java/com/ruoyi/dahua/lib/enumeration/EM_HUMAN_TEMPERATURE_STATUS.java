package com.ruoyi.dahua.lib.enumeration;

/**
 * 人体测温状态
*/
public enum EM_HUMAN_TEMPERATURE_STATUS
{
    /**
     * 未知
    */
    EM_HUMAN_TEMPERATURE_STATUS_UNKNOWN(-1, "未知"),
    /**
     * 温度异常
    */
    EM_HUMAN_TEMPERATURE_STATUS_LOW(0, "温度异常"),
    /**
     * 正常
    */
    EM_HUMAN_TEMPERATURE_STATUS_NORMAL(1, "正常"),
    /**
     * 温度异常
    */
    EM_HUMAN_TEMPERATURE_STATUS_HIGH(2, "温度异常");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HUMAN_TEMPERATURE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HUMAN_TEMPERATURE_STATUS enumType : EM_HUMAN_TEMPERATURE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HUMAN_TEMPERATURE_STATUS enumType : EM_HUMAN_TEMPERATURE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

