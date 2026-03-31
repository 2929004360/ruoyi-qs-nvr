package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 工作服穿戴状态
 * @since ： Created in 2022/03/10 11:17
 */

public enum EM_WORKCLOTHES_STATE {
    /**
     * 未知
     */
    EM_WORKCLOTHES_STATE_UNKNOWN(0, "未知"),
    /**
     * 无工作服
     */
    EM_WORKCLOTHES_STATE_NOTWEAR(1, "无工作服"),
    /**
     * 有工作服
     */
    EM_WORKCLOTHES_STATE_WEAR(2, "有工作服");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_WORKCLOTHES_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WORKCLOTHES_STATE enumType : EM_WORKCLOTHES_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WORKCLOTHES_STATE enumType : EM_WORKCLOTHES_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_WORKCLOTHES_STATE getEnum(int value) {
        for (EM_WORKCLOTHES_STATE e : EM_WORKCLOTHES_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WORKCLOTHES_STATE.EM_WORKCLOTHES_STATE_UNKNOWN;
    }
}

