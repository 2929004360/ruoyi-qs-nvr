package com.ruoyi.dahua.lib.enumeration;

/**
 * EM_SPACE_STATE 车位状态
*/
public enum EM_SPACE_STATE
{
    /**
     * "", 未知
    */
    EM_SPACE_STATE_UNKOWN(0, "\"\", 未知"),
    /**
     * "SpaceOverLine", 车位压线
    */
    EM_SPACE_STATE_SPACE_OVER_LINE(1, "\"SpaceOverLine\", 车位压线"),
    /**
     * "SpaceFree", 车位空闲
    */
    EM_SPACE_STATE_SPACE_FREE(2, "\"SpaceFree\", 车位空闲"),
    /**
     * "SpaceFull", 车位有车
    */
    EM_SPACE_STATE_SPACE_FULL(3, "\"SpaceFull\", 车位有车"),
    /**
     * "SpaceAlarm", 车位报警
    */
    EM_SPACE_STATE_SPACE_ALARM(4, "\"SpaceAlarm\", 车位报警");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SPACE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SPACE_STATE enumType : EM_SPACE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SPACE_STATE enumType : EM_SPACE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

