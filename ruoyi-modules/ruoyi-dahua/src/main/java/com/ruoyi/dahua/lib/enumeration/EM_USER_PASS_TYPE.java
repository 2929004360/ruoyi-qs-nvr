package com.ruoyi.dahua.lib.enumeration;
/**
 * @author ： 291189
 * @description ： 进出方向
 * @since ： Created in 2022/05/26
 */
public enum EM_USER_PASS_TYPE {
    EM_USER_PASS_TYPE_UNKNOWN(0, "未知"),
    EM_USER_PASS_TYPE_ENTRY(1, "进门"),// 进门
    EM_USER_PASS_TYPE_EXIT(2, "出门");// 出门

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_USER_PASS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_USER_PASS_TYPE enumType : EM_USER_PASS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_USER_PASS_TYPE enumType : EM_USER_PASS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_USER_PASS_TYPE getEnum(int value) {
        for (EM_USER_PASS_TYPE e : EM_USER_PASS_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_USER_PASS_TYPE.EM_USER_PASS_TYPE_UNKNOWN;
    }
}

