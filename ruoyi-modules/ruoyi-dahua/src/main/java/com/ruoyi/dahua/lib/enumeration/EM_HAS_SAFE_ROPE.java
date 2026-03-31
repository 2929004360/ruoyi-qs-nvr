package com.ruoyi.dahua.lib.enumeration;

/**
 * 是否有安全绳
*/
public enum EM_HAS_SAFE_ROPE
{
    /**
     * 未知
    */
    EM_SAFE_ROPE_UNKNOWN(0, "未知"),
    /**
     * 无
    */
    EM_SAFE_ROPE_NO(1, "无"),
    /**
     * 有
    */
    EM_SAFE_ROPE_YES(2, "有");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HAS_SAFE_ROPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HAS_SAFE_ROPE enumType : EM_HAS_SAFE_ROPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HAS_SAFE_ROPE enumType : EM_HAS_SAFE_ROPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

