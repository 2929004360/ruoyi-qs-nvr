package com.ruoyi.dahua.lib.enumeration;

/**
 * 镜头传感器类型
*/
public enum NET_CAMERA_SENSOR
{
    /**
     * 一般（可见光）
    */
    NET_CAMERA_SENSOR_NORMAL(0, "一般（可见光）"),
    /**
     * Lepton 热成像
    */
    NET_CAMERA_SENSOR_LEPTON(1, "Lepton 热成像"),
    /**
     * Tau 热成像
    */
    NET_CAMERA_SENSOR_TAU(2, "Tau 热成像");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_CAMERA_SENSOR(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_CAMERA_SENSOR enumType : NET_CAMERA_SENSOR.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_CAMERA_SENSOR enumType : NET_CAMERA_SENSOR.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

