package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 车辆行驶方向（相对车道方向）
 * @since ： Created in 2022/03/01 14:55
 */

public enum EM_VEHICLE_DRIVING_DIRECTION {
    /**
     * 未知
     */
    EM_VEHICLE_DRIVING_DIRECTION_UNKNOWN(0, "未知"),
    /**
     * 正向
     */
    EM_VEHICLE_DRIVING_DIRECTION_OBVERSE(1, "正向"),
    /**
     * 反向
     */
    EM_VEHICLE_DRIVING_DIRECTION_REVERSE(2, "反向");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VEHICLE_DRIVING_DIRECTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VEHICLE_DRIVING_DIRECTION enumType : EM_VEHICLE_DRIVING_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VEHICLE_DRIVING_DIRECTION enumType : EM_VEHICLE_DRIVING_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_VEHICLE_DRIVING_DIRECTION getEnum(int value) {
        for (EM_VEHICLE_DRIVING_DIRECTION e : EM_VEHICLE_DRIVING_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_VEHICLE_DRIVING_DIRECTION.EM_VEHICLE_DRIVING_DIRECTION_UNKNOWN;
    }
}

