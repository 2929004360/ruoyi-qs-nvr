package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  图片类型 
* @date 2022/06/28 19:24:08
*/
public enum EM_PIC_TYPE {
/**
未知
*/
    EM_PIC_TYPE_UNKNOWN(-1, "未知"),
/**
算法处理后的图片
*/
    EM_PIC_TYPE_ALGORITHMICALLY_PROCESSED(0, "算法处理后的图片");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_PIC_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_PIC_TYPE enumType : EM_PIC_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_PIC_TYPE enumType : EM_PIC_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_PIC_TYPE getEnum(int value) {
        for (EM_PIC_TYPE e : EM_PIC_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_PIC_TYPE.EM_PIC_TYPE_UNKNOWN;
    }
}

