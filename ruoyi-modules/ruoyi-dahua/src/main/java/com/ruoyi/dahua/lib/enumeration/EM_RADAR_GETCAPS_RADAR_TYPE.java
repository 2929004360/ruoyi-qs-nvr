package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 雷达类型
 * @date 2022/08/04 10:13:32
 */
public enum EM_RADAR_GETCAPS_RADAR_TYPE {
    /**
     * 未知
     */
    EM_RADAR_GETCAPS_RADAR_TYPE_UNKNOWN(0, "未知"),
    /**
     * 单IP雷达
     */
    EM_RADAR_GETCAPS_RADAR_TYPE1(1, "单IP雷达"),
    /**
     * 雷达控制盒
     */
    EM_RADAR_GETCAPS_RADAR_TYPE2(2, "雷达控制盒"),
    /**
     * 端站一体机
     */
    EM_RADAR_GETCAPS_RADAR_TYPE3(3, "端站一体机"),
    /**
     * 室内雷达
     */
    EM_RADAR_GETCAPS_RADAR_TYPE4(4, "室内雷达"),
    /**
     * 体征雷达
     */
    EM_RADAR_GETCAPS_RADAR_TYPE5(5, "体征雷达");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RADAR_GETCAPS_RADAR_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RADAR_GETCAPS_RADAR_TYPE enumType : EM_RADAR_GETCAPS_RADAR_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RADAR_GETCAPS_RADAR_TYPE enumType : EM_RADAR_GETCAPS_RADAR_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_RADAR_GETCAPS_RADAR_TYPE getEnum(int value) {
        for (EM_RADAR_GETCAPS_RADAR_TYPE e : EM_RADAR_GETCAPS_RADAR_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RADAR_GETCAPS_RADAR_TYPE.EM_RADAR_GETCAPS_RADAR_TYPE_UNKNOWN;
    }
}

