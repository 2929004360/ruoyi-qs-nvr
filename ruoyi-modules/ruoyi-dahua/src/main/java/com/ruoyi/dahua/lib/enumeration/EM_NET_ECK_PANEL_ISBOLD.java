package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  是否加粗 
* @origin autoTool
* @date 2023/06/27 16:54:41
*/
public enum EM_NET_ECK_PANEL_ISBOLD {
/**
不加粗
*/
    EM_NET_ECK_PANEL_ISBOLD_FALSE(0, "不加粗"),
/**
加粗
*/
    EM_NET_ECK_PANEL_ISBOLD_TRUE(1, "加粗");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_ECK_PANEL_ISBOLD(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_ECK_PANEL_ISBOLD enumType : EM_NET_ECK_PANEL_ISBOLD.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_ECK_PANEL_ISBOLD enumType : EM_NET_ECK_PANEL_ISBOLD.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_ECK_PANEL_ISBOLD getEnum(int value) {
        for (EM_NET_ECK_PANEL_ISBOLD e : EM_NET_ECK_PANEL_ISBOLD.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_ECK_PANEL_ISBOLD.EM_NET_ECK_PANEL_ISBOLD_FALSE;
    }
}

