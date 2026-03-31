package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  运动移动检测结果类型 
* @date 2022/08/03 14:26:52
*/
public enum EM_PTZ_MOVING_RESULT_TYPE {
/**
未知
*/
    EM_PTZ_MOVING_UNKNOWN(0, "未知"),
/**
检测正常
*/
    EM_PTZ_MOVING_NORMAL(1, "检测正常"),
/**
不按指令运动
*/
    EM_PTZ_MOVING_NOTOBEY(2, "不按指令运动"),
/**
不能运动
*/
    EM_PTZ_MOVING_NOTMOVE(3, "不能运动"),
/**
未做检测
*/
    EM_PTZ_MOVING_NOTDETECT(4, "未做检测");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_PTZ_MOVING_RESULT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_PTZ_MOVING_RESULT_TYPE enumType : EM_PTZ_MOVING_RESULT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_PTZ_MOVING_RESULT_TYPE enumType : EM_PTZ_MOVING_RESULT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_PTZ_MOVING_RESULT_TYPE getEnum(int value) {
        for (EM_PTZ_MOVING_RESULT_TYPE e : EM_PTZ_MOVING_RESULT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_PTZ_MOVING_RESULT_TYPE.EM_PTZ_MOVING_UNKNOWN;
    }
}

