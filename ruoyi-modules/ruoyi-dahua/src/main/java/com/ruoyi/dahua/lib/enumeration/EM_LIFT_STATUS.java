package com.ruoyi.dahua.lib.enumeration;

/**
 * 举升状态
*/
public enum EM_LIFT_STATUS
{
    /**
     * 未知
    */
    EM_LIFT_STATUS_UNKNOWN(0, "未知"),
    /**
     * 未举升
    */
    EM_LIFT_STATUS_NOT_LIFT(1, "未举升"),
    /**
     * 举升
    */
    EM_LIFT_STATUS_LIFT(2, "举升");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LIFT_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LIFT_STATUS enumType : EM_LIFT_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LIFT_STATUS enumType : EM_LIFT_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

