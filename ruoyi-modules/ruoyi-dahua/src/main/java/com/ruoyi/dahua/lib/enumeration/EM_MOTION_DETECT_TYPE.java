package com.ruoyi.dahua.lib.enumeration;

/**
 * 动检触发类型
*/
public enum EM_MOTION_DETECT_TYPE
{
    /**
     * 未知
    */
    EM_MOTION_DETECT_UNKNOWN(0, "未知"),
    /**
     * 人
    */
    EM_MOTION_DETECT_HUMAN(1, "人"),
    /**
     * 车
    */
    EM_MOTION_DETECT_VEHICLE(2, "车"),
    /**
     * 人和车
    */
    EM_MOTION_DETECT_HUMAN_AND_VEHICLE(3, "人和车");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MOTION_DETECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MOTION_DETECT_TYPE enumType : EM_MOTION_DETECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MOTION_DETECT_TYPE enumType : EM_MOTION_DETECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

