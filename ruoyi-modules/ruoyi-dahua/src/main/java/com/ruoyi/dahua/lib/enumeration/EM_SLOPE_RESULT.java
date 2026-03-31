package com.ruoyi.dahua.lib.enumeration;

/**
 * 斜率报警采样类型
*/
public enum EM_SLOPE_RESULT
{
    /**
     * 未知
    */
    EM_SLOPE_RESULT_UNKNOWN(0, "未知"),
    /**
     * 平均值
    */
    EM_SLOPE_RESULT_AVER(1, "平均值"),
    /**
     * 最大值
    */
    EM_SLOPE_RESULT_MAX(2, "最大值"),
    /**
     * 最小值
    */
    EM_SLOPE_RESULT_MIN(3, "最小值");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SLOPE_RESULT(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SLOPE_RESULT enumType : EM_SLOPE_RESULT.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SLOPE_RESULT enumType : EM_SLOPE_RESULT.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

