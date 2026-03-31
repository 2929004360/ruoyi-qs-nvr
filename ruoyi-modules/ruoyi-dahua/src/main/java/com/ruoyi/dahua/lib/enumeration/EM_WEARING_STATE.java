package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 穿戴状态
 * @since ： Created in 2022/03/10 11:17
 */

public enum EM_WEARING_STATE {
    /**
     * 未知
     */
    EM_WEARING_STATE_UNKNOWN(0, "未知"),
    /**
     * 没有穿戴
     */
    EM_WEARING_STATE_NOTWEAR(1, "没有穿戴"),
    /**
     * 有穿戴
     */
    EM_WEARING_STATE_WEAR(2, "有穿戴");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_WEARING_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WEARING_STATE enumType : EM_WEARING_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WEARING_STATE enumType : EM_WEARING_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_WEARING_STATE getEnum(int value) {
        for (EM_WEARING_STATE e : EM_WEARING_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WEARING_STATE.EM_WEARING_STATE_UNKNOWN;
    }
}

