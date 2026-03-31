package com.ruoyi.dahua.lib.enumeration;

/**
 * 传感器类型
*/
public enum EM_SENSOR_TYPE
{
    /**
     * 未知
    */
    EM_SENSOR_TYPE_UNKNOWN(0, "未知"),
    /**
     * 常闭
    */
    EM_SENSOR_TYPE_NC(1, "常闭"),
    /**
     * 常开
    */
    EM_SENSOR_TYPE_NO(2, "常开");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SENSOR_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SENSOR_TYPE enumType : EM_SENSOR_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SENSOR_TYPE enumType : EM_SENSOR_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

