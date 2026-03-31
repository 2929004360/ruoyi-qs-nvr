package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  锁状态 
* @date 2022/08/10 11:46:30
*/
public enum EM_VAULT_LOCKSTATE {
/**
未知
*/
    EM_VAULT_LOCKSTATE_UNKNOWN(0, "未知"),
/**
关锁
*/
    EM_VAULT_LOCKSTATE_ISCLOSED(1, "关锁"),
/**
开锁
*/
    EM_VAULT_LOCKSTATE_ISOPENED(2, "开锁");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_VAULT_LOCKSTATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_VAULT_LOCKSTATE enumType : EM_VAULT_LOCKSTATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_VAULT_LOCKSTATE enumType : EM_VAULT_LOCKSTATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_VAULT_LOCKSTATE getEnum(int value) {
        for (EM_VAULT_LOCKSTATE e : EM_VAULT_LOCKSTATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_VAULT_LOCKSTATE.EM_VAULT_LOCKSTATE_UNKNOWN;
    }
}

