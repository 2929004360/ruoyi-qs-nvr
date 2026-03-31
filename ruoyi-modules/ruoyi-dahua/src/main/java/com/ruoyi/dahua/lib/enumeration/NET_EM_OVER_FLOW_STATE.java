package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 车辆排队长度溢出状态
 * @since ： Created in 2022/03/10 10:12
 */

public enum NET_EM_OVER_FLOW_STATE {
    /**
     * 状态未知
     */
    EM_OVER_FLOW_STATE_UNKNOWN(0, "状态未知"),
    /**
     * 未溢出
     */
    EM_OVER_FLOW_STATE_NOT_OVERFLOW(1, "未溢出"),
    /**
     * 溢出
     */
    EM_OVER_FLOW_STATE_OVERFLOW(2, "溢出");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_EM_OVER_FLOW_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_OVER_FLOW_STATE enumType : NET_EM_OVER_FLOW_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_OVER_FLOW_STATE enumType : NET_EM_OVER_FLOW_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_OVER_FLOW_STATE getEnum(int value) {
        for (NET_EM_OVER_FLOW_STATE e : NET_EM_OVER_FLOW_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_OVER_FLOW_STATE.EM_OVER_FLOW_STATE_UNKNOWN;
    }
}

