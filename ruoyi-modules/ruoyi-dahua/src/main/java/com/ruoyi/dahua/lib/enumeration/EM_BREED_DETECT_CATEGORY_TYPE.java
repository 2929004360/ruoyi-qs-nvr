package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description 智慧养殖检测目标子类型
* @date 2022/04/15 15:29:30
*/
public enum EM_BREED_DETECT_CATEGORY_TYPE {
/**
未知
*/
    EM_BREED_DETECT_CATEGORY_UNKNOWN(0, "未知"),
/**
猪
*/
    EM_BREED_DETECT_CATEGORY_PIG(1, "猪");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_BREED_DETECT_CATEGORY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_BREED_DETECT_CATEGORY_TYPE enumType : EM_BREED_DETECT_CATEGORY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_BREED_DETECT_CATEGORY_TYPE enumType : EM_BREED_DETECT_CATEGORY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_BREED_DETECT_CATEGORY_TYPE getEnum(int value) {
        for (EM_BREED_DETECT_CATEGORY_TYPE e : EM_BREED_DETECT_CATEGORY_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_BREED_DETECT_CATEGORY_TYPE.EM_BREED_DETECT_CATEGORY_UNKNOWN;
    }
}

