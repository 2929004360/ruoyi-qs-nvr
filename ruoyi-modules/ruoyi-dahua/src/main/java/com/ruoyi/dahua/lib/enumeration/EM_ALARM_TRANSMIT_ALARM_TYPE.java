package com.ruoyi.dahua.lib.enumeration;

/**
 * 报警类型
*/
public enum EM_ALARM_TRANSMIT_ALARM_TYPE
{
    /**
     * 未知
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_UNKNOWN(0, "未知"),
    /**
     * "PreventRemove"   防拆
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_PREVENTREMOVE(1, "\"PreventRemove\"   防拆"),
    /**
     * "LowerPower"  低电压
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_LOWERPOWER(2, "\"LowerPower\"  低电压"),
    /**
     * "Key"          钥匙
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_KEY(3, "\"Key\"          钥匙"),
    /**
     * "Duress"       胁迫
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_DURESS(4, "\"Duress\"       胁迫"),
    /**
     * "BatteryLost"  电池不在位
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_BATTERYLOST(5, "\"BatteryLost\"  电池不在位"),
    /**
     * "PSTNBreakLine"电话线断开
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_PSTN_BREAKLINE(6, "\"PSTNBreakLine\"电话线断开"),
    /**
     * "EmergencyHelp"紧急求助
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_EMERGENCY_HELP(7, "\"EmergencyHelp\"紧急求助"),
    /**
     * "AlarmIn"      报警输入
    */
    EM_ALARM_TRANSMIT_ALARM_TYPE_ALARMIN(8, "\"AlarmIn\"      报警输入");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ALARM_TRANSMIT_ALARM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ALARM_TRANSMIT_ALARM_TYPE enumType : EM_ALARM_TRANSMIT_ALARM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ALARM_TRANSMIT_ALARM_TYPE enumType : EM_ALARM_TRANSMIT_ALARM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

