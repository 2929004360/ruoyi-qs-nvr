package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  是否支持本地存储 
* @date 2022/08/22 10:11:26
*/
public enum EM_SUPPORT_LOCALDATA_STORE {
/**
未知
*/
    EM_SUPPORT_LOCALDATA_STORE_UNKNOWN(-1, "未知"),
/**
不支持
*/
    EM_SUPPORT_LOCALDATA_STORE_NO(0, "不支持"),
/**
支持
*/
    EM_SUPPORT_LOCALDATA_STORE_YES(1, "支持");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_SUPPORT_LOCALDATA_STORE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_SUPPORT_LOCALDATA_STORE enumType : EM_SUPPORT_LOCALDATA_STORE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_SUPPORT_LOCALDATA_STORE enumType : EM_SUPPORT_LOCALDATA_STORE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_SUPPORT_LOCALDATA_STORE getEnum(int value) {
        for (EM_SUPPORT_LOCALDATA_STORE e : EM_SUPPORT_LOCALDATA_STORE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SUPPORT_LOCALDATA_STORE.EM_SUPPORT_LOCALDATA_STORE_UNKNOWN;
    }
}

