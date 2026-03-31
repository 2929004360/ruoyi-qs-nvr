package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 工作服合法状态
 * @since ： Created in 2022/03/10 11:17
 */

public enum EM_CLOTHES_LEGAL_STATE {
    /**
     * 未知
     */
    EM_CLOTHES_LEGAL_STATE_UNKNOWN(0, "未知"),
    /**
     * 不合法
     */
    EM_CLOTHES_LEGAL_STATE_WRONGFUL(1, "不合法"),
    /**
     * 合法
     */
    EM_CLOTHES_LEGAL_STATE_LEGAL(2, "合法");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_CLOTHES_LEGAL_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CLOTHES_LEGAL_STATE enumType : EM_CLOTHES_LEGAL_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CLOTHES_LEGAL_STATE enumType : EM_CLOTHES_LEGAL_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_CLOTHES_LEGAL_STATE getEnum(int value) {
        for (EM_CLOTHES_LEGAL_STATE e : EM_CLOTHES_LEGAL_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CLOTHES_LEGAL_STATE.EM_CLOTHES_LEGAL_STATE_UNKNOWN;
    }
}

