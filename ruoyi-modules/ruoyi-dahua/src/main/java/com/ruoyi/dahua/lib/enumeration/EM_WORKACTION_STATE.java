package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 作业行为状态信息
 * @since ： Created in 2022/03/08 15:25
 */

public enum EM_WORKACTION_STATE {
    /**
     * 未知
     */
    EM_WORKACTION_STATE_UNKNOWN(0, "未知"),
    /**
     * 无人作业
     */
    EM_WORKACTION_STATE_NO_WORKER(1, "无人作业"),
    /**
     * 单人作业
     */
    EEM_WORKACTION_STATE_SINGLE_WORKER(2, "单人作业"),
    /**
     * 无红马甲作业
     */
    EM_WORKACTION_STATE_NORED_VEST(3, "无红马甲作业");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_WORKACTION_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WORKACTION_STATE enumType : EM_WORKACTION_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WORKACTION_STATE enumType : EM_WORKACTION_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_WORKACTION_STATE getEnum(int value) {
        for (EM_WORKACTION_STATE e : EM_WORKACTION_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WORKACTION_STATE.EM_WORKACTION_STATE_UNKNOWN;
    }
}

