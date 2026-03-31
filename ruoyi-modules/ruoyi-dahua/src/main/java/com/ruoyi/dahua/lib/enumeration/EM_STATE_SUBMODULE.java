package com.ruoyi.dahua.lib.enumeration;

/**
 * 模块状态
*/
public enum EM_STATE_SUBMODULE
{
    EM_STATE_SUBMODULES_UNKNOWN(0, ""),
    /**
     * 正常
    */
    EM_STATE_SUBMODULES_NORMAL(1, "正常"),
    /**
     * 异常
    */
    EM_STATE_SUBMODULES_EXCEPTION(2, "异常");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STATE_SUBMODULE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STATE_SUBMODULE enumType : EM_STATE_SUBMODULE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STATE_SUBMODULE enumType : EM_STATE_SUBMODULE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

