package com.ruoyi.dahua.lib.enumeration;

/**
 * 头盔状态
*/
public enum EM_HELMET_STATE
{
    /**
     * 未知
    */
    EM_HELMET_UNKNOWN(0, "未知"),
    /**
     * 未识别
    */
    EM_HELMET_OTHER(1, "未识别"),
    /**
     * 未戴头盔
    */
    EM_HELMET_NO(2, "未戴头盔"),
    /**
     * 戴头盔
    */
    EM_HELMET_YES(3, "戴头盔");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HELMET_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HELMET_STATE enumType : EM_HELMET_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HELMET_STATE enumType : EM_HELMET_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

