package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 消费结果
* @date 2022/03/07 11:45:30
*/
public enum EM_CONSUMPTION_STATUS {
/**
未知
*/
    EM_CONSUMPTION_STATUS_UNKNOWN(-1, "未知"),
/**
失败
*/
    EM_CONSUMPTION_STATUS_FAIL(0, "失败"),
/**
成功
*/
    EM_CONSUMPTION_STATUS_SUCC(1, "成功");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CONSUMPTION_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CONSUMPTION_STATUS enumType : EM_CONSUMPTION_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CONSUMPTION_STATUS enumType : EM_CONSUMPTION_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_CONSUMPTION_STATUS getEnum(int value) {
        for (EM_CONSUMPTION_STATUS e : EM_CONSUMPTION_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CONSUMPTION_STATUS.EM_CONSUMPTION_STATUS_UNKNOWN;
    }
}

