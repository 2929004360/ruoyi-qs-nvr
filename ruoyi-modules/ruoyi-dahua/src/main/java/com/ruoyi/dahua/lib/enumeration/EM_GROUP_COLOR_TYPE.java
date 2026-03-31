package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  分组物品颜色 
* @date 2022/12/01 16:27:09
*/
public enum EM_GROUP_COLOR_TYPE {
/**
未知
*/
    EM_GROUP_COLOR_TYPE_UNKNOWN(-1, "未知"),
/**
枣红色,rgb(195,33,54)
*/
    EM_GROUP_COLOR_TYPE_JUJUBE_RED(0, "枣红色,rgb(195,33,54)"),
/**
紫色,rgb(255,0,255)
*/
    EM_GROUP_COLOR_TYPE_PURPLE(1, "紫色,rgb(255,0,255)"),
/**
灰色,rgb(192,192,192)
*/
    EM_GROUP_COLOR_TYPE_GRAY(2, "灰色,rgb(192,192,192)"),
/**
高亮黄色,rgb(255,255, 0)
*/
    EM_GROUP_COLOR_TYPE_HIGHLIGHT_YELLOW(3, "高亮黄色,rgb(255,255, 0)"),
/**
青色
*/
    EM_GROUP_COLOR_TYPE_CYAN(4, "青色"),
/**
粉色
*/
    EM_GROUP_COLOR_TYPE_PINK(5, "粉色"),
/**
黑色
*/
    EM_GROUP_COLOR_TYPE_BLACK(6, "黑色"),
/**
棕色
*/
    EM_GROUP_COLOR_TYPE_BROWN(7, "棕色");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GROUP_COLOR_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GROUP_COLOR_TYPE enumType : EM_GROUP_COLOR_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GROUP_COLOR_TYPE enumType : EM_GROUP_COLOR_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_GROUP_COLOR_TYPE getEnum(int value) {
        for (EM_GROUP_COLOR_TYPE e : EM_GROUP_COLOR_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GROUP_COLOR_TYPE.EM_GROUP_COLOR_TYPE_UNKNOWN;
    }
}

