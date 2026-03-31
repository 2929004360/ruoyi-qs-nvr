package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 场景的触发类型
 * @date 2022/04/20 11:28:13
 */
public enum EM_THINGS_TRIGGER_TYPE {
    /**
     * 未知
     */
    EM_THINGS_TRIGGER_TYPE_UNKNOWN(0, "未知"),
    /**
     * trigger/timer 	定时触发，对应结构体NET_THINGS_RULECONTENT_TRIGGER_TIMER
     */
    EM_THINGS_TRIGGER_TYPE_TRIGGER_TIMER(1, " trigger/timer 	定时触发，对应结构体NET_THINGS_RULECONTENT_TRIGGER_TIMER"),
    /**
     * trigger/device/property  设备属性触发，对应结构体NET_THINGS_RULECONTENT_TRIGGER_PROPERTY
     */
    EM_THINGS_TRIGGER_TYPE_TRIGGER_PROPERTY(2, " trigger/device/property  设备属性触发，对应结构体NET_THINGS_RULECONTENT_TRIGGER_PROPERTY");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_TRIGGER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_TRIGGER_TYPE enumType : EM_THINGS_TRIGGER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_TRIGGER_TYPE enumType : EM_THINGS_TRIGGER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_THINGS_TRIGGER_TYPE getEnum(int value) {
        for (EM_THINGS_TRIGGER_TYPE e : EM_THINGS_TRIGGER_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_TRIGGER_TYPE.EM_THINGS_TRIGGER_TYPE_UNKNOWN;
    }
}

