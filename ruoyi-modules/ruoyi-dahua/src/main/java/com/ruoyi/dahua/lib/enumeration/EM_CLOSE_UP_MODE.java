package com.ruoyi.dahua.lib.enumeration;

/**
 * 系统联动特写模式
*/
public enum EM_CLOSE_UP_MODE
{
    /**
     * 未知
    */
    EM_CLOSE_UP_MODE_UNKNOWN(0, "未知"),
    /**
     * 不联动
    */
    EM_CLOSE_UP_MODE_NO_LINKAGE(1, "不联动"),
    /**
     * 跟踪模式
    */
    EM_CLOSE_UP_MODE_TRACK_MODE(2, "跟踪模式"),
    /**
     * 固定模式
    */
    EM_CLOSE_UP_MODE_FIXED_MODE(3, "固定模式"),
    /**
     * 指定区域模式
    */
    EM_CLOSE_UP_MODE_DESIGNED_REGION_MODE(4, "指定区域模式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CLOSE_UP_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CLOSE_UP_MODE enumType : EM_CLOSE_UP_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CLOSE_UP_MODE enumType : EM_CLOSE_UP_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

