package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  防区异常状态 
* @date 2022/10/09 11:29:42
*/
public enum EM_ZONE_STATUS {
/**
未知
*/
    EM_ZONE_STATUS_UNKNOWN(0, "未知"),
/**
防区报警/打开
*/
    EM_ZONE_STATUS_ALARM(1, "防区报警/打开"),
/**
防区防拆
*/
    EM_ZONE_STATUS_TAMPER(2, "防区防拆"),
/**
防区防遮挡
*/
    EM_ZONE_STATUS_MASK(3, "防区防遮挡"),
/**
防区短路
*/
    EM_ZONE_STATUS_SHORT(4, "防区短路"),
/**
防区正常
*/
    EM_ZONE_STATUS_NORMAL(5, "防区正常");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ZONE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ZONE_STATUS enumType : EM_ZONE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ZONE_STATUS enumType : EM_ZONE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ZONE_STATUS getEnum(int value) {
        for (EM_ZONE_STATUS e : EM_ZONE_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ZONE_STATUS.EM_ZONE_STATUS_UNKNOWN;
    }
}

