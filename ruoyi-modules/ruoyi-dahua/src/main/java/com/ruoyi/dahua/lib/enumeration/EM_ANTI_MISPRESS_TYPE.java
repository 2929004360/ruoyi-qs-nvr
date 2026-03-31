package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  防误按模式 
* @date 2022/08/31 14:44:16
*/
public enum EM_ANTI_MISPRESS_TYPE {
/**
未知
*/
    EM_ANTI_MISPRESS_UNKNOWN(0, "未知"),
/**
按下0.1s生效
*/
    EM_ANTI_MISPRESS_OFF(1, "按下0.1s生效"),
/**
长按3s有效
*/
    EM_ANTI_MISPRESS_LONG(2, "长按3s有效"),
/**
间断1s内连续按2次
*/
    EM_ANTI_MISPRESS_DOUBLE(3, "间断1s内连续按2次");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ANTI_MISPRESS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ANTI_MISPRESS_TYPE enumType : EM_ANTI_MISPRESS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ANTI_MISPRESS_TYPE enumType : EM_ANTI_MISPRESS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ANTI_MISPRESS_TYPE getEnum(int value) {
        for (EM_ANTI_MISPRESS_TYPE e : EM_ANTI_MISPRESS_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ANTI_MISPRESS_TYPE.EM_ANTI_MISPRESS_UNKNOWN;
    }
}

