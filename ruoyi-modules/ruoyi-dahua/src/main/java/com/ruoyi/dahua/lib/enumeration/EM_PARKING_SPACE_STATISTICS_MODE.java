package com.ruoyi.dahua.lib.enumeration;

/**
 * 统计模式
*/
public enum EM_PARKING_SPACE_STATISTICS_MODE
{
    /**
     * 未知
    */
    EM_PARKING_SPACE_STATISTICS_MODE_UNKNOWN(0, "未知"),
    /**
     * 区域模式
    */
    EM_PARKING_SPACE_STATISTICS_MODE_AREA_MODE(1, "区域模式"),
    /**
     * 车位模式
    */
    EM_PARKING_SPACE_STATISTICS_MODE_SPACE_MODE(2, "车位模式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKING_SPACE_STATISTICS_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKING_SPACE_STATISTICS_MODE enumType : EM_PARKING_SPACE_STATISTICS_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKING_SPACE_STATISTICS_MODE enumType : EM_PARKING_SPACE_STATISTICS_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

