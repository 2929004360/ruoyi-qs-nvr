package com.ruoyi.dahua.lib.enumeration;

/**
 * @author 291189
 * @version 1.0
 * @description 厨师装备状态
 * @date 2021/7/26 14:24
 */
public enum EM_SMART_KITCHEN_CLOTHES_STATE {
    EM_SMART_KITCHEN_CLOTHES_STATE_UNKNOWN(0, "未知"),// 未知
    EM_SMART_KITCHEN_CLOTHES_STATE_LACK(1, "没有"),// 没有
    EM_SMART_KITCHEN_CLOTHES_STATE_HAS(2, "有");// 有

    private final int value;
    private final String note;

    EM_SMART_KITCHEN_CLOTHES_STATE(int givenValue, String note) {
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
        for (EM_SMART_KITCHEN_CLOTHES_STATE enumType : EM_SMART_KITCHEN_CLOTHES_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SMART_KITCHEN_CLOTHES_STATE enumType : EM_SMART_KITCHEN_CLOTHES_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SMART_KITCHEN_CLOTHES_STATE getEnum(int value) {
        for (EM_SMART_KITCHEN_CLOTHES_STATE e : EM_SMART_KITCHEN_CLOTHES_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SMART_KITCHEN_CLOTHES_STATE.EM_SMART_KITCHEN_CLOTHES_STATE_UNKNOWN;
    }
}

