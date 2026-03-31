package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  外接电源状态 
* @date 2022/08/31 14:44:16
*/
public enum EM_EXPOWER_STATE {
/**
未连接
*/
    EM_EXPOWER_DISCONNECT(0, "未连接"),
/**
连接
*/
    EM_EXPOWER_CONNECT(1, "连接"),
/**
未知
*/
    EM_EXPOWER_UNKNOWN(2, "未知");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_EXPOWER_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_EXPOWER_STATE enumType : EM_EXPOWER_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_EXPOWER_STATE enumType : EM_EXPOWER_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_EXPOWER_STATE getEnum(int value) {
        for (EM_EXPOWER_STATE e : EM_EXPOWER_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_EXPOWER_STATE.EM_EXPOWER_DISCONNECT;
    }
}

