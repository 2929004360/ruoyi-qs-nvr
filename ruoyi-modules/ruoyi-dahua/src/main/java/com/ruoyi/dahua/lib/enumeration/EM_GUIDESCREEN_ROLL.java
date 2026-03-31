package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  诱导屏内容是否滚动 
* @origin autoTool
* @date 2023/06/27 15:49:59
*/
public enum EM_GUIDESCREEN_ROLL {
/**

*/
    EM_GUIDESCREEN_ROLL_UNKNOWN(0, ""),
/**
不滚动
*/
    EM_GUIDESCREEN_ROLL_DISABLE(1, "不滚动"),
/**
滚动
*/
    EM_GUIDESCREEN_ROLL_ENABLE(2, "滚动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GUIDESCREEN_ROLL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GUIDESCREEN_ROLL enumType : EM_GUIDESCREEN_ROLL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GUIDESCREEN_ROLL enumType : EM_GUIDESCREEN_ROLL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_GUIDESCREEN_ROLL getEnum(int value) {
        for (EM_GUIDESCREEN_ROLL e : EM_GUIDESCREEN_ROLL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GUIDESCREEN_ROLL.EM_GUIDESCREEN_ROLL_UNKNOWN;
    }
}

