package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 场景触发过滤条件类型
 * @date 2022/04/20 11:28:14
 */
public enum EM_THINGS_CONDITION_TYPE {
    /**
     * 未知
     */
    EM_THINGS_CONDITION_TYPE_UNKNOWN(0, "未知"),
    /**
     * condition/timeRange 	比较当前时间是否在一个区间内，对应结构体
     */
    EM_THINGS_CONDITION_TYPE_TIMERANGE(1, " condition/timeRange 	比较当前时间是否在一个区间内，对应结构体"),
    /**
     * condition/device/property 	设备属性过滤，对应结构体
     */
    EM_THINGS_CONDITION_TYPE_PROPERTY(2, " condition/device/property 	设备属性过滤，对应结构体");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_CONDITION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_CONDITION_TYPE enumType : EM_THINGS_CONDITION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_CONDITION_TYPE enumType : EM_THINGS_CONDITION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_THINGS_CONDITION_TYPE getEnum(int value) {
        for (EM_THINGS_CONDITION_TYPE e : EM_THINGS_CONDITION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_CONDITION_TYPE.EM_THINGS_CONDITION_TYPE_UNKNOWN;
    }
}

