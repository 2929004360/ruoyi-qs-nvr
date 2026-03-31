package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  按钮报警类型 
* @date 2022/08/31 14:44:18
*/
public enum EM_BUTTON_ALARM_TYPE {
/**
未知
*/
    EM_BUTTON_ALARM_UNKNOWN(-1, "未知"),
/**
火警
*/
    EM_BUTTON_ALARM_FIRE(0, "火警"),
/**
紧急报警
*/
    EM_BUTTON_ALARM_EMERGENCY(1, "紧急报警"),
/**
医疗报警
*/
    EM_BUTTON_ALARM_MEDICAL(2, "医疗报警");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_BUTTON_ALARM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_BUTTON_ALARM_TYPE enumType : EM_BUTTON_ALARM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_BUTTON_ALARM_TYPE enumType : EM_BUTTON_ALARM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_BUTTON_ALARM_TYPE getEnum(int value) {
        for (EM_BUTTON_ALARM_TYPE e : EM_BUTTON_ALARM_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_BUTTON_ALARM_TYPE.EM_BUTTON_ALARM_UNKNOWN;
    }
}

