package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 421657
* @description  支持的模式 
* @origin autoTool
* @date 2023/10/19 17:27:50
*/
public enum EM_LC_MODE {
	/**
     * 未知
     */
    EM_LC_MODE_UNKNOWN(0, "未知"),
	/**
     * 手动
     */
    EM_LC_MODE_MANUAL(1, "手动"),
	/**
     * 倍率优先
     */
    EM_LC_MODE_ZOOMPRIO(2, "倍率优先"),
	/**
     * 定时
     */
    EM_LC_MODE_TIMING(3, "定时"),
	/**
     * 自动
     */
    EM_LC_MODE_AUTO(4, "自动"),
	/**
     * 关闭灯光
     */
    EM_LC_MODE_OFF(5, "关闭灯光"),
	/**
     * 支持多种灯光
     */
    EM_LC_MODE_EXCLUSIVEMANUAL(6, "支持多种灯光"),
	/**
     * 智能灯光
     */
    EM_LC_MODE_SMARTLIGHT(7, "智能灯光"),
	/**
     * 事件联动
     */
    EM_LC_MODE_LINKING(8, "事件联动"),
	/**
     * 光敏
     */
    EM_LC_MODE_DUSKTODAW(9, "光敏");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_LC_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_LC_MODE enumType : EM_LC_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_LC_MODE enumType : EM_LC_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_LC_MODE getEnum(int value) {
        for (EM_LC_MODE e : EM_LC_MODE.values()) {
            if (e.getValue() == value){
                return e;
            }
        }
        return EM_LC_MODE.EM_LC_MODE_UNKNOWN;
    }
}

