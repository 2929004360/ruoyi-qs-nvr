package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  盖板状态 
* @date 2022/06/28 19:44:56
*/
public enum EM_COVER_PLATE_STATE {
/**
未知
*/
    EM_COVER_PLATE_STATE_UNKNOWN(0, "未知"),
/**
正常
*/
    EM_COVER_PLATE_STATE_NORMAL(1, "正常"),
/**
破裂
*/
    EM_COVER_PLATE_STATE_BROKEN(2, "破裂");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_COVER_PLATE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_COVER_PLATE_STATE enumType : EM_COVER_PLATE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_COVER_PLATE_STATE enumType : EM_COVER_PLATE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_COVER_PLATE_STATE getEnum(int value) {
        for (EM_COVER_PLATE_STATE e : EM_COVER_PLATE_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_COVER_PLATE_STATE.EM_COVER_PLATE_STATE_UNKNOWN;
    }
}

