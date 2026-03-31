package com.ruoyi.dahua.lib.enumeration;

/**
 * 物体进入还是离开
*/
public enum EM_CAR_PASSING_MOVE_STATE
{
    /**
     * 未知
    */
    EM_CAR_PASSING_MOVE_STATE_UNKNOWN(0, "未知"),
    /**
     * 进入
    */
    EM_CAR_PASSING_MOVE_STATE_ENTER(1, "进入"),
    /**
     * 离开
    */
    EM_CAR_PASSING_MOVE_STATE_LEAVE(2, "离开");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CAR_PASSING_MOVE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CAR_PASSING_MOVE_STATE enumType : EM_CAR_PASSING_MOVE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CAR_PASSING_MOVE_STATE enumType : EM_CAR_PASSING_MOVE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

