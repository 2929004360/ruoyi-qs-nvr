package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  雷达检测车型 
* @date 2022/06/07 09:46:01
*/
public enum EM_RADAR_DETECTION_VEHICLE_TYPE {
/**
未知
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_UNKNOWN(0, "未知"),
/**
中小客车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_MID_PASSENGER_CAR(1, "中小客车"),
/**
大客车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_MOTOR_BUS(2, "大客车"),
/**
小货车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_MICRO_TRUCK(3, "小货车"),
/**
中型货车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_MID_TRUCK(4, "中型货车"),
/**
大型货车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_LARGE_TRUCK(5, "大型货车"),
/**
特大货车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_SUPER_TRUCK(6, "特大货车"),
/**
集装箱车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_CONTAINER_CAR(7, "集装箱车"),
/**
摩托车
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_MOTORCYCLE(8, "摩托车"),
/**
拖拉机
*/
    EM_RADAR_DETECTION_VEHICLE_TYPE_TRACTOR(9, "拖拉机");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_RADAR_DETECTION_VEHICLE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_RADAR_DETECTION_VEHICLE_TYPE enumType : EM_RADAR_DETECTION_VEHICLE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_RADAR_DETECTION_VEHICLE_TYPE enumType : EM_RADAR_DETECTION_VEHICLE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_RADAR_DETECTION_VEHICLE_TYPE getEnum(int value) {
        for (EM_RADAR_DETECTION_VEHICLE_TYPE e : EM_RADAR_DETECTION_VEHICLE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RADAR_DETECTION_VEHICLE_TYPE.EM_RADAR_DETECTION_VEHICLE_TYPE_UNKNOWN;
    }
}

