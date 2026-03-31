package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 订阅类型
 * @date 2022/04/20 10:16:56
 */
public enum EM_ATTACH_TOPICS {
    /**
     * 表示订阅所有属性和事件
     */
    EM_ATTACH_TOPICS_ALL(0, " *  表示订阅所有属性和事件"),
    /**
     * Props  表示订阅设备属性上报
     */
    EM_ATTACH_TOPICS_PROPS(1, " Props  表示订阅设备属性上报"),
    /**
     * Events  标识订阅设备事件上报
     */
    EM_ATTACH_TOPICS_EVENTS(2, " Events  标识订阅设备事件上报");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_ATTACH_TOPICS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_ATTACH_TOPICS enumType : EM_ATTACH_TOPICS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_ATTACH_TOPICS enumType : EM_ATTACH_TOPICS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_ATTACH_TOPICS getEnum(int value) {
        for (EM_ATTACH_TOPICS e : EM_ATTACH_TOPICS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_ATTACH_TOPICS.EM_ATTACH_TOPICS_ALL;
    }
}

