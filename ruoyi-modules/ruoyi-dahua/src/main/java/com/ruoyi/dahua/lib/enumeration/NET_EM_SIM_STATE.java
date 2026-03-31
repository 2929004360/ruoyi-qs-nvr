package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description   sim卡状态 
* @date 2022/08/31 14:35:19
*/
public enum NET_EM_SIM_STATE {
/**
未知
*/
    NET_EM_SIM_UNKNOWN(0, "未知"),
/**
在线
*/
    NET_EM_SIM_ONLINE(1, "在线"),
/**
离线
*/
    NET_EM_SIM_OFFLINE(2, "离线");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

NET_EM_SIM_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (NET_EM_SIM_STATE enumType : NET_EM_SIM_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (NET_EM_SIM_STATE enumType : NET_EM_SIM_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static NET_EM_SIM_STATE getEnum(int value) {
        for (NET_EM_SIM_STATE e : NET_EM_SIM_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_SIM_STATE.NET_EM_SIM_UNKNOWN;
    }
}

