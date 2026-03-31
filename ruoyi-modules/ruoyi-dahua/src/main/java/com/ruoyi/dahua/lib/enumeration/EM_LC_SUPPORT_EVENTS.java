package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 支持灯光联动的非智能事件
 * @origin autoTool
 * @date 2023/10/19 17:27:53
 */
public enum EM_LC_SUPPORT_EVENTS {
    /**
     * 未知
     */
    EM_LC_SUPPORT_EVENTS_UNKNOWN(0, "未知"),
    /**
     * 动态检测
     */
    EM_LC_SUPPORT_EVENTS_MOTIONDETECT(1, "动态检测"),
    /**
     * 遮挡
     */
    EM_LC_SUPPORT_EVENTS_MASK(2, "遮挡"),
    /**
     * 报警
     */
    EM_LC_SUPPORT_EVENTS_ALARM(3, "报警"),
    /**
     * 所有事件
     */
    EM_LC_SUPPORT_EVENTS_ALL(4, "所有事件");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LC_SUPPORT_EVENTS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LC_SUPPORT_EVENTS enumType : EM_LC_SUPPORT_EVENTS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LC_SUPPORT_EVENTS enumType : EM_LC_SUPPORT_EVENTS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_LC_SUPPORT_EVENTS getEnum(int value) {
        for (EM_LC_SUPPORT_EVENTS e : EM_LC_SUPPORT_EVENTS.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return EM_LC_SUPPORT_EVENTS.EM_LC_SUPPORT_EVENTS_UNKNOWN;
    }
}

