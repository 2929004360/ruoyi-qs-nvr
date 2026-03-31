package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  统计方向查询条件 
* @date 2023/02/06 16:07:27
*/
public enum EM_STAT_DIRECTION {
/**
未知
*/
    EM_STAT_DIRECTION_UNKNOWN(0, "未知"),
/**
左到右
*/
    EM_STAT_DIRECTION_LEFTTORIGHT(1, "左到右"),
/**
右到左
*/
    EM_STAT_DIRECTION_RIGHTTOLEFT(2, "右到左"),
/**
全部
*/
    EM_STAT_DIRECTION_BOTH(3, "全部");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_STAT_DIRECTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_STAT_DIRECTION enumType : EM_STAT_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_STAT_DIRECTION enumType : EM_STAT_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_STAT_DIRECTION getEnum(int value) {
        for (EM_STAT_DIRECTION e : EM_STAT_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_STAT_DIRECTION.EM_STAT_DIRECTION_UNKNOWN;
    }
}

