package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  布防模式 
* @date 2022/08/31 14:44:17
*/
public enum EM_ARMING_TYPE {
/**
未知
*/
    EM_ARMING_UNKNOWN(-1, "未知"),
/**
在家布防
*/
    EM_ARMING_ATHOME(0, "在家布防"),
/**
外出布防
*/
    EM_ARMING_OUT(1, "外出布防");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ARMING_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ARMING_TYPE enumType : EM_ARMING_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ARMING_TYPE enumType : EM_ARMING_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_ARMING_TYPE getEnum(int value) {
        for (EM_ARMING_TYPE e : EM_ARMING_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ARMING_TYPE.EM_ARMING_UNKNOWN;
    }
}

