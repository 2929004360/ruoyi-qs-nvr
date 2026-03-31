package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 自定义抓图类型
* @date 2022/03/31 14:04:19
*/
public enum EM_CUSTOM_SNAP_TYPE {
/**
未知
*/
    EM_CUSTOM_SNAP_UNKNOWN(0, "未知"),
/**
猪体温检测, 对应结构体 NET_PIG_TEMPERATURE_INFO
*/
    EM_CUSTOM_SNAP_PIG_TEMPERATURE(1, "猪体温检测, 对应结构体 NET_PIG_TEMPERATURE_INFO");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_CUSTOM_SNAP_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_CUSTOM_SNAP_TYPE enumType : EM_CUSTOM_SNAP_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_CUSTOM_SNAP_TYPE enumType : EM_CUSTOM_SNAP_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_CUSTOM_SNAP_TYPE getEnum(int value) {
        for (EM_CUSTOM_SNAP_TYPE e : EM_CUSTOM_SNAP_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CUSTOM_SNAP_TYPE.EM_CUSTOM_SNAP_UNKNOWN;
    }
}

