package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 报警状态
 * @date 2022/04/20 10:50:24
 */
public enum EM_THINGS_CURRENTSTATUSGET_STATUS {
    /**
     * 未知
     */
    EM_THINGS_CURRENTSTATUSGET_STATUS_UNKNOWN(-1, "未知"),
    /**
     * 正常
     */
    EM_THINGS_CURRENTSTATUSGET_STATUS_NORMAL(0, "正常"),
    /**
     * 预警
     */
    EM_THINGS_CURRENTSTATUSGET_STATUS_EARLY(1, "预警"),
    /**
     * 报警
     */
    EM_THINGS_CURRENTSTATUSGET_STATUS_ALARM(2, "报警");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_CURRENTSTATUSGET_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_CURRENTSTATUSGET_STATUS enumType : EM_THINGS_CURRENTSTATUSGET_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_CURRENTSTATUSGET_STATUS enumType : EM_THINGS_CURRENTSTATUSGET_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

    public static EM_THINGS_CURRENTSTATUSGET_STATUS getEnum(int value) {
        for (EM_THINGS_CURRENTSTATUSGET_STATUS e : EM_THINGS_CURRENTSTATUSGET_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_CURRENTSTATUSGET_STATUS.EM_THINGS_CURRENTSTATUSGET_STATUS_UNKNOWN;
    }
}

