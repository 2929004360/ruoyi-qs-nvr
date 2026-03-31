package com.ruoyi.dahua.lib.enumeration;

/**
 * 车道线属性
*/
public enum EM_AREA_TYPE
{
    /**
     * 未知
    */
    EM_AREA_TYPE_UNKNOWN(0, "未知"),
    /**
     * 信号灯
    */
    EM_AREA_TYPE_SIGNAL_LIGHT(1, "信号灯"),
    /**
     * LED屏
    */
    EM_AREA_TYPE_LED_SCREEN(2, "LED屏");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_AREA_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_AREA_TYPE enumType : EM_AREA_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_AREA_TYPE enumType : EM_AREA_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

