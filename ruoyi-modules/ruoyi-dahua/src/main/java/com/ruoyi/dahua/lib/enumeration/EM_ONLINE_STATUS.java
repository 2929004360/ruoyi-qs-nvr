package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  在线状态 
* @date 2022/08/31 14:44:15
*/
public enum EM_ONLINE_STATUS {
/**
未知
*/
    EM_ONLINE_UNKNOWN(0, "未知"),
/**
离线
*/
    EM_ONLINE_OFF(1, "离线"),
/**
在线
*/
    EM_ONLINE_ON(2, "在线");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ONLINE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ONLINE_STATUS enumType : EM_ONLINE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ONLINE_STATUS enumType : EM_ONLINE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ONLINE_STATUS getEnum(int value) {
        for (EM_ONLINE_STATUS e : EM_ONLINE_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ONLINE_STATUS.EM_ONLINE_UNKNOWN;
    }
}

