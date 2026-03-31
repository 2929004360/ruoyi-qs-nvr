package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 合规检测状态
 * @since ： Created in 2022/03/10 11:17
 */

public enum EM_COMPLIANCE_STATE {
    /**
     * 合规
     */
    EM_COMPLIANCE_STATE_COMPLIANT(0, "合规"),
    /**
     * 不合规
     */
    EM_COMPLIANCE_STATE_NONCOMPLIANT(1, "不合规"),
    /**
     * 未知
     */
    EM_COMPLIANCE_STATE_UNKNOWN(2, "未知");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_COMPLIANCE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_COMPLIANCE_STATE enumType : EM_COMPLIANCE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_COMPLIANCE_STATE enumType : EM_COMPLIANCE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_COMPLIANCE_STATE getEnum(int value) {
        for (EM_COMPLIANCE_STATE e : EM_COMPLIANCE_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_COMPLIANCE_STATE.EM_COMPLIANCE_STATE_COMPLIANT;
    }
}

