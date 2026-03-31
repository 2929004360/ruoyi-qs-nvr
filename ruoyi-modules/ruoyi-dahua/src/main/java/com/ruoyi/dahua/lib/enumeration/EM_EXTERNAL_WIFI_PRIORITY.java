package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  外部wifi优先级 
* @date 2022/08/31 14:44:17
*/
public enum EM_EXTERNAL_WIFI_PRIORITY {
/**
未知
*/
    EM_EXTERNAL_WIFI_UNKNOWN(0, "未知"),
/**
首选外部wifi
*/
    EM_EXTERNAL_WIFI_FIRST(1, "首选外部wifi"),
/**
备选外部wifi
*/
    EM_EXTERNAL_WIFI_ALTERNATIVE(2, "备选外部wifi"),
/**
仅使用外部wifi
*/
    EM_EXTERNAL_WIFI_USEONLY(3, "仅使用外部wifi");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_EXTERNAL_WIFI_PRIORITY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_EXTERNAL_WIFI_PRIORITY enumType : EM_EXTERNAL_WIFI_PRIORITY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_EXTERNAL_WIFI_PRIORITY enumType : EM_EXTERNAL_WIFI_PRIORITY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_EXTERNAL_WIFI_PRIORITY getEnum(int value) {
        for (EM_EXTERNAL_WIFI_PRIORITY e : EM_EXTERNAL_WIFI_PRIORITY.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_EXTERNAL_WIFI_PRIORITY.EM_EXTERNAL_WIFI_UNKNOWN;
    }
}

