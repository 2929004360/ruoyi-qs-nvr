package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  输入类型 
* @date 2022/08/31 14:44:16
*/
public enum EM_ACCESSORY_INPUT_TYPE {
/**
未知
*/
    EM_ACCESSORY_INPUT_UNKNOWN(-1, "未知"),
/**
防拆
*/
    EM_ACCESSORY_INPUT_TAMPER(0, "防拆"),
/**
报警输入
*/
    EM_ACCESSORY_INPUT_SENSOR(1, "报警输入");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ACCESSORY_INPUT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ACCESSORY_INPUT_TYPE enumType : EM_ACCESSORY_INPUT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ACCESSORY_INPUT_TYPE enumType : EM_ACCESSORY_INPUT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_ACCESSORY_INPUT_TYPE getEnum(int value) {
        for (EM_ACCESSORY_INPUT_TYPE e : EM_ACCESSORY_INPUT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ACCESSORY_INPUT_TYPE.EM_ACCESSORY_INPUT_UNKNOWN;
    }
}

