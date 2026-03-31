package com.ruoyi.dahua.lib.enumeration;

/**
 * APP的运行状态
*/
public enum EM_APP_RUNNING_STATE
{
    /**
     * 未知
    */
    EM_APP_RUNNING_STATE_UNKNOWN(0, "未知"),
    /**
     * 运行中
    */
    EM_APP_RUNNING_STATE_RUNNING(1, "运行中"),
    /**
     * 已停止
    */
    EM_APP_RUNNING_STATE_STOP(2, "已停止"),
    /**
     * 异常状态
    */
    EM_APP_RUNNING_STATE_ERROR(3, "异常状态");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_APP_RUNNING_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_APP_RUNNING_STATE enumType : EM_APP_RUNNING_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_APP_RUNNING_STATE enumType : EM_APP_RUNNING_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

