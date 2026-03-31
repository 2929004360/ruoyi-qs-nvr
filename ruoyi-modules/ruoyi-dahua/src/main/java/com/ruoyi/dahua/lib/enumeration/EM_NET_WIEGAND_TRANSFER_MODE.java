package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 传输模式
* @date 2022/03/08 09:56:03
*/
public enum EM_NET_WIEGAND_TRANSFER_MODE {
/**
未知
*/
    EM_NET_WIEGAND_MODE_UNKNOWN(-1, "未知"),
/**
韦根34bit传输,4字节卡号，2Bit校验；
*/
    EM_NET_WIEGAND_MODE_BIT_34(0, "韦根34bit传输,4字节卡号，2Bit校验；"),
/**
韦根66bit传输,8字节卡号，2Bit校验；
*/
    EM_NET_WIEGAND_MODE_BIT_66(1, "韦根66bit传输,8字节卡号，2Bit校验；"),
/**
韦根26bit传输,3字节卡号，2Bit校验；
*/
    EM_NET_WIEGAND_MODE_BIT_26(2, "韦根26bit传输,3字节卡号，2Bit校验；"),
/**
韦根32bit传输,4字节卡号，无校验位
*/
    EM_NET_WIEGAND_MODE_BIT_32(3, "韦根32bit传输,4字节卡号，无校验位"),
/**
韦根42bit传输,5字节卡号，2Bit校验
*/
    EM_NET_WIEGAND_MODE_BIT_42(4, "韦根42bit传输,5字节卡号，2Bit校验");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_WIEGAND_TRANSFER_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_WIEGAND_TRANSFER_MODE enumType : EM_NET_WIEGAND_TRANSFER_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_WIEGAND_TRANSFER_MODE enumType : EM_NET_WIEGAND_TRANSFER_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_NET_WIEGAND_TRANSFER_MODE getEnum(int value) {
        for (EM_NET_WIEGAND_TRANSFER_MODE e : EM_NET_WIEGAND_TRANSFER_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_WIEGAND_TRANSFER_MODE.EM_NET_WIEGAND_MODE_UNKNOWN;
    }
}

