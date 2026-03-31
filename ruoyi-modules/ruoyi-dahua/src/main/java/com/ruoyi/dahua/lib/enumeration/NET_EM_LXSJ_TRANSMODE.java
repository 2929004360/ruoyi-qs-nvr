package com.ruoyi.dahua.lib.enumeration;

public enum NET_EM_LXSJ_TRANSMODE {
    /**未知*/
    NET_EM_LXSJ_TRANSMODE_UNKNOWN(-1, "未知"),
    /**TCP方式*/
    NET_EM_LXSJ_TRANSMODE_TCP(0, "TCP方式"),
    /**UDP方式*/
    NET_EM_LXSJ_TRANSMODE_UD(1, "UDP方式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_LXSJ_TRANSMODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_LXSJ_TRANSMODE enumType : NET_EM_LXSJ_TRANSMODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_LXSJ_TRANSMODE enumType : NET_EM_LXSJ_TRANSMODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_LXSJ_TRANSMODE getEnum(int value) {
        for (NET_EM_LXSJ_TRANSMODE e : NET_EM_LXSJ_TRANSMODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_LXSJ_TRANSMODE.NET_EM_LXSJ_TRANSMODE_UNKNOWN;
    }
}

