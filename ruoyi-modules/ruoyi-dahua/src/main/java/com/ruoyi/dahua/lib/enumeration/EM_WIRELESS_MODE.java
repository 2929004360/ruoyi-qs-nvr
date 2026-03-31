package com.ruoyi.dahua.lib.enumeration;

/**
 * 无线模块类型
*/
public enum EM_WIRELESS_MODE
{
    EM_WIRELESS_UNKNOWN(0, ""),
    EM_WIRELESS_3G(1, ""),
    EM_WIRELESS_3G1(2, ""),
    EM_WIRELESS_3G2(3, ""),
    EM_WIRELESS_3G3(4, "");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_WIRELESS_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WIRELESS_MODE enumType : EM_WIRELESS_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WIRELESS_MODE enumType : EM_WIRELESS_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

