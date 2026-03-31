package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  操作模式 
* @date 2022/08/31 14:44:16
*/
public enum EM_OPERATION_MODE {
/**
未知
*/
    EM_OPERATION_UNKNOWN(-1, "未知"),
/**
紧急操作
*/
    EM_OPERATION_PANIC(0, "紧急操作"),
/**
控制操作
*/
    EM_OPERATION_CONTROL(1, "控制操作"),
/**
静音火灾报警操作
*/
    EM_OPERATION_MUTE_FIRE_ALARM(2, "静音火灾报警操作");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_OPERATION_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_OPERATION_MODE enumType : EM_OPERATION_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_OPERATION_MODE enumType : EM_OPERATION_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_OPERATION_MODE getEnum(int value) {
        for (EM_OPERATION_MODE e : EM_OPERATION_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_OPERATION_MODE.EM_OPERATION_UNKNOWN;
    }
}

