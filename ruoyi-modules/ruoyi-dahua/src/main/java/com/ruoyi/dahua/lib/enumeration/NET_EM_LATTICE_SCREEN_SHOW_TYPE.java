package com.ruoyi.dahua.lib.enumeration;
/**
 *逻辑屏显示方式
 */
public enum NET_EM_LATTICE_SCREEN_SHOW_TYPE {
    EM_LATTICE_SCREEN_SHOW_TYPE_UNKNOWN(-1, "未知"),// 未知
    EM_LATTICE_SCREEN_SHOW_TYPE_WORD_CONTROL(0, "字段控制显示"),// 字段控制显示
    EM_LATTICE_SCREEN_SHOW_TYPE_TRUSTEESHIP_CONTROL(1, "托管显示");// 托管显示（完全由平台自定义显示）

    private int value;
    private String note;

    NET_EM_LATTICE_SCREEN_SHOW_TYPE(int givenValue, String note) {
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
        for (NET_EM_LATTICE_SCREEN_SHOW_TYPE enumType : NET_EM_LATTICE_SCREEN_SHOW_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_LATTICE_SCREEN_SHOW_TYPE enumType : NET_EM_LATTICE_SCREEN_SHOW_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

