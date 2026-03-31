package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 报警类型
 * @since ： Created in 2021/11/26 10:13
 */

public enum EM_CFG_CROSSREGION_ALARMTYPE {
    /**
     * 未知
     */
    EM_CFG_CROSSREGION_ALARMTYPE_UNKNOWN(0, "未知"),
    /**
     * 报警
     */
    EM_CFG_CROSSREGION_ALARMTYPE_ALARM(1, "报警"),
    /**
     * 预警
     */
    EM_CFG_CROSSREGION_ALARMTYPE_WARNING(2, "预警"),
    /**
     * 屏蔽
     */
    EM_CFG_CROSSREGION_ALARMTYPE_SHIELD(3, "屏蔽");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_CFG_CROSSREGION_ALARMTYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CFG_CROSSREGION_ALARMTYPE enumType : EM_CFG_CROSSREGION_ALARMTYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CFG_CROSSREGION_ALARMTYPE enumType : EM_CFG_CROSSREGION_ALARMTYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_CFG_CROSSREGION_ALARMTYPE getEnum(int value) {
        for (EM_CFG_CROSSREGION_ALARMTYPE e : EM_CFG_CROSSREGION_ALARMTYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CFG_CROSSREGION_ALARMTYPE.EM_CFG_CROSSREGION_ALARMTYPE_UNKNOWN;
    }
}

