package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  显示颜色 
* @origin autoTool
* @date 2023/06/27 16:54:41
*/
public enum EM_NET_ECK_PANEL_COLOR {
/**
未知
*/
    EM_NET_ECK_PANEL_COLOR_UNKNOWN(0, "未知"),
/**
红色
*/
    EM_NET_ECK_PANEL_COLOR_RED(1, "红色"),
/**
绿色
*/
    EM_NET_ECK_PANEL_COLOR_GREEN(2, "绿色"),
/**
黄色
*/
    EM_NET_ECK_PANEL_COLOR_YELLOW(3, "黄色");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_ECK_PANEL_COLOR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_ECK_PANEL_COLOR enumType : EM_NET_ECK_PANEL_COLOR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_ECK_PANEL_COLOR enumType : EM_NET_ECK_PANEL_COLOR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_ECK_PANEL_COLOR getEnum(int value) {
        for (EM_NET_ECK_PANEL_COLOR e : EM_NET_ECK_PANEL_COLOR.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_ECK_PANEL_COLOR.EM_NET_ECK_PANEL_COLOR_UNKNOWN;
    }
}

