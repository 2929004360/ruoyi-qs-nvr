package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  设备状态 
* @date 2022/10/09 11:29:42
*/
public enum EM_DEV_STATUS {
/**
未知
*/
    EM_DEV_STATUS_UNKNOWN(-1, "未知"),
/**
离线
*/
    EM_DEV_STATUS_OFFLINE(0, "离线"),
/**
在线
*/
    EM_DEV_STATUS_ONLINE(1, "在线");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DEV_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DEV_STATUS enumType : EM_DEV_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DEV_STATUS enumType : EM_DEV_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_DEV_STATUS getEnum(int value) {
        for (EM_DEV_STATUS e : EM_DEV_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DEV_STATUS.EM_DEV_STATUS_UNKNOWN;
    }
}

