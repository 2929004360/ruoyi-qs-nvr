package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  背光补偿模式，当背光模式为Backlight时有效 
* @origin autoTool
* @date 2023/11/30 16:22:26
*/
public enum EM_BACKLIGHT_MODE {
	/**
     * 未知模式
     */
    EM_BACKLIGHT_UNKONW(0, "未知模式"),
	/**
     * 默认模式
     */
    EM_BACKLIGHT_DEFAULT(1, "默认模式"),
	/**
     * 自定义区域模式
     */
    EM_BACKLIGHT_REGION(2, "自定义区域模式");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_BACKLIGHT_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_BACKLIGHT_MODE enumType : EM_BACKLIGHT_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_BACKLIGHT_MODE enumType : EM_BACKLIGHT_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_BACKLIGHT_MODE getEnum(int value) {
        for (EM_BACKLIGHT_MODE e : EM_BACKLIGHT_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_BACKLIGHT_MODE.EM_BACKLIGHT_UNKONW;
    }
}

