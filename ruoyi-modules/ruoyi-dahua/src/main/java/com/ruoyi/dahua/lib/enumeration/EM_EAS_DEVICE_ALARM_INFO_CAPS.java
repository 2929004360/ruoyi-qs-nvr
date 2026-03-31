package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持的报警信息
*/
public enum EM_EAS_DEVICE_ALARM_INFO_CAPS
{
    /**
     * 未知
    */
    EM_EAS_DEVICE_ALARM_INFO_UNKNOWN(0, "未知"),
    /**
     * All:所有的报警信息
    */
    EM_EAS_DEVICE_ALARM_INFO_ALL(1, "All:所有的报警信息"),
    /**
     * FalseAlarm:误报信息
    */
    EM_EAS_DEVICE_ALARM_INFO_FALSE_ALARM(2, "FalseAlarm:误报信息"),
    /**
     * AlarmCount:报警计数
    */
    EM_EAS_DEVICE_ALARM_INFO_ALARM_COUNT(3, "AlarmCount:报警计数"),
    /**
     * AlarmSignalAtrength:报警信号强度
    */
    EM_EAS_DEVICE_ALARM_INFO_ALARM_SIGNAL_STRENGTH(4, "AlarmSignalAtrength:报警信号强度");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_EAS_DEVICE_ALARM_INFO_CAPS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_EAS_DEVICE_ALARM_INFO_CAPS enumType : EM_EAS_DEVICE_ALARM_INFO_CAPS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_EAS_DEVICE_ALARM_INFO_CAPS enumType : EM_EAS_DEVICE_ALARM_INFO_CAPS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

