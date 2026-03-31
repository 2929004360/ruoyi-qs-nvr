package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  SSA对比度调节模式,当背光模式为SSA时有效 
* @origin autoTool
* @date 2023/11/30 16:22:27
*/
public enum EM_INTENSITY_MODE {
	/**
     * 未知模式
     */
    EM_INTENSITY_UNKNOW(0, "未知模式"),
	/**
     * 关闭
     */
    EM_INTENSITY_DISABLE(1, "关闭"),
	/**
     * 自动
     */
    EM_INTENSITY_AUTO(2, "自动"),
	/**
     * 手动
     */
    EM_INTENSITY_MANUAL(3, "手动");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_INTENSITY_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_INTENSITY_MODE enumType : EM_INTENSITY_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_INTENSITY_MODE enumType : EM_INTENSITY_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_INTENSITY_MODE getEnum(int value) {
        for (EM_INTENSITY_MODE e : EM_INTENSITY_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_INTENSITY_MODE.EM_INTENSITY_UNKNOW;
    }
}

