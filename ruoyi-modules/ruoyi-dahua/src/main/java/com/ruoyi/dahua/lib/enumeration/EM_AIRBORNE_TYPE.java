package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  挂空悬浮物具体类型 
* @date 2022/06/28 19:44:54
*/
public enum EM_AIRBORNE_TYPE {
/**
未知
*/
    EM_AIRBORNE_TYPE_UNKNOWN(0, "未知"),
/**
塑料袋
*/
    EM_AIRBORNE_TYPE_PLASTICBAG(1, "塑料袋"),
/**
织物
*/
    EM_AIRBORNE_TYPE_TEXTILE(2, "织物"),
/**
风筝
*/
    EM_AIRBORNE_TYPE_KITE(3, "风筝");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_AIRBORNE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_AIRBORNE_TYPE enumType : EM_AIRBORNE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_AIRBORNE_TYPE enumType : EM_AIRBORNE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_AIRBORNE_TYPE getEnum(int value) {
        for (EM_AIRBORNE_TYPE e : EM_AIRBORNE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_AIRBORNE_TYPE.EM_AIRBORNE_TYPE_UNKNOWN;
    }
}

