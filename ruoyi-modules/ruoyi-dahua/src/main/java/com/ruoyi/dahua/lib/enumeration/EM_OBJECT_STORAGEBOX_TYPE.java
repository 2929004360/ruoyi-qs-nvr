package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  非机动车后备箱 
* @origin autoTool
* @date 2023/02/14 20:11:39
*/
public enum EM_OBJECT_STORAGEBOX_TYPE {
/**
未知
*/
    EM_OBJECT_STORAGEBOX_UNKNOWN(0, "未知"),
/**
无后备箱
*/
    EM_OBJECT_STORAGEBOX_NO(1, "无后备箱"),
/**
自带箱
*/
    EM_OBJECT_STORAGEBOX_OWNBOX(2, "自带箱"),
/**
自装箱
*/
    EM_OBJECT_STORAGEBOX_SELFPACK(3, "自装箱"),
/**
自装箱和自带箱都有
*/
    EM_OBJECT_STORAGEBOX_ALL(4, "自装箱和自带箱都有");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_OBJECT_STORAGEBOX_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_OBJECT_STORAGEBOX_TYPE enumType : EM_OBJECT_STORAGEBOX_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_OBJECT_STORAGEBOX_TYPE enumType : EM_OBJECT_STORAGEBOX_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_OBJECT_STORAGEBOX_TYPE getEnum(int value) {
        for (EM_OBJECT_STORAGEBOX_TYPE e : EM_OBJECT_STORAGEBOX_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_OBJECT_STORAGEBOX_TYPE.EM_OBJECT_STORAGEBOX_UNKNOWN;
    }
}

