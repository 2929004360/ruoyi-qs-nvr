package com.ruoyi.dahua.lib.enumeration;

/**
 * 载货状态
*/
public enum EM_BOAT_CARGO_TYPE
{
    /**
     * 未知
    */
    EM_BOAT_CARGO_TYPE_UNKNOWN(0, "未知"),
    /**
     * 空载状态
    */
    EM_BOAT_CARGO_TYPE_EMPTY(1, "空载状态"),
    /**
     * 正常载货状态
    */
    EM_BOAT_CARGO_TYPE_LOAD(2, "正常载货状态"),
    /**
     * 超载状态
    */
    EM_BOAT_CARGO_TYPE_OVERLOAD(3, "超载状态");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_BOAT_CARGO_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_BOAT_CARGO_TYPE enumType : EM_BOAT_CARGO_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_BOAT_CARGO_TYPE enumType : EM_BOAT_CARGO_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

