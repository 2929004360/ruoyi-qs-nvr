package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  呼叫电梯方式 
* @date 2022/10/19 14:01:07
*/
public enum EM_CALLLIFT_ACTION {
/**
未知
*/
    EM_CALLLIFT_ACTION_UNKNOWN(-1, "未知"),
/**
用户通过对讲分机打开单元门锁，同时呼叫电梯到单元门处
*/
    EM_CALLLIFT_ACTION_USER_UNLOCK_CALL(0, "用户通过对讲分机打开单元门锁，同时呼叫电梯到单元门处"),
/**
用户呼叫电梯到用户所在楼层
*/
    EM_CALLLIFT_ACTION_USER_DIRECT_CALL(1, "用户呼叫电梯到用户所在楼层"),
/**
用户呼叫电梯经由某层到某层
*/
    EM_CALLLIFT_ACTION_USER_REDIRECT_CALL(2, "用户呼叫电梯经由某层到某层"),
/**
单元门开锁，同时呼叫电梯到单元门处
*/
    EM_CALLLIFT_ACTION_DIRECT_UNLOCK_CALL(3, "单元门开锁，同时呼叫电梯到单元门处");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CALLLIFT_ACTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CALLLIFT_ACTION enumType : EM_CALLLIFT_ACTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CALLLIFT_ACTION enumType : EM_CALLLIFT_ACTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_CALLLIFT_ACTION getEnum(int value) {
        for (EM_CALLLIFT_ACTION e : EM_CALLLIFT_ACTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CALLLIFT_ACTION.EM_CALLLIFT_ACTION_UNKNOWN;
    }
}

