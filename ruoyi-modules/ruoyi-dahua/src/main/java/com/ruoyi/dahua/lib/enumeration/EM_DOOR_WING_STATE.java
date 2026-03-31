package com.ruoyi.dahua.lib.enumeration;

/**
 * 门翼状态
*/
public enum EM_DOOR_WING_STATE
{
    /**
     * 未知
    */
    EM_DOOR_WING_STATE_UNKNOWN(0, "未知"),
    /**
     * 关门
    */
    EM_DOOR_WING_STATE_CLOSE(1, "关门"),
    /**
     * 进口打开
    */
    EM_DOOR_WING_STATE_ENTER_OPEN(2, "进口打开"),
    /**
     * 出口打开
    */
    EM_DOOR_WING_STATE_LEAVE_OPEN(3, "出口打开");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DOOR_WING_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DOOR_WING_STATE enumType : EM_DOOR_WING_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DOOR_WING_STATE enumType : EM_DOOR_WING_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

