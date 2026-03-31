package com.ruoyi.dahua.lib.enumeration;

/**
 * 拨号网卡名称
*/
public enum EM_DIAL_INTERFACE
{
    /**
     * 未知
    */
    EM_DIAL_INTERFACE_UNKNOWN(0, "未知"),
    /**
     * 有线网卡
    */
    EM_DIAL_INTERFACE_ETH0(1, "有线网卡"),
    /**
     * 无线网卡
    */
    EM_DIAL_INTERFACE_ETH2(2, "无线网卡"),
    /**
     * 3G网卡
    */
    EM_DIAL_INTERFACE_3G(3, "3G网卡");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DIAL_INTERFACE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DIAL_INTERFACE enumType : EM_DIAL_INTERFACE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DIAL_INTERFACE enumType : EM_DIAL_INTERFACE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

