package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 设备连接状态
 * @date 2022/04/20 10:16:56
 */
public enum EM_THINGS_CONNECT_STATE {
    /**
     * 未知
     */
    EM_THINGS_CONNECT_STATE_UNKNOWN(-1, "未知"),
    /**
     * 连接断开
     */
    EM_THINGS_CONNECT_STATE_DISCONNECT(0, "连接断开"),
    /**
     * 已连接
     */
    EM_THINGS_CONNECT_STATE_CONNECT(1, "已连接");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_CONNECT_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_CONNECT_STATE enumType : EM_THINGS_CONNECT_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_CONNECT_STATE enumType : EM_THINGS_CONNECT_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

    public static EM_THINGS_CONNECT_STATE getEnum(int value) {
        for (EM_THINGS_CONNECT_STATE e : EM_THINGS_CONNECT_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_CONNECT_STATE.EM_THINGS_CONNECT_STATE_UNKNOWN;
    }
}

