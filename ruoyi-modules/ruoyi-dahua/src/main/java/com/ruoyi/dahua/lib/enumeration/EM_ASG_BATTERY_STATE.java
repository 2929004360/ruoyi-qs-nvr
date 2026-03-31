package com.ruoyi.dahua.lib.enumeration;

/**
 * 闸机电池状态
*/
public enum EM_ASG_BATTERY_STATE
{
    /**
     * 未知
    */
    EM_ASG_BATTERY_STATE_UNKNOWN(0, "未知"),
    /**
     * 不在位
    */
    EM_ASG_BATTERY_STATE_NOT_IN_PLACE(1, "不在位"),
    /**
     * 在位
    */
    EM_ASG_BATTERY_STATE_IN_PLACE(2, "在位");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ASG_BATTERY_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ASG_BATTERY_STATE enumType : EM_ASG_BATTERY_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ASG_BATTERY_STATE enumType : EM_ASG_BATTERY_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

