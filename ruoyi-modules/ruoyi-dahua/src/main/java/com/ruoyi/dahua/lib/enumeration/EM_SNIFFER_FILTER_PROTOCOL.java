package com.ruoyi.dahua.lib.enumeration;

/**
 * ,**********************************************************************,, 过滤协议类型
*/
public enum EM_SNIFFER_FILTER_PROTOCOL
{
    /**
     * 未知
    */
    EM_SNIFFER_FILTER_PROTOCOL_UNKNOWN(0, "未知"),
    /**
     * TCP
    */
    EM_SNIFFER_FILTER_PROTOCOL_TCP(1, "TCP"),
    /**
     * UDP
    */
    EM_SNIFFER_FILTER_PROTOCOL_UDP(2, "UDP");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SNIFFER_FILTER_PROTOCOL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SNIFFER_FILTER_PROTOCOL enumType : EM_SNIFFER_FILTER_PROTOCOL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SNIFFER_FILTER_PROTOCOL enumType : EM_SNIFFER_FILTER_PROTOCOL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

