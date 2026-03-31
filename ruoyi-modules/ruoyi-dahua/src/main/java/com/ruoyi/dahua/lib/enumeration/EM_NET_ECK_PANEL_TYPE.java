package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  显示内容的类型 
* @origin autoTool
* @date 2023/06/27 16:54:40
*/
public enum EM_NET_ECK_PANEL_TYPE {
/**
整数、字母、空格
*/
    EM_NET_ECK_PANEL_TYPE_INTCHAR(0, "整数、字母、空格"),
/**
图形
*/
    EM_NET_ECK_PANEL_TYPE_IMAGE(1, "图形"),
/**
箭头
*/
    EM_NET_ECK_PANEL_TYPE_ARROWS(2, "箭头");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_ECK_PANEL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_ECK_PANEL_TYPE enumType : EM_NET_ECK_PANEL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_ECK_PANEL_TYPE enumType : EM_NET_ECK_PANEL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_ECK_PANEL_TYPE getEnum(int value) {
        for (EM_NET_ECK_PANEL_TYPE e : EM_NET_ECK_PANEL_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_ECK_PANEL_TYPE.EM_NET_ECK_PANEL_TYPE_INTCHAR;
    }
}

