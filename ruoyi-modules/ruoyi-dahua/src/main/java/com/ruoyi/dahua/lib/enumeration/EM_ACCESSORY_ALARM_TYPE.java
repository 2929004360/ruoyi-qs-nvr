package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  报警类型 
* @date 2022/08/31 14:44:15
*/
public enum EM_ACCESSORY_ALARM_TYPE {
/**
未知
*/
    EM_ACCESSORY_ALARM_UNKNOWN(-1, "未知"),
/**
入侵
*/
    EM_ACCESSORY_ALARM_INTRUSION(0, "入侵"),
/**
火警
*/
    EM_ACCESSORY_ALARM_FIRE(1, "火警"),
/**
医疗
*/
    EM_ACCESSORY_ALARM_MEDICAL(2, "医疗"),
/**
紧急
*/
    EM_ACCESSORY_ALARM_PANIC(3, "紧急"),
/**
燃气
*/
    EM_ACCESSORY_ALARM_GAS(4, "燃气"),
/**
双按钮紧急按钮报警类型
*/
    EM_ACCESSORY_ALARM_HOLDUP(5, "双按钮紧急按钮报警类型");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ACCESSORY_ALARM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ACCESSORY_ALARM_TYPE enumType : EM_ACCESSORY_ALARM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ACCESSORY_ALARM_TYPE enumType : EM_ACCESSORY_ALARM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_ACCESSORY_ALARM_TYPE getEnum(int value) {
        for (EM_ACCESSORY_ALARM_TYPE e : EM_ACCESSORY_ALARM_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ACCESSORY_ALARM_TYPE.EM_ACCESSORY_ALARM_UNKNOWN;
    }
}

