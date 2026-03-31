package com.ruoyi.dahua.lib.enumeration;

/**
 * 媒体能力集类型
*/
public enum NET_MEDIA_CAP_TYPE
{
    /**
     * 镜头传感器信息
    */
    NET_MEDIA_CAP_TYPE_SENSORINFO(0, "镜头传感器信息");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_MEDIA_CAP_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_MEDIA_CAP_TYPE enumType : NET_MEDIA_CAP_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_MEDIA_CAP_TYPE enumType : NET_MEDIA_CAP_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

