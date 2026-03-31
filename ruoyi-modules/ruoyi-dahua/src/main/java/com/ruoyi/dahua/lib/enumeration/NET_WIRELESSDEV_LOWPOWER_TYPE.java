package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  无线设备类型 
* @date 2022/07/19 10:21:10
*/
public enum NET_WIRELESSDEV_LOWPOWER_TYPE {
/**
未知设备
*/
    NET_WIRELESSDEV_UNKNOWN(0, "未知设备"),
/**
无线遥控器
*/
    NET_WIRELESSDEV_CONTROL(1, "无线遥控器"),
/**
无线防区
*/
    NET_WIRELESSDEV_DEFENCE(2, "无线防区"),
/**
无线键盘
*/
    NET_WIRELESSDEV_KEYBOARD(3, "无线键盘"),
/**
无线门磁    此字段协议上已废弃
*/
    NET_WIRELESSDEV_MAGNETOMER(4, "无线门磁    此字段协议上已废弃"),
/**
无线警号
*/
    NET_WIRELESSDEV_ALARMBELL(5, "无线警号"),
/**
智能锁
*/
    NET_WIRELESSDEV_SMARTLOCK(6, "智能锁");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_WIRELESSDEV_LOWPOWER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_WIRELESSDEV_LOWPOWER_TYPE enumType : NET_WIRELESSDEV_LOWPOWER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_WIRELESSDEV_LOWPOWER_TYPE enumType : NET_WIRELESSDEV_LOWPOWER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_WIRELESSDEV_LOWPOWER_TYPE getEnum(int value) {
        for (NET_WIRELESSDEV_LOWPOWER_TYPE e : NET_WIRELESSDEV_LOWPOWER_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_WIRELESSDEV_LOWPOWER_TYPE.NET_WIRELESSDEV_UNKNOWN;
    }
}

