package com.ruoyi.dahua.lib.enumeration;

/**
 * 报警标志(保养里程小于2000km时告警标示)
*/
public enum EM_VEHICLE_STATE_ALARM_FLAG
{
    /**
     * 未知
    */
    EM_VEHICLE_STATE_ALARM_FLAG_UNKNOWN(-1, "未知"),
    /**
     * 无告警
    */
    EM_VEHICLE_STATE_ALARM_FLAG_NO_ALARM(0, "无告警"),
    /**
     * 有告警
    */
    EM_VEHICLE_STATE_ALARM_FLAG_ALARM(1, "有告警"),
    /**
     * 补传时无意义平台不需要处理
    */
    EM_VEHICLE_STATE_ALARM_FLAG_NO_MEAN(2, "补传时无意义平台不需要处理");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VEHICLE_STATE_ALARM_FLAG(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VEHICLE_STATE_ALARM_FLAG enumType : EM_VEHICLE_STATE_ALARM_FLAG.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VEHICLE_STATE_ALARM_FLAG enumType : EM_VEHICLE_STATE_ALARM_FLAG.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

