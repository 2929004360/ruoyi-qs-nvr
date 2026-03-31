package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 表示人数越上限类型
 * @date 2022/07/04 10:11:30
 */
public enum EM_NUMBER_STAT_TYPE {
    /**
     * 未知
     */
    EM_NUMBERSTAT_TYPE_UNKNOWN(0, "未知"),
    /**
     * 进入
     */
    EM_NUMBERSTAT_TYPE_ENTEROVER(1, "进入"),
    /**
     * 退出
     */
    EM_NUMBERSTAT_TYPE_EXITOVER(2, "退出"),
    /**
     * 在里面
     */
    EM_NUMBERSTAT_TYPE_INSIDEOVER(3, "在里面"),
    /**
     * 经过
     */
    EM_NUMBERSTAT_TYPE_PASSOVER(4, "经过");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NUMBER_STAT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NUMBER_STAT_TYPE enumType : EM_NUMBER_STAT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NUMBER_STAT_TYPE enumType : EM_NUMBER_STAT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_NUMBER_STAT_TYPE getEnum(int value) {
        for (EM_NUMBER_STAT_TYPE e : EM_NUMBER_STAT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NUMBER_STAT_TYPE.EM_NUMBERSTAT_TYPE_UNKNOWN;
    }
}

