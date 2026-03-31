package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 251823
* @description  是否穿着防火衣 
* @date 2022/11/01 19:47:04
*/
public enum EM_FIREPROOF_CLOTHES_STATE {
/**
未知
*/
    EM_FIREPROOF_CLOTHES_STATE_UNKNOWN(0, "未知"),
/**
没有穿着防火衣
*/
    EM_FIREPROOF_CLOTHES_STATE_NO(1, "没有穿着防火衣"),
/**
有穿着防火衣
*/
    EM_FIREPROOF_CLOTHES_STATE_YES(2, "有穿着防火衣"),
/**
不存在指定颜色的防火衣
*/
    EM_FIREPROOF_CLOTHES_STATE_NOEXIT(3, "不存在指定颜色的防火衣");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_FIREPROOF_CLOTHES_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_FIREPROOF_CLOTHES_STATE enumType : EM_FIREPROOF_CLOTHES_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_FIREPROOF_CLOTHES_STATE enumType : EM_FIREPROOF_CLOTHES_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_FIREPROOF_CLOTHES_STATE getEnum(int value) {
        for (EM_FIREPROOF_CLOTHES_STATE e : EM_FIREPROOF_CLOTHES_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_FIREPROOF_CLOTHES_STATE.EM_FIREPROOF_CLOTHES_STATE_UNKNOWN;
    }
}

