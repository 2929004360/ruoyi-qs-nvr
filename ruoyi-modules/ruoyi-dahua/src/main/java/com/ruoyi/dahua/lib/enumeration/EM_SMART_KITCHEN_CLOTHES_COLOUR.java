package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @version 1.0
 * @description 厨师服颜色
 * @date 2021/7/26 15:24
 */
public enum EM_SMART_KITCHEN_CLOTHES_COLOUR {
    EM_SMART_KITCHEN_CLOTHES_COLOUR_UNKNOWN(0, "未知"),// 未知
    EM_SMART_KITCHEN_CLOTHES_COLOUR_RED(1, "红"),// 红
    EM_SMART_KITCHEN_CLOTHES_COLOUR_BLACK(2, "黑"),// 黑
    EM_SMART_KITCHEN_CLOTHES_COLOUR_WHITE(3, "白");// 白

    private final int value;
    private final String note;

    EM_SMART_KITCHEN_CLOTHES_COLOUR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SMART_KITCHEN_CLOTHES_COLOUR enumType : EM_SMART_KITCHEN_CLOTHES_COLOUR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SMART_KITCHEN_CLOTHES_COLOUR enumType : EM_SMART_KITCHEN_CLOTHES_COLOUR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SMART_KITCHEN_CLOTHES_COLOUR getEnum(int value) {
        for (EM_SMART_KITCHEN_CLOTHES_COLOUR e : EM_SMART_KITCHEN_CLOTHES_COLOUR.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SMART_KITCHEN_CLOTHES_COLOUR.EM_SMART_KITCHEN_CLOTHES_COLOUR_UNKNOWN;
    }
}

