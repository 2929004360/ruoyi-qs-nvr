package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  大气光模式枚举 
* @origin autoTool
* @date 2023/11/30 15:50:59
*/
public enum CFG_LIGHT_INTENSITY_MODE {
	/**
     * 未知方式
     */
    EM_LIGHT_INTENSITY_MODE_UNKNOW(0, "未知方式"),
	/**
     * 自动
     */
    EM_LIGHT_INTENSITY_MODE_AUTO(1, "自动"),
	/**
     * 手动
     */
    EM_LIGHT_INTENSITY_MODE_MANUL(2, "手动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

CFG_LIGHT_INTENSITY_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (CFG_LIGHT_INTENSITY_MODE enumType : CFG_LIGHT_INTENSITY_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (CFG_LIGHT_INTENSITY_MODE enumType : CFG_LIGHT_INTENSITY_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static CFG_LIGHT_INTENSITY_MODE getEnum(int value) {
        for (CFG_LIGHT_INTENSITY_MODE e : CFG_LIGHT_INTENSITY_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return CFG_LIGHT_INTENSITY_MODE.EM_LIGHT_INTENSITY_MODE_UNKNOW;
    }
}

