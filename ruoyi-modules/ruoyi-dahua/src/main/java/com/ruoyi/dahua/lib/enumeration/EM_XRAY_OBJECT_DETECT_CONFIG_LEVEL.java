package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  X光机物品检测配置等级 
* @date 2022/12/01 16:22:06
*/
public enum EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL {
/**
未知
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_UNKNWON(-1, "未知"),
/**
高
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_HIGH(0, "高"),
/**
中
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_MID(1, "中"),
/**
普通
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_NORMAL(2, "普通"),
/**
自定义1
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_CUSTOM1(3, "自定义1"),
/**
自定义2
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_CUSTOM2(4, "自定义2"),
/**
自定义3
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_CUSTOM3(5, "自定义3"),
/**
自定义4
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_CUSTOM4(6, "自定义4"),
/**
自定义5
*/
    EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_CUSTOM5(7, "自定义5");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL enumType : EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL enumType : EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL getEnum(int value) {
        for (EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL e : EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL.EM_XRAY_OBJECT_DETECT_CONFIG_LEVEL_UNKNWON;
    }
}

