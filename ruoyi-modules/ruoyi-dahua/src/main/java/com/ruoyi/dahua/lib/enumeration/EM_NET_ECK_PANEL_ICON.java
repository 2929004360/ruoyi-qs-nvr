package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  图标 
* @origin autoTool
* @date 2023/06/27 16:54:41
*/
public enum EM_NET_ECK_PANEL_ICON {
/**
未知
*/
    EM_NET_ECK_PANEL_ICON_UNKNOWN(0, "未知"),
/**
禁止
*/
    EM_NET_ECK_PANEL_ICON_CLOSE(1, "禁止"),
/**
禁止
*/
    EM_NET_ECK_PANEL_ICON_CLOSECUSTOM(2, "禁止");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_ECK_PANEL_ICON(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_ECK_PANEL_ICON enumType : EM_NET_ECK_PANEL_ICON.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_ECK_PANEL_ICON enumType : EM_NET_ECK_PANEL_ICON.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_ECK_PANEL_ICON getEnum(int value) {
        for (EM_NET_ECK_PANEL_ICON e : EM_NET_ECK_PANEL_ICON.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_ECK_PANEL_ICON.EM_NET_ECK_PANEL_ICON_UNKNOWN;
    }
}

