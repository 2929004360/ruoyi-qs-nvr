package com.ruoyi.dahua.lib.enumeration;

/**
 * 协议类型
*/
public enum EM_ACCOUNT_PROTOCOL
{
    /**
     * 通用协议
    */
    EM_ACCOUNT_PROTOCOL_NORMAL(0, "通用协议"),
    /**
     * TTLV协议
    */
    EM_ACCOUNT_PROTOCOL_TTLV(1, "TTLV协议");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ACCOUNT_PROTOCOL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ACCOUNT_PROTOCOL enumType : EM_ACCOUNT_PROTOCOL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ACCOUNT_PROTOCOL enumType : EM_ACCOUNT_PROTOCOL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

