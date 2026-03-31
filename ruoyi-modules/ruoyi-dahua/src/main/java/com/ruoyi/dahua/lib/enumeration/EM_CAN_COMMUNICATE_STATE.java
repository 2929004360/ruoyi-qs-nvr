package com.ruoyi.dahua.lib.enumeration;

/**
 * Can通信状态
*/
public enum EM_CAN_COMMUNICATE_STATE
{
    /**
     * 未知
    */
    EM_CAN_COMMUNICATE_STATE_UNKNOWN(-1, "未知"),
    /**
     * 正常
    */
    EM_CAN_COMMUNICATE_STATE_NORMAL(0, "正常"),
    /**
     * 异常
    */
    EM_CAN_COMMUNICATE_STATE_ABNORMAL(1, "异常");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CAN_COMMUNICATE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CAN_COMMUNICATE_STATE enumType : EM_CAN_COMMUNICATE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CAN_COMMUNICATE_STATE enumType : EM_CAN_COMMUNICATE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

