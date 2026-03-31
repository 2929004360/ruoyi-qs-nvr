package com.ruoyi.dahua.lib.enumeration;

/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 10:19
 * 人员通过方向枚举
 */
public enum EM_SECURITYGATE_PERSON_PASS_DIRECTION {
    // 未知
    EM_SECURITYGATE_PERSON_PASS_DIRECTION_UNKNOWN(0, "未知"),
    EM_SECURITYGATE_PERSON_PASS_DIRECTION_IN(1, "进入"),// 进入
    EM_SECURITYGATE_PERSON_PASS_DIRECTION_OUT(2, "离开");// 离开

    private final int value;
    private final String note;

    EM_SECURITYGATE_PERSON_PASS_DIRECTION(int givenValue, String note) {
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
        for (EM_SECURITYGATE_PERSON_PASS_DIRECTION enumType : EM_SECURITYGATE_PERSON_PASS_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_PERSON_PASS_DIRECTION enumType : EM_SECURITYGATE_PERSON_PASS_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_PERSON_PASS_DIRECTION getEnum(int value) {
        for (EM_SECURITYGATE_PERSON_PASS_DIRECTION e : EM_SECURITYGATE_PERSON_PASS_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_PERSON_PASS_DIRECTION.EM_SECURITYGATE_PERSON_PASS_DIRECTION_UNKNOWN;
    }
}

