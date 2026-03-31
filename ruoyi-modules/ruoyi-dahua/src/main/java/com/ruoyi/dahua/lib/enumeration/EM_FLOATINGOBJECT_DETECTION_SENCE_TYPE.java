package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  漂浮物检测场景 
* @date 2022/05/09 11:40:43
*/
public enum EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE {
/**
未知
*/
    EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE_UNKNOWN(0, "未知"),
/**
普通漂浮物检测
*/
    EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE_NOMAL(1, "普通漂浮物检测"),
/**
泡沫检测
*/
    EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE_BUBBLE(2, "泡沫检测");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE enumType : EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE enumType : EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE getEnum(int value) {
        for (EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE e : EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE.EM_FLOATINGOBJECT_DETECTION_SENCE_TYPE_UNKNOWN;
    }
}

