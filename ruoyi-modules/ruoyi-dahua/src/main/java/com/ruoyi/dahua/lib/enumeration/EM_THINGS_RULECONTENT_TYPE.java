package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 场景规则类型
 * @date 2022/04/20 10:50:21
 */
public enum EM_THINGS_RULECONTENT_TYPE {
    /**
     * 未知
     */
    EM_THINGS_RULECONTENT_TYPE_UNKNOWN(0, "未知"),
    /**
     * IFTTT类型
     */
    EM_THINGS_RULECONTENT_TYPE_IFTTT(1, "IFTTT类型");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_THINGS_RULECONTENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_THINGS_RULECONTENT_TYPE enumType : EM_THINGS_RULECONTENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_THINGS_RULECONTENT_TYPE enumType : EM_THINGS_RULECONTENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_THINGS_RULECONTENT_TYPE getEnum(int value) {
        for (EM_THINGS_RULECONTENT_TYPE e : EM_THINGS_RULECONTENT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_THINGS_RULECONTENT_TYPE.EM_THINGS_RULECONTENT_TYPE_UNKNOWN;
    }
}

