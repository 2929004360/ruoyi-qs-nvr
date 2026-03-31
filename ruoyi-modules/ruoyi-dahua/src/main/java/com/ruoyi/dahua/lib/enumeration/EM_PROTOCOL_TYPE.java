package com.ruoyi.dahua.lib.enumeration;

/**
 * 协议类型
*/
public enum EM_PROTOCOL_TYPE
{
    /**
     * 未知协议
    */
    EM_PROTOCOL_UNKNOWN(-1, "未知协议"),
    /**
     * TCP协议
    */
    EM_PROTOCOL_TCP(0, "TCP协议"),
    /**
     * UDP协议
    */
    EM_PROTOCOL_UDP(1, "UDP协议");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PROTOCOL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PROTOCOL_TYPE enumType : EM_PROTOCOL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PROTOCOL_TYPE enumType : EM_PROTOCOL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

