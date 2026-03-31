package com.ruoyi.dahua.lib.enumeration;

/**
 * 车位类型
*/
public enum EM_PARKINGSPACE_TYPE
{
    /**
     * 未知
    */
    EM_PARKINGSPACE_TYPE_UNKNOWN(-1, "未知"),
    /**
     * 小车车位
    */
    EM_PARKINGSPACE_TYPE_SMALL_CAR(0, "小车车位"),
    /**
     * 大车车位
    */
    EM_PARKINGSPACE_TYPE_BIG_CAR(1, "大车车位");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKINGSPACE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKINGSPACE_TYPE enumType : EM_PARKINGSPACE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKINGSPACE_TYPE enumType : EM_PARKINGSPACE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

