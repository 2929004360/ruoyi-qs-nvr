package com.ruoyi.dahua.lib.enumeration;

/**
 * 智能动检敏感等级
*/
public enum EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL
{
    /**
     * 未知
    */
    EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL_UNKNOWN(0, "未知"),
    /**
     * 低
    */
    EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL_LOW(1, "低"),
    /**
     * 中
    */
    EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL_MIDDLE(2, "中"),
    /**
     * 高
    */
    EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL_HIGH(3, "高");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL enumType : EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL enumType : EM_SMART_MOTION_DETECT_SENSITIVITY_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

