package com.ruoyi.dahua.lib.enumeration;

/**
 * 雨量状态
*/
public enum EM_RAIN_FALL_STATUS
{
    /**
     * 未知
    */
    EM_RAIN_FALL_STATUS_UNKNOWN(0, "未知"),
    /**
     * 正常
    */
    EM_RAIN_FALL_STATUS_NORMAL(1, "正常"),
    /**
     * 高于上限阈值
    */
    EM_RAIN_FALL_STATUS_HIGH(2, "高于上限阈值");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RAIN_FALL_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RAIN_FALL_STATUS enumType : EM_RAIN_FALL_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RAIN_FALL_STATUS enumType : EM_RAIN_FALL_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

