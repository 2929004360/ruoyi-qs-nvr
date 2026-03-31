package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  水质类别，越小越好 
* @date 2022/08/22 16:51:44
*/
public enum EM_WATER_QUALITY {
/**
未知
*/
    EM_WATER_QUALITY_UNKNOWN(0, "未知"),
/**
1类
*/
    EM_WATER_QUALITY_1(1, "1类"),
/**
2类
*/
    EM_WATER_QUALITY_2(2, "2类"),
/**
3类
*/
    EM_WATER_QUALITY_3(3, "3类"),
/**
4类
*/
    EM_WATER_QUALITY_4(4, "4类"),
/**
5类
*/
    EM_WATER_QUALITY_5(5, "5类");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_WATER_QUALITY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_WATER_QUALITY enumType : EM_WATER_QUALITY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_WATER_QUALITY enumType : EM_WATER_QUALITY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_WATER_QUALITY getEnum(int value) {
        for (EM_WATER_QUALITY e : EM_WATER_QUALITY.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WATER_QUALITY.EM_WATER_QUALITY_UNKNOWN;
    }
}

