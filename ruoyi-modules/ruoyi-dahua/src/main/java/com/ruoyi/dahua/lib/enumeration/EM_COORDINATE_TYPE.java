package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 坐标系类型
 * @date 2022/11/21 17:53:12
 */
public enum EM_COORDINATE_TYPE {
    /**
     * 未知
     */
    EM_COORDINATE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 绝对坐标
     */
    EM_COORDINATE_TYPE_ABSOLUTE(1, "绝对坐标"),
    /**
     * 8192坐标
     */
    EM_COORDINATE_TYPE_8192(2, "8192坐标");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_COORDINATE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_COORDINATE_TYPE enumType : EM_COORDINATE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_COORDINATE_TYPE enumType : EM_COORDINATE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_COORDINATE_TYPE getEnum(int value) {
        for (EM_COORDINATE_TYPE e : EM_COORDINATE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_COORDINATE_TYPE.EM_COORDINATE_TYPE_UNKNOWN;
    }
}

