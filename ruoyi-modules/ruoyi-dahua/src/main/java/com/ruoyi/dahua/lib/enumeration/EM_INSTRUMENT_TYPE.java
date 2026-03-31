package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  仪表类型 
* @date 2022/06/28 19:24:08
*/
public enum EM_INSTRUMENT_TYPE {
/**
未知
*/
    EM_INSTRUMENT_TYPE_UNKNOWN(0, "未知"),
/**
状态识别类型
*/
    EM_INSTRUMENT_TYPE_STATE(1, "状态识别类型"),
/**
状态匹配类型
*/
    EM_INSTRUMENT_TYPE_STATEMATCH(2, "状态匹配类型"),
/**
指针式表计类型
*/
    EM_INSTRUMENT_TYPE_METER(3, "指针式表计类型"),
/**
数字识别类型
*/
    EM_INSTRUMENT_TYPE_CHAR(4, "数字识别类型"),
/**
油表类型
*/
    EM_INSTRUMENT_TYPE_OIL(5, "油表类型"),
/**
开关标识
*/
    EM_INSTRUMENT_TYPE_SWIIDEN(6, "开关标识"),
/**
指示灯类型
*/
    EM_INSTRUMENT_TYPE_LIGHT(7, "指示灯类型"),
/**
室内开关类型
*/
    EM_INSTRUMENT_TYPE_SWITCH(8, "室内开关类型"),
/**
外观检测类型
*/
    EM_INSTRUMENT_TYPE_APPEARANCE(9, "外观检测类型"),
/**
液位计
*/
    EM_INSTRUMENT_TYPE_LEVELGAUGE(10, "液位计");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_INSTRUMENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_INSTRUMENT_TYPE enumType : EM_INSTRUMENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_INSTRUMENT_TYPE enumType : EM_INSTRUMENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_INSTRUMENT_TYPE getEnum(int value) {
        for (EM_INSTRUMENT_TYPE e : EM_INSTRUMENT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_INSTRUMENT_TYPE.EM_INSTRUMENT_TYPE_UNKNOWN;
    }
}

