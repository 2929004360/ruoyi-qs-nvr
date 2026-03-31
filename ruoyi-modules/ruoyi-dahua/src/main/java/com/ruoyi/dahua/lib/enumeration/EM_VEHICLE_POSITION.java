package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 抓拍时车的位置
 * @since ： Created in 2022/03/01 14:55
 */

public enum EM_VEHICLE_POSITION {
    /**
     * 未知
     */
    EM_VEHICLE_POSITION_UNKNOWN(0, "未知"),
    /**
     * 头
     */
    EM_VEHICLE_POSITION_HEAD(1, "头"),
    /**
     * 尾
     */
    EM_VEHICLE_POSITION_TAIL(2, "尾");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VEHICLE_POSITION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VEHICLE_POSITION enumType : EM_VEHICLE_POSITION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VEHICLE_POSITION enumType : EM_VEHICLE_POSITION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_VEHICLE_POSITION getEnum(int value) {
        for (EM_VEHICLE_POSITION e : EM_VEHICLE_POSITION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_VEHICLE_POSITION.EM_VEHICLE_POSITION_UNKNOWN;
    }
}

