package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  输出状态 
* @date 2022/10/09 11:29:42
*/
public enum EM_OUTPUT_STATE {
/**
未知
*/
    EM_OUTPUT_STATE_UNKNOWN(-1, "未知"),
/**
关闭
*/
    EM_OUTPUT_STATE_CLOSE(0, "关闭"),
/**
打开
*/
    EM_OUTPUT_STATE_OPEN(1, "打开");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_OUTPUT_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_OUTPUT_STATE enumType : EM_OUTPUT_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_OUTPUT_STATE enumType : EM_OUTPUT_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_OUTPUT_STATE getEnum(int value) {
        for (EM_OUTPUT_STATE e : EM_OUTPUT_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_OUTPUT_STATE.EM_OUTPUT_STATE_UNKNOWN;
    }
}

