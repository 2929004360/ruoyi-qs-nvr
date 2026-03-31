package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  当前是否具备水质检测功能 
* @date 2022/08/22 10:11:26
*/
public enum EM_WATERDATA_STAT_SERVER_SUPPORT {
/**
未知
*/
    EM_WATERDATA_STAT_SERVER_SUPPORT_UNKNOWN(-1, "未知"),
/**
不支持
*/
    EM_WATERDATA_STAT_SERVER_SUPPORT_NO(0, "不支持"),
/**
支持
*/
    EM_WATERDATA_STAT_SERVER_SUPPORT_YES(1, "支持");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_WATERDATA_STAT_SERVER_SUPPORT(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_WATERDATA_STAT_SERVER_SUPPORT enumType : EM_WATERDATA_STAT_SERVER_SUPPORT.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_WATERDATA_STAT_SERVER_SUPPORT enumType : EM_WATERDATA_STAT_SERVER_SUPPORT.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_WATERDATA_STAT_SERVER_SUPPORT getEnum(int value) {
        for (EM_WATERDATA_STAT_SERVER_SUPPORT e : EM_WATERDATA_STAT_SERVER_SUPPORT.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WATERDATA_STAT_SERVER_SUPPORT.EM_WATERDATA_STAT_SERVER_SUPPORT_UNKNOWN;
    }
}

