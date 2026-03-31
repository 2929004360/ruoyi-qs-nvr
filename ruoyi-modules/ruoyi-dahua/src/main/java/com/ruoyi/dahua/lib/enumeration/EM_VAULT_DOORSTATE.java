package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  门状态 
* @date 2022/08/10 11:46:30
*/
public enum EM_VAULT_DOORSTATE {
/**
未知
*/
    EM_VAULT_DOORSTATE_UNKNOWN(0, "未知"),
/**
关门
*/
    EM_VAULT_DOORSTATE_ISCLOSED(1, "关门"),
/**
开门
*/
    EM_VAULT_DOORSTATE_ISOPENED(2, "开门");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_VAULT_DOORSTATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_VAULT_DOORSTATE enumType : EM_VAULT_DOORSTATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_VAULT_DOORSTATE enumType : EM_VAULT_DOORSTATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_VAULT_DOORSTATE getEnum(int value) {
        for (EM_VAULT_DOORSTATE e : EM_VAULT_DOORSTATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_VAULT_DOORSTATE.EM_VAULT_DOORSTATE_UNKNOWN;
    }
}

