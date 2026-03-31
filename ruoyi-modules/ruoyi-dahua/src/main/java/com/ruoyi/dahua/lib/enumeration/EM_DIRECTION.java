package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  箭头指向 
* @origin autoTool
* @date 2023/06/27 15:49:59
*/
public enum EM_DIRECTION {
/**

*/
    EM_DIRECTION_UNKNOWN(0, ""),
/**
左
*/
    EM_DIRECTION_LEFT(1, "左"),
/**
左上
*/
    EM_DIRECTION_UPLEFT(2, "左上"),
/**
上
*/
    EM_DIRECTION_UP(3, "上"),
/**
右上
*/
    EM_DIRECTION_UPRIGHT(4, "右上"),
/**
右
*/
    EM_DIRECTION_RIGHT(5, "右"),
/**
右下
*/
    EM_DIRECTION_DOWNRIGHT(6, "右下"),
/**
下
*/
    EM_DIRECTION_DOWN(7, "下"),
/**
左下
*/
    EM_DIRECTION_DOWNLEFT(8, "左下");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DIRECTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DIRECTION enumType : EM_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DIRECTION enumType : EM_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DIRECTION getEnum(int value) {
        for (EM_DIRECTION e : EM_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DIRECTION.EM_DIRECTION_UNKNOWN;
    }
}

