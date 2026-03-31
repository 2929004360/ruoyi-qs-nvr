package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  箭头相对于数字的位置 
* @origin autoTool
* @date 2023/06/27 15:49:59
*/
public enum EM_GUIDESCREEN_POS {
/**

*/
    EM_GUIDESCREEN_POS_UNKNOWN(0, ""),
/**
左侧
*/
    EM_GUIDESCREEN_POS_LEFT(1, "左侧"),
/**
右侧
*/
    EM_GUIDESCREEN_POS_RIGHT(2, "右侧");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GUIDESCREEN_POS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GUIDESCREEN_POS enumType : EM_GUIDESCREEN_POS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GUIDESCREEN_POS enumType : EM_GUIDESCREEN_POS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_GUIDESCREEN_POS getEnum(int value) {
        for (EM_GUIDESCREEN_POS e : EM_GUIDESCREEN_POS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GUIDESCREEN_POS.EM_GUIDESCREEN_POS_UNKNOWN;
    }
}

