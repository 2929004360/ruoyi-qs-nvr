package com.ruoyi.dahua.lib.enumeration;

/**
 * 事件级别枚举
 *
 * @author 47040
 * @since Created in 2021/5/13 19:49
 */
public enum EM_EVENT_LEVEL {
    /**
     * 未知
     */
    EM_EVENT_LEVEL_UNKNOWN(-1, "未知"),
    /**
     * 提示
     */
    EM_EVENT_LEVEL_HINT(0, "提示"),
    /**
     * 普通
     */
    EM_EVENT_LEVEL_GENERAL(1, "普通"),
    /**
     * 警告
     */
    EM_EVENT_LEVEL_WARNING(2, "警告");

    private final int value;
    private final String note;

    EM_EVENT_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_EVENT_LEVEL enumType : EM_EVENT_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_EVENT_LEVEL enumType : EM_EVENT_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_EVENT_LEVEL getEnum(int value) {
        for (EM_EVENT_LEVEL e : EM_EVENT_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_EVENT_LEVEL.EM_EVENT_LEVEL_UNKNOWN;
    }
}

