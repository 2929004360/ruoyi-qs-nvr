package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  黑臭等级 
* @date 2022/08/22 16:51:44
*/
public enum EM_SMELLY_LEVEL {
/**
未知
*/
    EM_SMELLY_LEVEL_UNKNOWN(0, "未知"),
/**
正常
*/
    EM_SMELLY_LEVEL_NORMAL(1, "正常"),
/**
轻度污染
*/
    EM_SMELLY_LEVEL_LIGHT(2, "轻度污染"),
/**
重度污染
*/
    EM_SMELLY_LEVEL_HEAVY(3, "重度污染");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_SMELLY_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_SMELLY_LEVEL enumType : EM_SMELLY_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_SMELLY_LEVEL enumType : EM_SMELLY_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_SMELLY_LEVEL getEnum(int value) {
        for (EM_SMELLY_LEVEL e : EM_SMELLY_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SMELLY_LEVEL.EM_SMELLY_LEVEL_UNKNOWN;
    }
}

