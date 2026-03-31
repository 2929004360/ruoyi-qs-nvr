package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 输出类型
* @date 2022/03/08 09:56:03
*/
public enum EM_NET_WIEGAND_OUT_TYPE {
/**
未知
*/
    EM_NET_WIEGAND_OUT_TYPE_UNKNOWN(-1, "未知"),
/**
输出ID
*/
    EM_NET_WIEGAND_OUT_TYPE_ID(0, "输出ID"),
/**
输出卡号
*/
    EM_NET_WIEGAND_OUT_TYPE_CARD_NO(1, "输出卡号");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_WIEGAND_OUT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_WIEGAND_OUT_TYPE enumType : EM_NET_WIEGAND_OUT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_WIEGAND_OUT_TYPE enumType : EM_NET_WIEGAND_OUT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_NET_WIEGAND_OUT_TYPE getEnum(int value) {
        for (EM_NET_WIEGAND_OUT_TYPE e : EM_NET_WIEGAND_OUT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_WIEGAND_OUT_TYPE.EM_NET_WIEGAND_OUT_TYPE_UNKNOWN;
    }
}

