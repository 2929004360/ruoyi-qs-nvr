package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 触发事件的类型
 * @since ： Created in 2022/03/08 15:25
 */

public enum EM_HELMET_EVENT_TYPE {
    /**
     * 未知
     */
    EM_UNKNOWN_TYPE(0, "未知"),
    /**
     * 未佩戴安全帽
     */
    EM_NOT_WEAR_HELMET(1, "未佩戴安全帽");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HELMET_EVENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HELMET_EVENT_TYPE enumType : EM_HELMET_EVENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HELMET_EVENT_TYPE enumType : EM_HELMET_EVENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_HELMET_EVENT_TYPE getEnum(int value) {
        for (EM_HELMET_EVENT_TYPE e : EM_HELMET_EVENT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_HELMET_EVENT_TYPE.EM_UNKNOWN_TYPE;
    }
}

