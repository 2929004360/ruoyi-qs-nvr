package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  透雾模式枚举 
* @origin autoTool
* @date 2023/11/30 15:50:58
*/
public enum CFG_DEFOG_MODE {
	/**
     * 未知方式
     */
    EM_DEFOG_MODE_UNKNOW(0, "未知方式"),
	/**
     * 关闭
     */
    EM_DEFOG_MODE_OFF(1, "关闭"),
	/**
     * 自动
     */
    EM_DEFOG_MODE_AUTO(2, "自动"),
	/**
     * 手动
     */
    EM_DEFOG_MODE_MANA(3, "手动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

CFG_DEFOG_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (CFG_DEFOG_MODE enumType : CFG_DEFOG_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (CFG_DEFOG_MODE enumType : CFG_DEFOG_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static CFG_DEFOG_MODE getEnum(int value) {
        for (CFG_DEFOG_MODE e : CFG_DEFOG_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return CFG_DEFOG_MODE.EM_DEFOG_MODE_UNKNOW;
    }
}

