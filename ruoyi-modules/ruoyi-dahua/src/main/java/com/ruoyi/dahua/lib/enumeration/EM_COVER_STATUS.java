package com.ruoyi.dahua.lib.enumeration;

/**
 * 箱盖状态
*/
public enum EM_COVER_STATUS
{
    /**
     * 未知
    */
    EM_COVER_STATUS_UNKNOWN(0, "未知"),
    /**
     * 未密闭
    */
    EM_COVER_STATUS_NOT_COVER(1, "未密闭"),
    /**
     * 密闭
    */
    EM_COVER_STATUS_COVER(2, "密闭");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_COVER_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_COVER_STATUS enumType : EM_COVER_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_COVER_STATUS enumType : EM_COVER_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

