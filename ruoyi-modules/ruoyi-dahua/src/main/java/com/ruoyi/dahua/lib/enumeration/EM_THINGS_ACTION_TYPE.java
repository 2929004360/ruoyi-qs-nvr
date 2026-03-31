package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 场景触发执行动作类型
 * @date 2022/04/20 11:28:14
 */
public enum EM_THINGS_ACTION_TYPE {
    /**
     * 未知
     */
    EM_THINGS_ACTION_TYPE_UNKNOWN(0, "未知"),
    /**
     * action/device/setProperty 	设置设备属性，对应结构体NET_THINGS_RULECONTENT_ACTION_SETPROPERTY
     */
    EM_THINGS_ACTION_TYPE_SETPROPERTY(1, " action/device/setProperty 	设置设备属性，对应结构体NET_THINGS_RULECONTENT_ACTION_SETPROPERTY");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_ACTION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_ACTION_TYPE enumType : EM_THINGS_ACTION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_ACTION_TYPE enumType : EM_THINGS_ACTION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_THINGS_ACTION_TYPE getEnum(int value) {
        for (EM_THINGS_ACTION_TYPE e : EM_THINGS_ACTION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_ACTION_TYPE.EM_THINGS_ACTION_TYPE_UNKNOWN;
    }
}

