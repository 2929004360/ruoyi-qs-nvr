package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  事件报警类型 
* @date 2022/08/22 16:50:06
*/
public enum EM_WATER_DETECTION_ALARM_TYPE {
/**
未知
*/
    EM_WATER_DETECTION_ALARM_TYPE_UNKNOWN(0, "未知"),
/**
水质类别
*/
    EM_WATER_DETECTION_ALARM_TYPE_QUALITY(1, "水质类别"),
/**
PH
*/
    EM_WATER_DETECTION_ALARM_TYPE_PH(2, "PH"),
/**
浊度值
*/
    EM_WATER_DETECTION_ALARM_TYPE_NTU(3, "浊度值"),
/**
氨氮值
*/
    EM_WATER_DETECTION_ALARM_TYPE_NH3_N(4, "氨氮值"),
/**
总氮值
*/
    EM_WATER_DETECTION_ALARM_TYPE_TN(5, "总氮值"),
/**
透明度值
*/
    EM_WATER_DETECTION_ALARM_TYPE_SD(6, "透明度值"),
/**
化学需氧量
*/
    EM_WATER_DETECTION_ALARM_TYPE_COD(7, "化学需氧量"),
/**
亚硝酸盐氮
*/
    EM_WATER_DETECTION_ALARM_TYPE_NN(8, "亚硝酸盐氮"),
/**
溶解氧
*/
    EM_WATER_DETECTION_ALARM_TYPE_DO(9, "溶解氧"),
/**
叶绿素a
*/
    EM_WATER_DETECTION_ALARM_TYPE_CHL_A(10, "叶绿素a"),
/**
总磷
*/
    EM_WATER_DETECTION_ALARM_TYPE_TP(11, "总磷"),
/**
高锰酸盐指数范围
*/
    EM_WATER_DETECTION_ALARM_TYPE_CODMN(12, "高锰酸盐指数范围"),
/**
悬浮物
*/
    EM_WATER_DETECTION_ALARM_TYPE_SS(13, "悬浮物"),
/**
五日生化需氧量
*/
    EM_WATER_DETECTION_ALARM_TYPE_BOD_5(14, "五日生化需氧量"),
/**
硝酸盐
*/
    EM_WATER_DETECTION_ALARM_TYPE_NO3_N(15, "硝酸盐"),
/**
富营养状况指数
*/
    EM_WATER_DETECTION_ALARM_TYPE_TSI(16, "富营养状况指数"),
/**
黑臭等级
*/
    EM_WATER_DETECTION_ALARM_TYPE_SMELLY_LEVEL(17, "黑臭等级");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_WATER_DETECTION_ALARM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_WATER_DETECTION_ALARM_TYPE enumType : EM_WATER_DETECTION_ALARM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_WATER_DETECTION_ALARM_TYPE enumType : EM_WATER_DETECTION_ALARM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_WATER_DETECTION_ALARM_TYPE getEnum(int value) {
        for (EM_WATER_DETECTION_ALARM_TYPE e : EM_WATER_DETECTION_ALARM_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WATER_DETECTION_ALARM_TYPE.EM_WATER_DETECTION_ALARM_TYPE_UNKNOWN;
    }
}

