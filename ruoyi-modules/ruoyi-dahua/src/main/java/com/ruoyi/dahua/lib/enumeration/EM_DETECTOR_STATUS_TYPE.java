package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  探测器状态类型 
* @date 2022/08/31 14:44:15
*/
public enum EM_DETECTOR_STATUS_TYPE {
/**
未知
*/
    EM_DETECTOR_STATUS_UNKNOWN(-1, "未知"),
/**
启用所有功能
*/
    EM_DETECTOR_STATUS_ALLFUNCT_ENABLE(0, "启用所有功能"),
/**
禁用防拆功能
*/
    EM_DETECTOR_STATUS_ANTITAMPER_DISABLE(1, "禁用防拆功能"),
/**
禁用所有功能
*/
    EM_DETECTOR_STATUS_ALLFUNCT_DISABLE(2, "禁用所有功能");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DETECTOR_STATUS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DETECTOR_STATUS_TYPE enumType : EM_DETECTOR_STATUS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DETECTOR_STATUS_TYPE enumType : EM_DETECTOR_STATUS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_DETECTOR_STATUS_TYPE getEnum(int value) {
        for (EM_DETECTOR_STATUS_TYPE e : EM_DETECTOR_STATUS_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DETECTOR_STATUS_TYPE.EM_DETECTOR_STATUS_UNKNOWN;
    }
}

