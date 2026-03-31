package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  防区类型 
* @date 2022/07/19 19:31:27
*/
public enum EM_DEFENCE_AREA_TYPE {
/**
未知
*/
    EM_DEFENCE_AREA_TYPE_UNKNOWN(0, "未知"),
/**
立即防区
*/
    EM_DEFENCE_AREA_TYPE_INTIME(1, "立即防区"),
/**
延时防区
*/
    EM_DEFENCE_AREA_TYPE_DELAY(2, "延时防区"),
/**
延时防区2
*/
    EM_DEFENCE_AREA_TYPE_DELAY2(3, "延时防区2"),
/**
跟随防区
*/
    EM_DEFENCE_AREA_TYPE_FOLLOW(4, "跟随防区"),
/**
退出防区
*/
    EM_DEFENCE_AREA_TYPE_EXITEND(5, "退出防区"),
/**
24小时防区
*/
    EM_DEFENCE_AREA_TYPE_FULLDAY(6, "24小时防区"),
/**
火警防区
*/
    EM_DEFENCE_AREA_TYPE_FIRE(7, "火警防区"),
/**
恐慌防区
*/
    EM_DEFENCE_AREA_TYPE_PANIC(8, "恐慌防区"),
/**
匪警防区
*/
    EM_DEFENCE_AREA_TYPE_ROBBERY(9, "匪警防区"),
/**
医疗紧急防区
*/
    EM_DEFENCE_AREA_TYPE_MEDICAL(10, "医疗紧急防区"),
/**
key防区
*/
    EM_DEFENCE_AREA_TYPE_KEY(11, "key防区");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DEFENCE_AREA_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DEFENCE_AREA_TYPE enumType : EM_DEFENCE_AREA_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DEFENCE_AREA_TYPE enumType : EM_DEFENCE_AREA_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DEFENCE_AREA_TYPE getEnum(int value) {
        for (EM_DEFENCE_AREA_TYPE e : EM_DEFENCE_AREA_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DEFENCE_AREA_TYPE.EM_DEFENCE_AREA_TYPE_UNKNOWN;
    }
}

