package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  功率调节类型 
* @date 2022/08/31 14:44:15
*/
public enum EM_POWER_REGULATION_TYPE {
/**
未知
*/
    EM_POWER_REGULATION_UNKNOWN(-1, "未知"),
/**
自动
*/
    EM_POWER_REGULATION_AUTO(0, "自动"),
/**
低
*/
    EM_POWER_REGULATION_LOW(1, "低"),
/**
中
*/
    EM_POWER_REGULATION_MEDIUM(2, "中"),
/**
高
*/
    EM_POWER_REGULATION_HIGH(3, "高");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_POWER_REGULATION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_POWER_REGULATION_TYPE enumType : EM_POWER_REGULATION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_POWER_REGULATION_TYPE enumType : EM_POWER_REGULATION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_POWER_REGULATION_TYPE getEnum(int value) {
        for (EM_POWER_REGULATION_TYPE e : EM_POWER_REGULATION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_POWER_REGULATION_TYPE.EM_POWER_REGULATION_UNKNOWN;
    }
}

