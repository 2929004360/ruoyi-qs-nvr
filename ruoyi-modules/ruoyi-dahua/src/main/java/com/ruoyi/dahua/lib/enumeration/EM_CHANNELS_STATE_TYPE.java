package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  通道类型 
* @date 2022/10/09 11:22:21
*/
public enum EM_CHANNELS_STATE_TYPE {
/**
未知
*/
    EM_CHANNELS_STATE_TYPE_UNKNOWN(0, "未知"),
/**
所有通道
*/
    EM_CHANNELS_STATE_TYPE_ALL(1, "所有通道"),
/**
报警输入通道
*/
    EM_CHANNELS_STATE_TYPE_ALARMIN(2, "报警输入通道"),
/**
报警输出通道
*/
    EM_CHANNELS_STATE_TYPE_ALARMOUT(3, "报警输出通道"),
/**
警号通道
*/
    EM_CHANNELS_STATE_TYPE_SIREN(4, "警号通道");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CHANNELS_STATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CHANNELS_STATE_TYPE enumType : EM_CHANNELS_STATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CHANNELS_STATE_TYPE enumType : EM_CHANNELS_STATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_CHANNELS_STATE_TYPE getEnum(int value) {
        for (EM_CHANNELS_STATE_TYPE e : EM_CHANNELS_STATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CHANNELS_STATE_TYPE.EM_CHANNELS_STATE_TYPE_UNKNOWN;
    }
}

