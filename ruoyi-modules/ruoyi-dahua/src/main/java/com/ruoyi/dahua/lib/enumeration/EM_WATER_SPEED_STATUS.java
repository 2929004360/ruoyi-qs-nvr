package com.ruoyi.dahua.lib.enumeration;

/**
 * 水流速状态
*/
public enum EM_WATER_SPEED_STATUS
{
    /**
     * 未知
    */
    EM_WATER_SPEED_STATUS_UNKNOWN(0, "未知"),
    /**
     * 水流速正常
    */
    EM_WATER_SPEED_STATUS_NORMAL(1, "水流速正常"),
    /**
     * 水流速高于上限阈值
    */
    EM_WATER_SPEED_STATUS_HIGH(2, "水流速高于上限阈值"),
    /**
     * 水流速低于下限阈值
    */
    EM_WATER_SPEED_STATUS_LOW(3, "水流速低于下限阈值");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_WATER_SPEED_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WATER_SPEED_STATUS enumType : EM_WATER_SPEED_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WATER_SPEED_STATUS enumType : EM_WATER_SPEED_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

