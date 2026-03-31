package com.ruoyi.dahua.lib.enumeration;

/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 11:01
 *  人脸表情
 */

public enum EM_SECURITYGATE_EXPRESSION_TYPE {
    EM_SECURITYGATE_EXPRESSION_UNKNOWN(-1, "未知"),// 未知
    EM_SECURITYGATE_EXPRESSION_UNDEFINE(0, "未识别"),// 未识别
    EM_SECURITYGATE_EXPRESSION_ANGER(1, "生气"),// 生气
    EM_SECURITYGATE_EXPRESSION_SAD(2, "悲伤"),// 悲伤
    EM_SECURITYGATE_EXPRESSION_DISGUST(3, "厌恶"),// 厌恶
    EM_SECURITYGATE_EXPRESSION_FEAR(4, "恐惧"),// 恐惧
    EM_SECURITYGATE_EXPRESSION_SURPRISED(5, "诧异"),// 诧异
    EM_SECURITYGATE_EXPRESSION_CALM(6, "平静"),// 平静
    EM_SECURITYGATE_EXPRESSION_SMILE(7, "微笑"),// 微笑
    EM_SECURITYGATE_EXPRESSION_LAUGH(8, "大笑"),// 大笑
    EM_SECURITYGATE_EXPRESSION_HAPPY(9, "高兴"),// 高兴
    EM_SECURITYGATE_EXPRESSION_CONFUSE(10, "困惑"),// 困惑
    EM_SECURITYGATE_EXPRESSION_SCREAM(11, "尖叫");// 尖叫

    private final int value;
    private final String note;

    EM_SECURITYGATE_EXPRESSION_TYPE(int givenValue, String note) {
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
        for (EM_SECURITYGATE_EXPRESSION_TYPE enumType : EM_SECURITYGATE_EXPRESSION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_EXPRESSION_TYPE enumType : EM_SECURITYGATE_EXPRESSION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_EXPRESSION_TYPE getEnum(int value) {
        for (EM_SECURITYGATE_EXPRESSION_TYPE e : EM_SECURITYGATE_EXPRESSION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_EXPRESSION_TYPE.EM_SECURITYGATE_EXPRESSION_UNKNOWN;
    }
}

