package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  分组安全等级 
* @date 2022/12/01 16:27:09
*/
public enum EM_GROUP_GRADE_TYPE {
/**
未知
*/
    EM_GROUP_GRADE_TYPE_UNKNOWN(-1, "未知"),
/**
普通级别
*/
    EM_GROUP_GRADE_TYPE_NORMAL(0, "普通级别"),
/**
警示级别
*/
    EM_GROUP_GRADE_TYPE_WARN(1, "警示级别"),
/**
危险级别
*/
    EM_GROUP_GRADE_TYPE_DANGER(2, "危险级别");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GROUP_GRADE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GROUP_GRADE_TYPE enumType : EM_GROUP_GRADE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GROUP_GRADE_TYPE enumType : EM_GROUP_GRADE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_GROUP_GRADE_TYPE getEnum(int value) {
        for (EM_GROUP_GRADE_TYPE e : EM_GROUP_GRADE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GROUP_GRADE_TYPE.EM_GROUP_GRADE_TYPE_UNKNOWN;
    }
}

