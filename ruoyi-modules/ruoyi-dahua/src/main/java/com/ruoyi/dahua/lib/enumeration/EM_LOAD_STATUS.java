package com.ruoyi.dahua.lib.enumeration;

/**
 * 载重状态
*/
public enum EM_LOAD_STATUS
{
    /**
     * 未知
    */
    EM_LOAD_STATUS_UNKNOWN(0, "未知"),
    /**
     * 空车
    */
    EM_LOAD_STATUS_NOT_LOAD(1, "空车"),
    /**
     * 载重
    */
    EM_LOAD_STATUS_LOAD(2, "载重");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LOAD_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LOAD_STATUS enumType : EM_LOAD_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LOAD_STATUS enumType : EM_LOAD_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

