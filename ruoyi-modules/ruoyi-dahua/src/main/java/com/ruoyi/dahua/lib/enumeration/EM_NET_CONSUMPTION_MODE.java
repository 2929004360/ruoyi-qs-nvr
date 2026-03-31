package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 消费模式
* @date 2022/03/07 10:54:03
*/
public enum EM_NET_CONSUMPTION_MODE {
/**
未知
*/
    EM_NET_CONSUMPTION_MODE_UNKNOWN(-1, "未知"),
/**
定额消费
*/
    EM_NET_CONSUMPTION_MODE_QUOTA(0, "定额消费"),
/**
非定额消费
*/
    EM_NET_CONSUMPTION_MODE_NONQUOTA(1, "非定额消费"),
/**
充值
*/
    EM_NET_CONSUMPTION_MODE_DEPOSIT(2, "充值");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_CONSUMPTION_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_CONSUMPTION_MODE enumType : EM_NET_CONSUMPTION_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_CONSUMPTION_MODE enumType : EM_NET_CONSUMPTION_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_NET_CONSUMPTION_MODE getEnum(int value) {
        for (EM_NET_CONSUMPTION_MODE e : EM_NET_CONSUMPTION_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_CONSUMPTION_MODE.EM_NET_CONSUMPTION_MODE_UNKNOWN;
    }
}

