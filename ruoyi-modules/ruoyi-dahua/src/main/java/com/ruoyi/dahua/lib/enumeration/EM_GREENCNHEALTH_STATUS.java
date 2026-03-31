package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 人员健康状态
* @date 2022/03/08 11:25:17
*/
public enum EM_GREENCNHEALTH_STATUS {
/**
未知
*/
    EM_GREENCNHEALTH_STATUS_UNKNOWN(0, "未知"),
/**
绿码
*/
    EM_GREENCNHEALTH_STATUS_GREEN(1, "绿码"),
/**
黄码
*/
    EM_GREENCNHEALTH_STATUS_YELLOW(2, "黄码"),
/**
红码
*/
    EM_GREENCNHEALTH_STATUS_RED(3, "红码"),
/**
无效
*/
    EM_GREENCNHEALTH_STATUS_INVALID(4, "无效");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_GREENCNHEALTH_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_GREENCNHEALTH_STATUS enumType : EM_GREENCNHEALTH_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_GREENCNHEALTH_STATUS enumType : EM_GREENCNHEALTH_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_GREENCNHEALTH_STATUS getEnum(int value) {
        for (EM_GREENCNHEALTH_STATUS e : EM_GREENCNHEALTH_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_GREENCNHEALTH_STATUS.EM_GREENCNHEALTH_STATUS_UNKNOWN;
    }
}

