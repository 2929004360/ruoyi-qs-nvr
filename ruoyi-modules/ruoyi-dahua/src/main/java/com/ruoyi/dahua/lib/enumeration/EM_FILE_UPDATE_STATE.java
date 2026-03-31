package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  文件上传状态 
* @date 2022/09/22 19:11:10
*/
public enum EM_FILE_UPDATE_STATE {
/**
未知
*/
    EM_FILE_UPDATE_STATE_UNKNOWN(0, "未知"),
/**
正在上传
*/
    EM_FILE_UPDATE_STATE_UPLOADING(1, "正在上传"),
/**
上传中断
*/
    EM_FILE_UPDATE_STATE_PAUSED(2, "上传中断"),
/**
上传成功
*/
    EM_FILE_UPDATE_STATE_SUCCEEDED(3, "上传成功");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_FILE_UPDATE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_FILE_UPDATE_STATE enumType : EM_FILE_UPDATE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_FILE_UPDATE_STATE enumType : EM_FILE_UPDATE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_FILE_UPDATE_STATE getEnum(int value) {
        for (EM_FILE_UPDATE_STATE e : EM_FILE_UPDATE_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_FILE_UPDATE_STATE.EM_FILE_UPDATE_STATE_UNKNOWN;
    }
}

