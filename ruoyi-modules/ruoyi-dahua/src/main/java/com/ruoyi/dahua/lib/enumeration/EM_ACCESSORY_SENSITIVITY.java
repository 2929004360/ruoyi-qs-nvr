package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  灵敏度 
* @date 2022/08/31 14:44:15
*/
public enum EM_ACCESSORY_SENSITIVITY {
/**
未知
*/
    EM_ACCESSORY_SENSITIVITY_UNKNOWN(0, "未知"),
/**
低灵敏度
*/
    EM_ACCESSORY_SENSITIVITY_LOW(1, "低灵敏度"),
/**
中灵敏度
*/
    EM_ACCESSORY_SENSITIVITY_MEDIUM(2, "中灵敏度"),
/**
高灵敏度
*/
    EM_ACCESSORY_SENSITIVITY_HIGH(3, "高灵敏度");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ACCESSORY_SENSITIVITY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ACCESSORY_SENSITIVITY enumType : EM_ACCESSORY_SENSITIVITY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ACCESSORY_SENSITIVITY enumType : EM_ACCESSORY_SENSITIVITY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ACCESSORY_SENSITIVITY getEnum(int value) {
        for (EM_ACCESSORY_SENSITIVITY e : EM_ACCESSORY_SENSITIVITY.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ACCESSORY_SENSITIVITY.EM_ACCESSORY_SENSITIVITY_UNKNOWN;
    }
}

