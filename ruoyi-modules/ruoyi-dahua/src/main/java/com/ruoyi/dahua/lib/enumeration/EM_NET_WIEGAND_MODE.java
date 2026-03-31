package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 工作模式
* @date 2022/03/08 09:56:02
*/
public enum EM_NET_WIEGAND_MODE {
/**
未知
*/
    EM_NET_WIEGAND_TRANSFER_MODE_UNKNOWN(-1, "未知"),
/**
韦根输入
*/
    EM_NET_WIEGAND_TRANSFER_MODE_INPUT(0, "韦根输入"),
/**
韦根输出
*/
    EM_NET_WIEGAND_TRANSFER_MODE_OUTPUT(1, "韦根输出");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_WIEGAND_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_WIEGAND_MODE enumType : EM_NET_WIEGAND_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_WIEGAND_MODE enumType : EM_NET_WIEGAND_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_NET_WIEGAND_MODE getEnum(int value) {
        for (EM_NET_WIEGAND_MODE e : EM_NET_WIEGAND_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_WIEGAND_MODE.EM_NET_WIEGAND_TRANSFER_MODE_UNKNOWN;
    }
}

