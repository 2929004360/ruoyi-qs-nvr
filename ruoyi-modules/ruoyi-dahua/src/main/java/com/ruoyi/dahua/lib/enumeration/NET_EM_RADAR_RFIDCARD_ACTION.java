package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 卡片行为
 * @since ： Created in 2022/02/11 15:37
 */

public enum NET_EM_RADAR_RFIDCARD_ACTION {
    /**
     * 未知
     */
    EM_RADAR_RFIDCARD_ACTION_UNKNOWN(0, "未知"),
    /**
     * 卡片离开
     */
    EM_RADAR_RFIDCARD_ACTION_LEAVE(1, "卡片离开"),
    /**
     * 卡片进入
     */
    EM_RADAR_RFIDCARD_ACTION_ENTER(2, "卡片进入");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_EM_RADAR_RFIDCARD_ACTION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_RADAR_RFIDCARD_ACTION enumType : NET_EM_RADAR_RFIDCARD_ACTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_RADAR_RFIDCARD_ACTION enumType : NET_EM_RADAR_RFIDCARD_ACTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_RADAR_RFIDCARD_ACTION getEnum(int value) {
        for (NET_EM_RADAR_RFIDCARD_ACTION e : NET_EM_RADAR_RFIDCARD_ACTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_RADAR_RFIDCARD_ACTION.EM_RADAR_RFIDCARD_ACTION_UNKNOWN;
    }
}

