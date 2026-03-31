package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 消费记录类型
* @date 2022/03/07 11:45:31
*/
public enum EM_CONSUMPTION_EVENT_TYPE {
/**
未知
*/
    EM_CONSUMPTION_EVENT_TYPE_UNKNOWN(-1, "未知"),
/**
定额消费事件
*/
    EM_CONSUMPTION_EVENT_TYPE_QUOTA(0, "定额消费事件"),
/**
非定额消费事件
*/
    EM_CONSUMPTION_EVENT_TYPE_NONQUOTA(1, "非定额消费事件"),
/**
充值事件
*/
    EM_CONSUMPTION_EVENT_TYPE_DEPOSIT(2, "充值事件"),
/**
末次返还事件
*/
    EM_CONSUMPTION_EVENT_TYPE_RESTORE(3, "末次返还事件");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CONSUMPTION_EVENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CONSUMPTION_EVENT_TYPE enumType : EM_CONSUMPTION_EVENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CONSUMPTION_EVENT_TYPE enumType : EM_CONSUMPTION_EVENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_CONSUMPTION_EVENT_TYPE getEnum(int value) {
        for (EM_CONSUMPTION_EVENT_TYPE e : EM_CONSUMPTION_EVENT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CONSUMPTION_EVENT_TYPE.EM_CONSUMPTION_EVENT_TYPE_UNKNOWN;
    }
}

