package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 道路等级
 * @since ： Created in 2022/03/10 10:12
 */

public enum NET_TRAFFIC_ROAD_RANK {
    /**
     * 
     */
    ROAD_RANK_UNKNOWN(0, ""),
    /**
     * 快速路
     */
    ROAD_RANK_RAPID(1, "快速路"),
    /**
     * 主干路
     */
    ROAD_RANK_TRUNK(2, "主干路"),
    /**
     * 次干路
     */
    ROAD_RANK_SUBTRUNK(3, "次干路"),
    /**
     * 支路
     */
    ROAD_RANK_BRANCH(4, "支路");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_TRAFFIC_ROAD_RANK(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_TRAFFIC_ROAD_RANK enumType : NET_TRAFFIC_ROAD_RANK.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_TRAFFIC_ROAD_RANK enumType : NET_TRAFFIC_ROAD_RANK.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_TRAFFIC_ROAD_RANK getEnum(int value) {
        for (NET_TRAFFIC_ROAD_RANK e : NET_TRAFFIC_ROAD_RANK.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_TRAFFIC_ROAD_RANK.ROAD_RANK_UNKNOWN;
    }
}

