package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description   供电类型 
* @date 2022/09/01 15:11:24
*/
public enum NET_EM_POWER_TYPE {
/**
未知
*/
    NET_EM_POWER_TYPE_UNKNOWN(-1, "未知"),
/**
电源适配器
*/
    NET_EM_POWER_TYPE_POWERADAPTER(0, "电源适配器"),
/**
电池
*/
    NET_EM_POWER_TYPE_BATTERY(1, "电池"),
/**
电池+电源适配器
*/
    NET_EM_POWER_TYPE_BATTERY_AND_POWERADAPTER(2, "电池+电源适配器");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_POWER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_POWER_TYPE enumType : NET_EM_POWER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_POWER_TYPE enumType : NET_EM_POWER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static NET_EM_POWER_TYPE getEnum(int value) {
        for (NET_EM_POWER_TYPE e : NET_EM_POWER_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_POWER_TYPE.NET_EM_POWER_TYPE_UNKNOWN;
    }
}

