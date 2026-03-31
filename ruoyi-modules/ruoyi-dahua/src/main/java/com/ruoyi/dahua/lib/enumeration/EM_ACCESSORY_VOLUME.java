package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  设备布撤防时音量 
* @date 2022/08/31 14:44:16
*/
public enum EM_ACCESSORY_VOLUME {
/**
未知
*/
    EM_ACCESSORY_VOLUME_UNKNOWN(0, "未知"),
/**
低音量
*/
    EM_ACCESSORY_VOLUME_LOW(1, "低音量"),
/**
中音量
*/
    EM_ACCESSORY_VOLUME_MEDIUM(2, "中音量"),
/**
高音量
*/
    EM_ACCESSORY_VOLUME_HIGH(3, "高音量");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_ACCESSORY_VOLUME(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_ACCESSORY_VOLUME enumType : EM_ACCESSORY_VOLUME.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_ACCESSORY_VOLUME enumType : EM_ACCESSORY_VOLUME.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_ACCESSORY_VOLUME getEnum(int value) {
        for (EM_ACCESSORY_VOLUME e : EM_ACCESSORY_VOLUME.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ACCESSORY_VOLUME.EM_ACCESSORY_VOLUME_UNKNOWN;
    }
}

