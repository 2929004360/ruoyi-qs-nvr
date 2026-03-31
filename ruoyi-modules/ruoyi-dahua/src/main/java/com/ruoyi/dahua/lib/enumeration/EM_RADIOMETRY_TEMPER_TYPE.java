package com.ruoyi.dahua.lib.enumeration;

/**
 * 测温项类别
*/
public enum EM_RADIOMETRY_TEMPER_TYPE
{
    /**
     * 点
    */
    EM_RADIOMETRY_TEMPER_TYPE_SPOT(0, "点"),
    /**
     * 区域
    */
    EM_RADIOMETRY_TEMPER_TYPE_AREA(1, "区域"),
    /**
     * 线
    */
    EM_RADIOMETRY_TEMPER_TYPE_LINE(2, "线");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RADIOMETRY_TEMPER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RADIOMETRY_TEMPER_TYPE enumType : EM_RADIOMETRY_TEMPER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RADIOMETRY_TEMPER_TYPE enumType : EM_RADIOMETRY_TEMPER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

