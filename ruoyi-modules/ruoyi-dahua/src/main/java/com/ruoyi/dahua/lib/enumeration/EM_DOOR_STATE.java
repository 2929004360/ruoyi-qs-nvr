package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  箱门状态 
* @date 2022/06/28 19:44:55
*/
public enum EM_DOOR_STATE {
/**
未知
*/
    EM_DOOR_STATE_UNKNOWN(0, "未知"),
/**
打开
*/
    EM_DOOR_STATE_OPEN(1, "打开"),
/**
关闭
*/
    EM_DOOR_STATE_CLOSE(2, "关闭");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DOOR_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DOOR_STATE enumType : EM_DOOR_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DOOR_STATE enumType : EM_DOOR_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DOOR_STATE getEnum(int value) {
        for (EM_DOOR_STATE e : EM_DOOR_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DOOR_STATE.EM_DOOR_STATE_UNKNOWN;
    }
}

