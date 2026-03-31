package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 设备程序所在的雷达区域
 * @date 2022/08/04 10:13:31
 */
public enum EM_RADAR_GETCAPS_AREASUB_TYPE {
    /**
     * 未知
     */
    EM_RADAR_GETCAPS_AREASUB_TYPE_UNKNOWN(0, "未知"),
    /**
     * 扇形
     */
    EM_RADAR_GETCAPS_AREASUB_TYPE_SECTOR(1, "扇形"),
    /**
     * 椭圆
     */
    EM_RADAR_GETCAPS_AREASUB_TYPE_ELLIPSE(2, "椭圆");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RADAR_GETCAPS_AREASUB_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RADAR_GETCAPS_AREASUB_TYPE enumType : EM_RADAR_GETCAPS_AREASUB_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RADAR_GETCAPS_AREASUB_TYPE enumType : EM_RADAR_GETCAPS_AREASUB_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_RADAR_GETCAPS_AREASUB_TYPE getEnum(int value) {
        for (EM_RADAR_GETCAPS_AREASUB_TYPE e : EM_RADAR_GETCAPS_AREASUB_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RADAR_GETCAPS_AREASUB_TYPE.EM_RADAR_GETCAPS_AREASUB_TYPE_UNKNOWN;
    }
}

