package com.ruoyi.dahua.lib.enumeration;

/**
 * 车位状态
*/
public enum EM_PARKING_SPACE_STATUS
{
    /**
     * 未知
    */
    EM_PARKING_SPACE_UNKNOWN(0, "未知"),
    /**
     * 有车
    */
    EM_PARKING_SPACE_PARK(1, "有车"),
    /**
     * 无车
    */
    EM_PARKING_SPACE_N0_PARK(2, "无车");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKING_SPACE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKING_SPACE_STATUS enumType : EM_PARKING_SPACE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKING_SPACE_STATUS enumType : EM_PARKING_SPACE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

