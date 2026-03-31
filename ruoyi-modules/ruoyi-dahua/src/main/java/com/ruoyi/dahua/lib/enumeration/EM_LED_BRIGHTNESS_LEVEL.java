package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  led灯亮度 
* @date 2022/08/31 14:44:16
*/
public enum EM_LED_BRIGHTNESS_LEVEL {
/**
未知
*/
    EM_LED_BRIGHTNESS_UNKNOWN(-1, "未知"),
/**
关闭
*/
    EM_LED_BRIGHTNESS_CLOSED(0, "关闭"),
/**
亮度最低
*/
    EM_LED_BRIGHTNESS_LOW(1, "亮度最低"),
/**
亮度适中
*/
    EM_LED_BRIGHTNESS_MEDIUM(2, "亮度适中"),
/**
亮度最高
*/
    EM_LED_BRIGHTNESS_HIGH(3, "亮度最高");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_LED_BRIGHTNESS_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_LED_BRIGHTNESS_LEVEL enumType : EM_LED_BRIGHTNESS_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_LED_BRIGHTNESS_LEVEL enumType : EM_LED_BRIGHTNESS_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_LED_BRIGHTNESS_LEVEL getEnum(int value) {
        for (EM_LED_BRIGHTNESS_LEVEL e : EM_LED_BRIGHTNESS_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_LED_BRIGHTNESS_LEVEL.EM_LED_BRIGHTNESS_UNKNOWN;
    }
}

