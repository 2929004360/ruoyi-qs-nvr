package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  动态密码锁错误码 
* @date 2022/06/23 10:09:19
*/
public enum EM_DYNAMIC_LOCK_ERRORCODE {
/**
未知
*/
    EM_DYNAMIC_LOCK_ERRORCODE_UNKNOWN(-1, "未知"),
/**
成功
*/
    EM_DYNAMIC_LOCK_ERRORCODE_SUCCESS(0, "成功"),
/**
操作失败
*/
    EM_DYNAMIC_LOCK_ERRORCODE_FAIL(1, "操作失败"),
/**
锁ID不存在
*/
    EM_DYNAMIC_LOCK_ERRORCODE_LOCK_NO_EXIT(2, "锁ID不存在"),
/**
锁已初始化
*/
    EM_DYNAMIC_LOCK_ERRORCODE_LOCK_INIT(3, "锁已初始化"),
/**
锁模块未启用
*/
    EM_DYNAMIC_LOCK_ERRORCODE_LOCK_NOT_USE(4, "锁模块未启用");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DYNAMIC_LOCK_ERRORCODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DYNAMIC_LOCK_ERRORCODE enumType : EM_DYNAMIC_LOCK_ERRORCODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DYNAMIC_LOCK_ERRORCODE enumType : EM_DYNAMIC_LOCK_ERRORCODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_DYNAMIC_LOCK_ERRORCODE getEnum(int value) {
        for (EM_DYNAMIC_LOCK_ERRORCODE e : EM_DYNAMIC_LOCK_ERRORCODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DYNAMIC_LOCK_ERRORCODE.EM_DYNAMIC_LOCK_ERRORCODE_UNKNOWN;
    }
}

