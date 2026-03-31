package com.ruoyi.dahua.lib.enumeration;

/**
 * APP的调试状态
*/
public enum EM_APP_DEBUG_STATE
{
    /**
     * 未知
    */
    EM_APP_DEBUG_STATE_UNKNOWN(0, "未知"),
    /**
     * 开启调试
    */
    EM_APP_DEBUG_STATE_ENABLE(1, "开启调试"),
    /**
     * 关闭调试
    */
    EM_APP_DEBUG_STATE_DISABLE(2, "关闭调试");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_APP_DEBUG_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_APP_DEBUG_STATE enumType : EM_APP_DEBUG_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_APP_DEBUG_STATE enumType : EM_APP_DEBUG_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

