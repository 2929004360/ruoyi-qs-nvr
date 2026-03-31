package com.ruoyi.dahua.lib.enumeration;


/** 
子连接连接方式
*/
public enum EM_TRANSMIT_PROXY_MODE {
/**
/< 本地代理
*/
    EM_TRANSMIT_PROXY_MODE_LOCAL(1, "/< 本地代理"),
/**
/< 级联代理
*/
    EM_TRANSMIT_PROXY_MODE_CASCADE(2, "/< 级联代理");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_TRANSMIT_PROXY_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_TRANSMIT_PROXY_MODE enumType : EM_TRANSMIT_PROXY_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_TRANSMIT_PROXY_MODE enumType : EM_TRANSMIT_PROXY_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return 0;
    }

public static EM_TRANSMIT_PROXY_MODE getEnum(int value) {
        for (EM_TRANSMIT_PROXY_MODE e : EM_TRANSMIT_PROXY_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_TRANSMIT_PROXY_MODE.EM_TRANSMIT_PROXY_MODE_LOCAL;
    }
}

