package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  梯控方式触发者 
* @date 2022/10/19 14:01:07
*/
public enum EM_LIFT_CALLER_TYPE {
/**
未知
*/
    EM_LIFT_CALLER_TYPE_UNKNOWN(0, "未知"),
/**
VTO 呼叫
*/
    EM_LIFT_CALLER_TYPE_VTO(1, "VTO 呼叫"),
/**
平台呼叫
*/
    EM_LIFT_CALLER_TYPE_PLATFORM(2, "平台呼叫"),
/**
本机鉴权呼叫
*/
    EM_LIFT_CALLER_TYPE_LOCAL_AUTH(3, "本机鉴权呼叫"),
/**
门禁呼梯
*/
    EM_LIFT_CALLER_TYPE_ACCESS_CONTROL(4, "门禁呼梯");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_LIFT_CALLER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_LIFT_CALLER_TYPE enumType : EM_LIFT_CALLER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_LIFT_CALLER_TYPE enumType : EM_LIFT_CALLER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_LIFT_CALLER_TYPE getEnum(int value) {
        for (EM_LIFT_CALLER_TYPE e : EM_LIFT_CALLER_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_LIFT_CALLER_TYPE.EM_LIFT_CALLER_TYPE_UNKNOWN;
    }
}

