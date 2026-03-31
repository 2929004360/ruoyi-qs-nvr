package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  呼吸器状态 
* @date 2022/06/28 19:44:55
*/
public enum EM_RESPIRATOR_STATE {
/**
未知
*/
    EM_RESPIRATOR_STATE_UNKNOWN(0, "未知"),
/**
正常
*/
    EM_RESPIRATOR_STATE_NORMAL(1, "正常"),
/**
硅胶变色
*/
    EM_RESPIRATOR_STATE_SILICA_GEL_COLOR_CHANGE(2, "硅胶变色"),
/**
硅胶桶破损
*/
    EM_RESPIRATOR_STATE_SILICA_GEL_BARREL_BROKEN(3, "硅胶桶破损"),
/**
油位异常
*/
    EM_RESPIRATOR_STATE_ABNORMAL_OIL_LEVEL(4, "油位异常");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_RESPIRATOR_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_RESPIRATOR_STATE enumType : EM_RESPIRATOR_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_RESPIRATOR_STATE enumType : EM_RESPIRATOR_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_RESPIRATOR_STATE getEnum(int value) {
        for (EM_RESPIRATOR_STATE e : EM_RESPIRATOR_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RESPIRATOR_STATE.EM_RESPIRATOR_STATE_UNKNOWN;
    }
}

