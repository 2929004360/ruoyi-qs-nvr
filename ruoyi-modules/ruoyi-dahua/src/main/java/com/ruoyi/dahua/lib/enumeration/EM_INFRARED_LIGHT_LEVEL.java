package com.ruoyi.dahua.lib.enumeration;

/**
 * 红外亮度
 */
public enum EM_INFRARED_LIGHT_LEVEL {
    EM_INFRARED_LIGHT_LEVEL_UNKNOWN(0, "未知"),
    EM_INFRARED_LIGHT_LEVEL_HIGH(1, "高"),
    EM_INFRARED_LIGHT_LEVEL_MEDIUM(2, "中"),
    EM_INFRARED_LIGHT_LEVEL_LOW(3, "低");

    private final int value;
    private final String note;

    EM_INFRARED_LIGHT_LEVEL(int givenValue, String note) {
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
        for (EM_INFRARED_LIGHT_LEVEL enumType : EM_INFRARED_LIGHT_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_INFRARED_LIGHT_LEVEL enumType : EM_INFRARED_LIGHT_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_INFRARED_LIGHT_LEVEL getEnum(int value) {
        for (EM_INFRARED_LIGHT_LEVEL e : EM_INFRARED_LIGHT_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_INFRARED_LIGHT_LEVEL.EM_INFRARED_LIGHT_LEVEL_UNKNOWN;
    }
}

