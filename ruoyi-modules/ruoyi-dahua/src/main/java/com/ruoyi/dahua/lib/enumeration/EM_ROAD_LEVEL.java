package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  路面状况等级 
* @origin autoTool
* @date 2023/06/16 10:02:52
*/
public enum EM_ROAD_LEVEL {
/**
未知
*/
    EM_ROAD_UNKNOWN_LEVEL(-1, "未知"),
/**
0: 纵向裂纹
*/
    EM_ROAD_ZERO_LEVEL(0, "0: 纵向裂纹"),
/**
1: 横向裂纹
*/
    EM_ROAD_ONE_LEVEL(1, "1: 横向裂纹"),
/**
2: 鳄鱼裂纹
*/
    EM_ROAD_TWO_LEVEL(2, "2: 鳄鱼裂纹"),
/**
3: 槽纹
*/
    EM_ROAD_THREE_LEVEL(3, "3: 槽纹"),
/**
4: 车辙纹
*/
    EM_ROAD_FOUR_LEVEL(4, "4: 车辙纹"),
/**
5: 纵向修复纹
*/
    EM_ROAD_FIVE_LEVEL(5, "5: 纵向修复纹"),
/**
6: 横向修复纹
*/
    EM_ROAD_SIX_LEVEL(6, "6: 横向修复纹");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ROAD_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ROAD_LEVEL enumType : EM_ROAD_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ROAD_LEVEL enumType : EM_ROAD_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_ROAD_LEVEL getEnum(int value) {
        for (EM_ROAD_LEVEL e : EM_ROAD_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ROAD_LEVEL.EM_ROAD_UNKNOWN_LEVEL;
    }
}

