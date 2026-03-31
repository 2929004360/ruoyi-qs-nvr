package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  滤光片类型 
* @date 2022/09/22 17:28:03
*/
public enum EM_NAS_STATE_TYPE {
/**
未知
*/
    EM_NAS_STATE_TYPE_UNKNOWN(0, "未知"),
/**
正常
*/
    EM_NAS_STATE_TYPE_ACTIVE(1, "正常"),
/**
LV异常
*/
    EM_NAS_STATE_TYPE_LVFAILED(2, "LV异常"),
/**
非活跃
*/
    EM_NAS_STATE_TYPE_INACTIVE(3, "非活跃"),
/**
LV所属的VG异常
*/
    EM_NAS_STATE_TYPE_VGFAILED(4, "LV所属的VG异常"),
/**
服务未开启
*/
    EM_NAS_STATE_TYPE_NOTSTARTED(5, "服务未开启");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NAS_STATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NAS_STATE_TYPE enumType : EM_NAS_STATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NAS_STATE_TYPE enumType : EM_NAS_STATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NAS_STATE_TYPE getEnum(int value) {
        for (EM_NAS_STATE_TYPE e : EM_NAS_STATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NAS_STATE_TYPE.EM_NAS_STATE_TYPE_UNKNOWN;
    }
}

