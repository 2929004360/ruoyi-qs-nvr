package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  箭头指向，箭头专用 
* @origin autoTool
* @date 2023/06/27 16:54:41
*/
public enum EM_NET_ECK_PANEL_DIRECTION {
/**
未知
*/
    EM_NET_ECK_PANEL_DIRECTION_UNKNOWN(0, "未知"),
/**
左
*/
    EM_NET_ECK_PANEL_DIRECTION_LEFT(1, "左"),
/**
上
*/
    EM_NET_ECK_PANEL_DIRECTION_UP(2, "上"),
/**
右
*/
    EM_NET_ECK_PANEL_DIRECTION_RIGHT(3, "右");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_ECK_PANEL_DIRECTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_ECK_PANEL_DIRECTION enumType : EM_NET_ECK_PANEL_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_ECK_PANEL_DIRECTION enumType : EM_NET_ECK_PANEL_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_ECK_PANEL_DIRECTION getEnum(int value) {
        for (EM_NET_ECK_PANEL_DIRECTION e : EM_NET_ECK_PANEL_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_ECK_PANEL_DIRECTION.EM_NET_ECK_PANEL_DIRECTION_UNKNOWN;
    }
}

