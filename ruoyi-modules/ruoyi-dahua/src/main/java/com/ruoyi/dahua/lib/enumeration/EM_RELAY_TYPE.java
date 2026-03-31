package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  是否启用中继转发 
* @date 2022/08/31 14:44:16
*/
public enum EM_RELAY_TYPE {
/**
未知
*/
    EM_RELAY_UNKNOWN(-1, "未知"),
/**
关闭
*/
    EM_RELAY_CLOSED(0, "关闭"),
/**
手动
*/
    EM_RELAY_MANUAL(1, "手动"),
/**
自动
*/
    EM_RELAY_AUTO(2, "自动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_RELAY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_RELAY_TYPE enumType : EM_RELAY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_RELAY_TYPE enumType : EM_RELAY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_RELAY_TYPE getEnum(int value) {
        for (EM_RELAY_TYPE e : EM_RELAY_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RELAY_TYPE.EM_RELAY_UNKNOWN;
    }
}

