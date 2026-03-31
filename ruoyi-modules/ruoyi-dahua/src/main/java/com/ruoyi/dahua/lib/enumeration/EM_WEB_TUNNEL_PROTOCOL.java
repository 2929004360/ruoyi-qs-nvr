package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  私有web代理协议类型 
* @date 2022/04/21 14:35:33
*/
public enum EM_WEB_TUNNEL_PROTOCOL {
/**
未知
*/
    EM_WEB_TUNNEL_PROTOCOL_UNKNOWN(0, "未知"),
/**
HTTP
*/
    EM_WEB_TUNNEL_PROTOCOL_HTTP(1, "HTTP"),
/**
THTPS
*/
    EM_WEB_TUNNEL_PROTOCOL_HTTPS(2, "THTPS");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_WEB_TUNNEL_PROTOCOL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_WEB_TUNNEL_PROTOCOL enumType : EM_WEB_TUNNEL_PROTOCOL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_WEB_TUNNEL_PROTOCOL enumType : EM_WEB_TUNNEL_PROTOCOL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_WEB_TUNNEL_PROTOCOL getEnum(int value) {
        for (EM_WEB_TUNNEL_PROTOCOL e : EM_WEB_TUNNEL_PROTOCOL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WEB_TUNNEL_PROTOCOL.EM_WEB_TUNNEL_PROTOCOL_UNKNOWN;
    }
}

