package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  车牌颜色 
* @date 2022/05/30 14:16:15
*/
public enum EM_NET_PLATE_COLOR_TYPE {
/**
其他颜色
*/
    NET_PLATE_COLOR_OTHER(0, "其他颜色"),
/**
蓝色  Blue 
*/
    NET_PLATE_COLOR_BLUE(1, "蓝色  Blue "),
/**
黄色  Yellow 
*/
    NET_PLATE_COLOR_YELLOW(2, "黄色  Yellow "),
/**
白色  White 
*/
    NET_PLATE_COLOR_WHITE(3, "白色  White "),
/**
黑色  Black 
*/
    NET_PLATE_COLOR_BLACK(4, "黑色  Black "),
/**
黄底黑字  YellowbottomBlackText 
*/
    NET_PLATE_COLOR_YELLOW_BOTTOM_BLACK_TEXT(5, "黄底黑字  YellowbottomBlackText "),
/**
蓝底白字  BluebottomWhiteText 
*/
    NET_PLATE_COLOR_BLUE_BOTTOM_WHITE_TEXT(6, "蓝底白字  BluebottomWhiteText "),
/**
黑底白字  BlackBottomWhiteText 
*/
    NET_PLATE_COLOR_BLACK_BOTTOM_WHITE_TEXT(7, "黑底白字  BlackBottomWhiteText "),
/**
渐变绿  ShadowGreen 
*/
    NET_PLATE_COLOR_SHADOW_GREEN(8, "渐变绿  ShadowGreen "),
/**
黄绿双拼  YellowGreen 
*/
    NET_PLATE_COLOR_YELLOW_GREEN(9, "黄绿双拼  YellowGreen ");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_PLATE_COLOR_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_PLATE_COLOR_TYPE enumType : EM_NET_PLATE_COLOR_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_PLATE_COLOR_TYPE enumType : EM_NET_PLATE_COLOR_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_PLATE_COLOR_TYPE getEnum(int value) {
        for (EM_NET_PLATE_COLOR_TYPE e : EM_NET_PLATE_COLOR_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_PLATE_COLOR_TYPE.NET_PLATE_COLOR_OTHER;
    }
}

