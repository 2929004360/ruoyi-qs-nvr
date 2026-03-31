package com.ruoyi.dahua.lib.enumeration;


/** 
子连接状态
*/
public enum EM_SUBLINK_STATE {
/**
/< 未知
*/
    EM_SUBLINK_STATE_UNKNWON(0, "/< 未知"),
/**
/< 已连接
*/
    EM_SUBLINK_STATE_CONNECT(1, "/< 已连接"),
/**
/< 已断开
*/
    EM_SUBLINK_STATE_DISCONNECT(2, "/< 已断开"),
/**
/< BSID校验失败, 子连接创建失败
*/
    EM_SUBLINK_STATE_BSID_ERROR(3, "/< BSID校验失败, 子连接创建失败");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_SUBLINK_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_SUBLINK_STATE enumType : EM_SUBLINK_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_SUBLINK_STATE enumType : EM_SUBLINK_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_SUBLINK_STATE getEnum(int value) {
        for (EM_SUBLINK_STATE e : EM_SUBLINK_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SUBLINK_STATE.EM_SUBLINK_STATE_UNKNWON;
    }
}

