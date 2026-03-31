package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  支持安全帽功能 
* @origin autoTool
* @date 2023/02/10 14:34:21
*/
public enum EM_SUPPORT_HELMET_TYPE {
/**
未知
*/
    EM_SUPPORT_HELMET_TYPE_UNKNOWN(-1, "未知"),
/**
不支持
*/
    EM_SUPPORT_HELMET_TYPE_NO_SUPPORT(0, "不支持"),
/**
支持
*/
    EM_SUPPORT_HELMET_TYPE_SUPPORT(1, "支持");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_SUPPORT_HELMET_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_SUPPORT_HELMET_TYPE enumType : EM_SUPPORT_HELMET_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_SUPPORT_HELMET_TYPE enumType : EM_SUPPORT_HELMET_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_SUPPORT_HELMET_TYPE getEnum(int value) {
        for (EM_SUPPORT_HELMET_TYPE e : EM_SUPPORT_HELMET_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SUPPORT_HELMET_TYPE.EM_SUPPORT_HELMET_TYPE_UNKNOWN;
    }
}

