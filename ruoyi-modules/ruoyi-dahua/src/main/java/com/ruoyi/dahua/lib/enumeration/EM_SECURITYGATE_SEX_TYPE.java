package com.ruoyi.dahua.lib.enumeration;
/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:51
 *  安检门事件人员性别
 */
public enum EM_SECURITYGATE_SEX_TYPE {
    EM_SECURITYGATE_SEX_MAN(0, "男"),// 男
    EM_SECURITYGATE_SEX_WOMEN(1, "女"),// 女
    EM_SECURITYGATE_SEX_UNKNOWN(2, "未知");// 未知

    private final int value;
    private final String note;

    EM_SECURITYGATE_SEX_TYPE(int givenValue, String note) {
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
        for (EM_SECURITYGATE_SEX_TYPE enumType : EM_SECURITYGATE_SEX_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_SEX_TYPE enumType : EM_SECURITYGATE_SEX_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_SEX_TYPE getEnum(int value) {
        for (EM_SECURITYGATE_SEX_TYPE e : EM_SECURITYGATE_SEX_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_SEX_TYPE.EM_SECURITYGATE_SEX_UNKNOWN;
    }
}

