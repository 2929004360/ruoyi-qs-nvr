package com.ruoyi.dahua.lib.enumeration;

/**
 * 主动巡视类型
*/
public enum EM_PATROL_TYPE
{
    /**
     * 未知
    */
    EM_PATROL_TYPE_UNKNOWN(0, "未知"),
    /**
     * 预置点主动巡航
    */
    EM_PATROL_TYPE_PRESET(1, "预置点主动巡航");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PATROL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PATROL_TYPE enumType : EM_PATROL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PATROL_TYPE enumType : EM_PATROL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

