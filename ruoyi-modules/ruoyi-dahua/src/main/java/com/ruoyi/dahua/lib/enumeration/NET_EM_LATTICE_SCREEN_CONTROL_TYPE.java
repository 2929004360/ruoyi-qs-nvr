package com.ruoyi.dahua.lib.enumeration;
/** 逻辑屏控制方式 */
public enum NET_EM_LATTICE_SCREEN_CONTROL_TYPE {
    EM_LATTICE_SCREEN_CONTROL_TYPE_UNKNOWN(-1, "未知"),// 未知
    EM_LATTICE_SCREEN_CONTROL_TYPE_CAMERA_CONTROL(0, "相机控制"),// 相机控制
    EM_LATTICE_SCREEN_CONTROL_TYPE_PLATFORM_CONTROL(1, "平台控制");// 平台控制

    private int value;
    private String note;

    NET_EM_LATTICE_SCREEN_CONTROL_TYPE(int givenValue, String note) {
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
        for (NET_EM_LATTICE_SCREEN_CONTROL_TYPE enumType : NET_EM_LATTICE_SCREEN_CONTROL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_LATTICE_SCREEN_CONTROL_TYPE enumType : NET_EM_LATTICE_SCREEN_CONTROL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

