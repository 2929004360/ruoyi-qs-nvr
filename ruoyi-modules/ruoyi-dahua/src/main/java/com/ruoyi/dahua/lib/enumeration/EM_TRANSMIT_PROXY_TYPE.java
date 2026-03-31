package com.ruoyi.dahua.lib.enumeration;


/** 
子连接连接方式
*/
public enum EM_TRANSMIT_PROXY_TYPE {
/**
/< Web代理。由服务端自动获取web端口，客户端不需要关心
*/
    EM_TRANSMIT_PROXY_TYPE_WEB(1, "/< Web代理。由服务端自动获取web端口，客户端不需要关心"),
/**
/< 通用端口代理。要求客户端知道服务的端口
*/
    EM_TRANSMIT_PROXY_TYPE_GENERAL(2, "/< 通用端口代理。要求客户端知道服务的端口");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_TRANSMIT_PROXY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_TRANSMIT_PROXY_TYPE enumType : EM_TRANSMIT_PROXY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_TRANSMIT_PROXY_TYPE enumType : EM_TRANSMIT_PROXY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return 0;
    }

public static EM_TRANSMIT_PROXY_TYPE getEnum(int value) {
        for (EM_TRANSMIT_PROXY_TYPE e : EM_TRANSMIT_PROXY_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_TRANSMIT_PROXY_TYPE.EM_TRANSMIT_PROXY_TYPE_WEB;
    }
}

