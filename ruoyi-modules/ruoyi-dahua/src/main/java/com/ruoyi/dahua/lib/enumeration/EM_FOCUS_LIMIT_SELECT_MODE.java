package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  聚焦极限对应枚举 
* @date 2022/11/01 11:16:54
*/
public enum EM_FOCUS_LIMIT_SELECT_MODE {
/**
 Manual  自动
*/
    EM_FOCUS_LIMIT_MODE_MANUAL(0, " Manual  自动"),
/**
 Auto 手动
*/
    EM_FOCUS_LIMIT_MODE_AUTO(1, " Auto 手动"),
/**

*/
    EM_FOCUS_LIMIT_MODE_INVALI(2, "无效");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_FOCUS_LIMIT_SELECT_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_FOCUS_LIMIT_SELECT_MODE enumType : EM_FOCUS_LIMIT_SELECT_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_FOCUS_LIMIT_SELECT_MODE enumType : EM_FOCUS_LIMIT_SELECT_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_FOCUS_LIMIT_SELECT_MODE getEnum(int value) {
        for (EM_FOCUS_LIMIT_SELECT_MODE e : EM_FOCUS_LIMIT_SELECT_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_FOCUS_LIMIT_SELECT_MODE.EM_FOCUS_LIMIT_MODE_MANUAL;
    }
}

