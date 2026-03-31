package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  触发时机类型 
* @date 2022/06/07 09:46:00
*/
public enum EM_TRIGGER_OCCUR_TYPE {
/**
未知
*/
    EM_TRIGGER_OCCUR_TYPE_UNKNOWN(-1, "未知"),
/**
进入
*/
    EM_TRIGGER_OCCUR_TYPE_ENTER(0, "进入"),
/**
离开
*/
    EM_TRIGGER_OCCUR_TYPE_LEAVE(1, "离开");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_TRIGGER_OCCUR_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_TRIGGER_OCCUR_TYPE enumType : EM_TRIGGER_OCCUR_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_TRIGGER_OCCUR_TYPE enumType : EM_TRIGGER_OCCUR_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_TRIGGER_OCCUR_TYPE getEnum(int value) {
        for (EM_TRIGGER_OCCUR_TYPE e : EM_TRIGGER_OCCUR_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_TRIGGER_OCCUR_TYPE.EM_TRIGGER_OCCUR_TYPE_UNKNOWN;
    }
}

