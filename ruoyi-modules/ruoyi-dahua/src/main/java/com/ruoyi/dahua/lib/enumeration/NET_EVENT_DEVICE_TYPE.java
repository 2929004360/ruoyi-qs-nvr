package com.ruoyi.dahua.lib.enumeration;

public enum NET_EVENT_DEVICE_TYPE {
    NET_EVENT_DEVICE_TYPE_UNKOWN(0, "未知"),
    NET_EVENT_DEVICE_TYPE_CARD(1, "卡片");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EVENT_DEVICE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EVENT_DEVICE_TYPE enumType : NET_EVENT_DEVICE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EVENT_DEVICE_TYPE enumType : NET_EVENT_DEVICE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EVENT_DEVICE_TYPE getEnum(int value) {
        for (NET_EVENT_DEVICE_TYPE e : NET_EVENT_DEVICE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EVENT_DEVICE_TYPE.NET_EVENT_DEVICE_TYPE_UNKOWN;
    }
}

