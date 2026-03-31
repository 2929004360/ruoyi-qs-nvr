package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 收费公路车辆通行费车型分类
 * @date 2022/07/20 16:46:40
 */
public enum EM_TOLLS_VEHICLE_TYPE {
    /**
     * 未知
     */
    EM_TOLLS_VEHICLE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 一型客车
     */
    EM_TOLLS_VEHICLE_TYPE_PASSENGER_CAR1(1, "一型客车"),
    /**
     * 二型客车
     */
    EM_TOLLS_VEHICLE_TYPE_PASSENGER_CAR2(2, "二型客车"),
    /**
     * 三型客车
     */
    EM_TOLLS_VEHICLE_TYPE_PASSENGER_CAR3(3, "三型客车"),
    /**
     * 四型客车
     */
    EM_TOLLS_VEHICLE_TYPE_PASSENGER_CAR4(4, "四型客车"),
    /**
     * 一型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK1(11, "一型货车"),
    /**
     * 二型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK2(12, "二型货车"),
    /**
     * 三型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK3(13, "三型货车"),
    /**
     * 四型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK4(14, "四型货车"),
    /**
     * 五型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK5(15, "五型货车"),
    /**
     * 六型货车
     */
    EM_TOLLS_VEHICLE_TYPE_TRUCK6(16, "六型货车"),
    /**
     * 一型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE1(21, "一型专项作业车"),
    /**
     * 二型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE2(22, "二型专项作业车"),
    /**
     * 三型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE3(23, "三型专项作业车"),
    /**
     * 四型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE4(24, "四型专项作业车"),
    /**
     * 五型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE5(25, "五型专项作业车"),
    /**
     * 六型专项作业车
     */
    EM_TOLLS_VEHICLE_TYPE_OPERATION_VEHICLE6(26, "六型专项作业车");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_TOLLS_VEHICLE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TOLLS_VEHICLE_TYPE enumType : EM_TOLLS_VEHICLE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TOLLS_VEHICLE_TYPE enumType : EM_TOLLS_VEHICLE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_TOLLS_VEHICLE_TYPE getEnum(int value) {
        for (EM_TOLLS_VEHICLE_TYPE e : EM_TOLLS_VEHICLE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_TOLLS_VEHICLE_TYPE.EM_TOLLS_VEHICLE_TYPE_UNKNOWN;
    }
}

