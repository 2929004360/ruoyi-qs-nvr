package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  预案类型 
* @origin autoTool
* @date 2023/03/06 15:54:24
*/
public enum EM_SAVE_COLLECTION_TYPE {
/**
未知
*/
    EM_SAVE_COLLECTION_UNKNOWN(0, "未知"),
/**
视频预案
*/
    EM_SAVE_COLLECTION_VIDEO(1, "视频预案"),
/**
附加预案
*/
    EM_SAVE_COLLECTION_ADDITIONAL(2, "附加预案");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_SAVE_COLLECTION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_SAVE_COLLECTION_TYPE enumType : EM_SAVE_COLLECTION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_SAVE_COLLECTION_TYPE enumType : EM_SAVE_COLLECTION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_SAVE_COLLECTION_TYPE getEnum(int value) {
        for (EM_SAVE_COLLECTION_TYPE e : EM_SAVE_COLLECTION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SAVE_COLLECTION_TYPE.EM_SAVE_COLLECTION_UNKNOWN;
    }
}

