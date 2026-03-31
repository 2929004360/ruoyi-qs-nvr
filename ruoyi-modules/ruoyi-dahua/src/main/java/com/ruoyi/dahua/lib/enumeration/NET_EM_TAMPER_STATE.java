package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description   主机防拆状态 
* @date 2022/09/01 15:11:24
*/
public enum NET_EM_TAMPER_STATE {
/**
未知
*/
    NET_EM_TAMPER_UNKNOWN(-1, "未知"),
/**
未报警
*/
    NET_EM_TAMPER_NOALARM(0, "未报警"),
/**
报警中
*/
    NET_EM_TAMPER_ALARMING(1, "报警中");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_TAMPER_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_TAMPER_STATE enumType : NET_EM_TAMPER_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_TAMPER_STATE enumType : NET_EM_TAMPER_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static NET_EM_TAMPER_STATE getEnum(int value) {
        for (NET_EM_TAMPER_STATE e : NET_EM_TAMPER_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_TAMPER_STATE.NET_EM_TAMPER_UNKNOWN;
    }
}

