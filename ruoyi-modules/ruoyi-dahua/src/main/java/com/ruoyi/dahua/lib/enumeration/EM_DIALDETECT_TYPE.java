package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  仪表类型 
* @date 2022/06/30 11:30:53
*/
public enum EM_DIALDETECT_TYPE {
/**
未知
*/
    EM_DIALDETECT_TYPE_UNKNOWN(0, "未知"),
/**
压板检测
*/
    EM_DIALDETECT_TYPE_PLATEN(1, "压板检测"),
/**
刀闸检测
*/
    EM_DIALDETECT_TYPE_KNIFE(2, "刀闸检测"),
/**
指针表计检测
*/
    EM_DIALDETECT_TYPE_POINTERMETER(3, "指针表计检测"),
/**
油位表计
*/
    EM_DIALDETECT_TYPE_OILMETER(4, "油位表计"),
/**
数码管字符检测
*/
    EM_DIALDETECT_TYPE_LED(5, "数码管字符检测"),
/**
液晶屏字符检测
*/
    EM_DIALDETECT_TYPE_LCD(6, "液晶屏字符检测"),
/**
指示灯检测
*/
    EM_DIALDETECT_TYPE_LIGHT(7, "指示灯检测"),
/**
旋转开关检测
*/
    EM_DIALDETECT_TYPE_SWITCH(8, "旋转开关检测"),
/**
呼吸器检测
*/
    EM_DIALDETECT_TYPE_PESPIRATOR(9, "呼吸器检测"),
/**
字符指示器检测
*/
    EM_DIALDETECT_TYPE_CHARLNDICTOR(10, "字符指示器检测"),
/**
指针指示器检测
*/
    EM_DIALDETECT_TYPE_POINTERLNDICTOR(11, "指针指示器检测"),
/**
液位计检测
*/
    EM_DIALDETECT_TYPE_OILLEVEL(12, "液位计检测"),
/**
拨码盘字符检测
*/
    EM_DIALDETECT_TYPE_DIALSWTICH(13, "拨码盘字符检测");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_DIALDETECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_DIALDETECT_TYPE enumType : EM_DIALDETECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_DIALDETECT_TYPE enumType : EM_DIALDETECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_DIALDETECT_TYPE getEnum(int value) {
        for (EM_DIALDETECT_TYPE e : EM_DIALDETECT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DIALDETECT_TYPE.EM_DIALDETECT_TYPE_UNKNOWN;
    }
}

