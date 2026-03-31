package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 车牌属性
 * @date 2023/01/10 19:44:59
 */
public enum EM_PLATE_ATTRIBUTE {
    /**
     * 未知
     */
    EM_PLATE_ATTRIBUTE_UNKNOWN(0, "未知"),
    /**
     * 正常
     */
    EM_PLATE_ATTRIBUTE_NORMAL(1, "正常"),
    /**
     * 无牌
     */
    EM_PLATE_ATTRIBUTE_NO_PLATE(2, "无牌"),
    /**
     * 部分遮挡/污损
     */
    EM_PLATE_ATTRIBUTE_PARTIAL_OCCLUSION(3, "部分遮挡/污损"),
    /**
     * 完全遮挡/污损
     */
    EM_PLATE_ATTRIBUTE_FULL_OCCLUSION(4, "完全遮挡/污损");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PLATE_ATTRIBUTE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PLATE_ATTRIBUTE enumType : EM_PLATE_ATTRIBUTE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PLATE_ATTRIBUTE enumType : EM_PLATE_ATTRIBUTE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_PLATE_ATTRIBUTE getEnum(int value) {
        for (EM_PLATE_ATTRIBUTE e : EM_PLATE_ATTRIBUTE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_PLATE_ATTRIBUTE.EM_PLATE_ATTRIBUTE_UNKNOWN;
    }
}

