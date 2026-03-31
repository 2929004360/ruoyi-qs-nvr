package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  缓存类型枚举 
* @date 2022/09/22 17:24:07
*/
public enum EM_CFG_NAS_DIRECTORY_CACHE_TYPE {
/**
未知枚举
*/
    EM_CFG_NAS_DIRECTORY_CACHE_TYPE_UNKNOWN(0, "未知枚举"),
/**
直存
*/
    EM_CFG_NAS_DIRECTORY_CACHE_TYPE_DIRECT(1, "直存"),
/**
回存
*/
    EM_CFG_NAS_DIRECTORY_CACHE_TYPE_INDIRECT(2, "回存");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CFG_NAS_DIRECTORY_CACHE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CFG_NAS_DIRECTORY_CACHE_TYPE enumType : EM_CFG_NAS_DIRECTORY_CACHE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CFG_NAS_DIRECTORY_CACHE_TYPE enumType : EM_CFG_NAS_DIRECTORY_CACHE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_CFG_NAS_DIRECTORY_CACHE_TYPE getEnum(int value) {
        for (EM_CFG_NAS_DIRECTORY_CACHE_TYPE e : EM_CFG_NAS_DIRECTORY_CACHE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CFG_NAS_DIRECTORY_CACHE_TYPE.EM_CFG_NAS_DIRECTORY_CACHE_TYPE_UNKNOWN;
    }
}

