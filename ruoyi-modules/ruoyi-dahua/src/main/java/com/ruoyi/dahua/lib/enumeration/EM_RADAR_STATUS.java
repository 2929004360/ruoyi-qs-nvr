package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 雷达状态
 * @date 2022/08/04 10:13:32
 */
public enum EM_RADAR_STATUS {
    /**
     * 未知
     */
    EM_RADAR_STATUS_UNKNOWN(0, "未知"),
    /**
     * 正常
     */
    EM_RADAR_STATUS_NORMAL(1, "正常"),
    /**
     * 异常
     */
    EM_RADAR_STATUS_ABNORMAL(2, "异常");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RADAR_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RADAR_STATUS enumType : EM_RADAR_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RADAR_STATUS enumType : EM_RADAR_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_RADAR_STATUS getEnum(int value) {
        for (EM_RADAR_STATUS e : EM_RADAR_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_RADAR_STATUS.EM_RADAR_STATUS_UNKNOWN;
    }
}

