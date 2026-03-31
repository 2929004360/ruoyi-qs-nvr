package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 车辆行驶方向
 * @since ： Created in 2022/03/10 10:12
 */

public enum NET_FLOWSTAT_DIRECTION {
    /**
     * 兼容之前
     */
    DRIVING_DIR_UNKNOW(0, "兼容之前"),
    /**
     * 上行,即车辆离设备部署点越来越近
     */
    DRIVING_DIR_APPROACH(1, "上行,即车辆离设备部署点越来越近"),
    /**
     * 下行,即车辆离设备部署点越来越远
     */
    DRIVING_DIR_LEAVE(2, "下行,即车辆离设备部署点越来越远");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_FLOWSTAT_DIRECTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_FLOWSTAT_DIRECTION enumType : NET_FLOWSTAT_DIRECTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_FLOWSTAT_DIRECTION enumType : NET_FLOWSTAT_DIRECTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_FLOWSTAT_DIRECTION getEnum(int value) {
        for (NET_FLOWSTAT_DIRECTION e : NET_FLOWSTAT_DIRECTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_FLOWSTAT_DIRECTION.DRIVING_DIR_UNKNOW;
    }
}

