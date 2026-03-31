package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  表盘状态 
* @date 2022/06/28 19:44:55
*/
public enum EM_DIAL_STATE {
/**
未知
*/
    EM_DIAL_STATE_UNKNOWN(0, "未知"),
/**
正常
*/
    EM_DIAL_STATE_NORMAL(1, "正常"),
/**
模糊
*/
    EM_DIAL_STATE_DIM(2, "模糊"),
/**
表盘破损
*/
    EM_DIAL_STATE_DIAL_BROKEN(3, "表盘破损"),
/**
外壳破裂
*/
    EM_DIAL_STATE_SHELL_BROKEN(4, "外壳破裂"),
/**
读数异常
*/
    EM_DIAL_STATE_ABNORMAL_READING(5, "读数异常");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DIAL_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DIAL_STATE enumType : EM_DIAL_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DIAL_STATE enumType : EM_DIAL_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DIAL_STATE getEnum(int value) {
        for (EM_DIAL_STATE e : EM_DIAL_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DIAL_STATE.EM_DIAL_STATE_UNKNOWN;
    }
}

