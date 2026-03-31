package com.ruoyi.dahua.lib.enumeration;

/**
 * 闪烁灯光类型
*/
public enum NET_EM_FILCKERLIGHT_TYPE
{
    /**
     * 闪烁白光灯
    */
    NET_EM_WHITELIGHT(0, "闪烁白光灯"),
    /**
     * 红蓝闪烁警示灯
    */
    NET_EM_REDBLUELIGHT(1, "红蓝闪烁警示灯");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_FILCKERLIGHT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_FILCKERLIGHT_TYPE enumType : NET_EM_FILCKERLIGHT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_FILCKERLIGHT_TYPE enumType : NET_EM_FILCKERLIGHT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

