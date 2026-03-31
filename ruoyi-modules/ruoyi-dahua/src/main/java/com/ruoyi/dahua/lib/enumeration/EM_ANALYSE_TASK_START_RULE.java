package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  智能任务启动规则 
* @date 2022/06/28 16:19:15
*/
public enum EM_ANALYSE_TASK_START_RULE {
/**
立刻启动
*/
    EM_ANALYSE_TASK_START_NOW(0, "立刻启动"),
/**
稍候手动启动
*/
    EM_ANALYSE_TASK_START_LATER(1, "稍候手动启动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ANALYSE_TASK_START_RULE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ANALYSE_TASK_START_RULE enumType : EM_ANALYSE_TASK_START_RULE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ANALYSE_TASK_START_RULE enumType : EM_ANALYSE_TASK_START_RULE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ANALYSE_TASK_START_RULE getEnum(int value) {
        for (EM_ANALYSE_TASK_START_RULE e : EM_ANALYSE_TASK_START_RULE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ANALYSE_TASK_START_RULE.EM_ANALYSE_TASK_START_NOW;
    }
}

