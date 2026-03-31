package com.ruoyi.dahua.lib.enumeration;

/**
 * 灯光联动方式
*/
public enum NET_EM_LIGHTLINK_TYPE
{
    /**
     * 闪烁； 默认
    */
    NET_EM_FILCKER(0, "闪烁； 默认"),
    /**
     * 常亮
    */
    NET_EM_KEEPLIGHTING(1, "常亮");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_LIGHTLINK_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_LIGHTLINK_TYPE enumType : NET_EM_LIGHTLINK_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_LIGHTLINK_TYPE enumType : NET_EM_LIGHTLINK_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

