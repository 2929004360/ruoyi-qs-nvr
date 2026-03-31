package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description   有线网连接状态 
* @date 2022/08/31 14:35:19
*/
public enum NET_EM_ETH_STATE {
/**
未知
*/
    NET_EM_ETH_UNKNOWN(0, "未知"),
/**
连接
*/
    NET_EM_ETH_CONNECT(1, "连接"),
/**
未连接
*/
    NET_EM_ETH_DISCONNECT(2, "未连接");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_ETH_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_ETH_STATE enumType : NET_EM_ETH_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_ETH_STATE enumType : NET_EM_ETH_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_ETH_STATE getEnum(int value) {
        for (NET_EM_ETH_STATE e : NET_EM_ETH_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_ETH_STATE.NET_EM_ETH_UNKNOWN;
    }
}

