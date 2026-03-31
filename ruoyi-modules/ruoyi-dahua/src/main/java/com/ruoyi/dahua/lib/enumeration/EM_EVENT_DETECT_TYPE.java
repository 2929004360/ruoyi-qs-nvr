package com.ruoyi.dahua.lib.enumeration;

public enum EM_EVENT_DETECT_TYPE {
    /**未知*/
    EM_EVENT_DETECT_TYPE_UNKNOWN(-1, "未知"),
    /**小于等于阈值报警*/
    EM_EVENT_DETECT_TYPE_LESS_OR_EQUAL(0, "小于等于阈值报警"),
    /**大于等于阈值报警*/
    EM_EVENT_DETECT_TYPE_GREATER_OR_EQUAL(1, "大于等于阈值报警"),
    /**等于阀值报警*/
    EM_EVENT_DETECT_TYPE_EQUAL(2, "等于阀值报警"),
    /**不等于阀值报警*/
    EM_EVENT_DETECT_TYPE_NOEQUAL(3, "不等于阀值报警"),
    /**人数变化报警*/
    EM_EVENT_DETECT_TYPE_CHANGE(4, "人数变化报警"),
    /**大于阈值报警*/
    EM_EVENT_DETECT_TYPE_GREATER(5, "大于阈值报警"),
    /**小于阈值报警*/
    EM_EVENT_DETECT_TYPE_LESS(6, "小于阈值报警"),
    /**区间内报警，检测人数在区间范围内报警，包括边界值。区间值对应PersonNum字段*/
    EM_EVENT_DETECT_TYPE_IN_INTERVAL(7, "区间内报警，检测人数在区间范围内报警，包括边界值。区间值对应PersonNum字段"),
    /**区间外报警，检测人数在区间范围外报警，不包括边界值。区间值对应PersonNum字段*/
    EM_EVENT_DETECT_TYPE_OUT_INTERVAL(8, "区间外报警，检测人数在区间范围外报警，不包括边界值。区间值对应PersonNum字段");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_EVENT_DETECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_EVENT_DETECT_TYPE enumType : EM_EVENT_DETECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_EVENT_DETECT_TYPE enumType : EM_EVENT_DETECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -2;
    }

    public static EM_EVENT_DETECT_TYPE getEnum(int value) {
        for (EM_EVENT_DETECT_TYPE e : EM_EVENT_DETECT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_EVENT_DETECT_TYPE.EM_EVENT_DETECT_TYPE_UNKNOWN;
    }
}

