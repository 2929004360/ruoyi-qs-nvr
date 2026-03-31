package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 电子通行证状态（添加）
* @date 2022/03/08 11:25:17
*/
public enum EM_ALLOW_PERMIT_FLAG {
/**
未知
*/
    EM_ALLOW_PERMIT_FLAG_UNKNOWN(0, "未知"),
/**
没有电子通行证
*/
    EM_ALLOW_PERMIT_FLAG_NO(1, "没有电子通行证"),
/**
电子通行证有效
*/
    EM_ALLOW_PERMIT_FLAG_EFFECTIVE(2, "电子通行证有效"),
/**
电子通行证无效
*/
    EM_ALLOW_PERMIT_FLAG_INVALID(3, "电子通行证无效");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ALLOW_PERMIT_FLAG(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ALLOW_PERMIT_FLAG enumType : EM_ALLOW_PERMIT_FLAG.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ALLOW_PERMIT_FLAG enumType : EM_ALLOW_PERMIT_FLAG.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ALLOW_PERMIT_FLAG getEnum(int value) {
        for (EM_ALLOW_PERMIT_FLAG e : EM_ALLOW_PERMIT_FLAG.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ALLOW_PERMIT_FLAG.EM_ALLOW_PERMIT_FLAG_UNKNOWN;
    }
}

