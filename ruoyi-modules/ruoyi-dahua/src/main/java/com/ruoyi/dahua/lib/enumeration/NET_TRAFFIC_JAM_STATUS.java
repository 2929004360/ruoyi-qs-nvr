package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 道路拥挤状况
 * @since ： Created in 2022/03/10 10:12
 */

public enum NET_TRAFFIC_JAM_STATUS {
    /**
     * 未知
     */
    JAM_STATUS_UNKNOW(0, "未知"),
    /**
     * 通畅
     */
    JAM_STATUS_CLEAR(1, "通畅"),
    /**
     * 拥堵
     */
    JAM_STATUS_JAMMED(2, "拥堵"),
    /**
     * 拥堵
     */
    JAM_STATUS_SLOWED(3, "拥堵");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_TRAFFIC_JAM_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_TRAFFIC_JAM_STATUS enumType : NET_TRAFFIC_JAM_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_TRAFFIC_JAM_STATUS enumType : NET_TRAFFIC_JAM_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_TRAFFIC_JAM_STATUS getEnum(int value) {
        for (NET_TRAFFIC_JAM_STATUS e : NET_TRAFFIC_JAM_STATUS.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_TRAFFIC_JAM_STATUS.JAM_STATUS_UNKNOW;
    }
}

