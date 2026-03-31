package com.ruoyi.dahua.lib.enumeration;

/**
 * 报警源设备的类型
*/
public enum EM_ALARM_TRANSMIT_DEV_SRC_TYPE
{
    /**
     * 未知
    */
    EM_ALARM_TRANSMIT_DEV_SRC_TYPE_UNKNOWN(0, "未知"),
    /**
     * Digit   数字
    */
    EM_ALARM_TRANSMIT_DEV_SRC_TYPE_DIGIT(1, "Digit   数字"),
    /**
     * Analog 模拟
    */
    EM_ALARM_TRANSMIT_DEV_SRC_TYPE_ANALOG(2, "Analog 模拟"),
    /**
     * SmartLock 智能锁
    */
    EM_ALARM_TRANSMIT_DEV_SRC_TYPE_SMARTLOCK(3, "SmartLock 智能锁");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ALARM_TRANSMIT_DEV_SRC_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ALARM_TRANSMIT_DEV_SRC_TYPE enumType : EM_ALARM_TRANSMIT_DEV_SRC_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ALARM_TRANSMIT_DEV_SRC_TYPE enumType : EM_ALARM_TRANSMIT_DEV_SRC_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

