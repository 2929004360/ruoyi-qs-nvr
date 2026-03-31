package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 定时触发类型内容表达式类型
 * @date 2022/04/20 11:28:15
 */
public enum EM_THINGS_TRIGGER_TIMER_CORN_TYPE {
    /**
     * 未知
     */
    EM_THINGS_TRIGGER_TIMER_CORN_TYPE_UNKNOWN(0, "未知"),
    /**
     * quartz_cron  表达式结构：$second $minute $hour $dayofmonth $month $dayofweek $year
     */
    EM_THINGS_TRIGGER_TIMER_CORN_TYPE_QUARTZCORN(1, " quartz_cron  表达式结构：$second $minute $hour $dayofmonth $month $dayofweek $year"),
    /**
     * linux  表达式结构：$minute $hour $day of month $month $dayofweek
     */
    EM_THINGS_TRIGGER_TIMER_CORN_TYPE_LINUX(2, " linux  表达式结构：$minute $hour $day of month $month $dayofweek");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_TRIGGER_TIMER_CORN_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_TRIGGER_TIMER_CORN_TYPE enumType : EM_THINGS_TRIGGER_TIMER_CORN_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_TRIGGER_TIMER_CORN_TYPE enumType : EM_THINGS_TRIGGER_TIMER_CORN_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_THINGS_TRIGGER_TIMER_CORN_TYPE getEnum(int value) {
        for (EM_THINGS_TRIGGER_TIMER_CORN_TYPE e : EM_THINGS_TRIGGER_TIMER_CORN_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_TRIGGER_TIMER_CORN_TYPE.EM_THINGS_TRIGGER_TIMER_CORN_TYPE_UNKNOWN;
    }
}

