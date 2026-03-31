package com.ruoyi.dahua.lib.enumeration;

/**
 * 车门状态
*/
public enum EM_CAR_DOOR_STATE
{
    /**
     * 未知
    */
    EM_CAR_DOOR_STATE_UNKNOWN(0, "未知"),
    /**
     * 开启
    */
    EM_CAR_DOOR_STATE_OPEN(1, "开启"),
    /**
     * 关闭
    */
    EM_CAR_DOOR_STATE_CLOSE(2, "关闭");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CAR_DOOR_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CAR_DOOR_STATE enumType : EM_CAR_DOOR_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CAR_DOOR_STATE enumType : EM_CAR_DOOR_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

