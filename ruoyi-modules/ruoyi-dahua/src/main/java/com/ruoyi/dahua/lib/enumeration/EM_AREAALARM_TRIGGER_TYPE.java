package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  区域报警触发类型 
* @date 2022/07/19 19:31:27
*/
public enum EM_AREAALARM_TRIGGER_TYPE {
/**
未知
*/
    EM_AREAALARM_TRIGGER_TYPE_UNKNOWN(0, "未知"),
/**
通道打开
*/
    EM_AREAALARM_TRIGGER_TYPE_ALARM(1, "通道打开"),
/**
通道防拆
*/
    EM_AREAALARM_TRIGGER_TYPE_TAMPER(2, "通道防拆"),
/**
通道遮挡
*/
    EM_AREAALARM_TRIGGER_TYPE_MASK(3, "通道遮挡"),
/**
通道防短
*/
    EM_AREAALARM_TRIGGER_TYPE_SHORT(4, "通道防短");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_AREAALARM_TRIGGER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_AREAALARM_TRIGGER_TYPE enumType : EM_AREAALARM_TRIGGER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_AREAALARM_TRIGGER_TYPE enumType : EM_AREAALARM_TRIGGER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_AREAALARM_TRIGGER_TYPE getEnum(int value) {
        for (EM_AREAALARM_TRIGGER_TYPE e : EM_AREAALARM_TRIGGER_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_AREAALARM_TRIGGER_TYPE.EM_AREAALARM_TRIGGER_TYPE_UNKNOWN;
    }
}

