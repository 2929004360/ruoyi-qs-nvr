package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  雨棚（伞）类型 
* @origin autoTool
* @date 2023/02/14 20:11:39
*/
public enum EM_RAIN_SHED_TYPE {
/**
无
*/
    EM_RAIN_SHED_TYPE_NONE(-1, "无"),
/**
不撑伞
*/
    EM_RAIN_SHED_TYPE_NO_UMBRELLA(0, "不撑伞"),
/**
人非手撑伞
*/
    EM_RAIN_SHED_TYPE_NOT_PEOPLE_HOLD(1, "人非手撑伞"),
/**
未知
*/
    EM_RAIN_SHED_TYPE_UNKOWN(2, "未知"),
/**
非机动车装载雨伞
*/
    EM_RAIN_SHED_TYPE_NO_MOTOR_UMBRELLA(3, "非机动车装载雨伞"),
/**
非机动车装载雨棚
*/
    EM_RAIN_SHED_TYPE_NO_MOTOR_CANOPY(4, "非机动车装载雨棚");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_RAIN_SHED_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_RAIN_SHED_TYPE enumType : EM_RAIN_SHED_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_RAIN_SHED_TYPE enumType : EM_RAIN_SHED_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

public static EM_RAIN_SHED_TYPE getEnum(int value) {
        for (EM_RAIN_SHED_TYPE e : EM_RAIN_SHED_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RAIN_SHED_TYPE.EM_RAIN_SHED_TYPE_NONE;
    }
}

