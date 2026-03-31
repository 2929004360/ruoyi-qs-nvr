package com.ruoyi.dahua.lib.enumeration;

/**
 * 休眠策略配置的低功耗模式
*/
public enum EM_SLEEPING_CONTRONL_MODE
{
    /**
     * unknown
    */
    NET_EM_SLEEPING_CONTRONL_UNKNOWN(0, "unknown"),
    /**
     * Timing: 定时休眠
    */
    NET_EM_SLEEPING_CONTRONL_TIMING(1, "Timing: 定时休眠"),
    /**
     * ShortMessage:短信休眠、唤醒模式
    */
    NET_EM_SLEEPING_CONTRONL_SHORTMESSAGE(2, "ShortMessage:短信休眠、唤醒模式"),
    /**
     * TimePeriod:按时间段休眠
    */
    NET_EM_SLEEPING_CONTRONL_TIMEPERIOD(3, "TimePeriod:按时间段休眠"),
    /**
     * MoveCtrl:运动控制模式
    */
    NET_EM_SLEEPING_CONTRONL_MOVECTRL(4, "MoveCtrl:运动控制模式"),
    /**
     * AutoCtrl:常供电模式
    */
    NET_EM_SLEEPING_CONTRONL_AUTOCTRL(5, "AutoCtrl:常供电模式"),
    /**
     * DormancyTimiWkUp:休眠、定时唤醒模式
    */
    NET_EM_SLEEPING_CONTRONL_DORMANCYTIMIWKUP(6, "DormancyTimiWkUp:休眠、定时唤醒模式"),
    /**
     * LowPowerLive:低功耗在线模式，仅调整部分参数以降低功耗
    */
    NET_EM_SLEEPING_CONTRONL_LOWPOWERLIVE(7, "LowPowerLive:低功耗在线模式，仅调整部分参数以降低功耗");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SLEEPING_CONTRONL_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SLEEPING_CONTRONL_MODE enumType : EM_SLEEPING_CONTRONL_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SLEEPING_CONTRONL_MODE enumType : EM_SLEEPING_CONTRONL_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

