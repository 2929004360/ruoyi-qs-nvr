package com.ruoyi.dahua.lib.enumeration;

/**
 * 是否有肩包
*/
public enum EM_HAS_SHOULDER_BAG
{
    /**
     * 未知
    */
    EM_HAS_SHOULDER_BAG_UNKNOWN(0, "未知"),
    /**
     * 没有肩包
    */
    EM_HAS_SHOULDER_BAG_NO(1, "没有肩包"),
    /**
     * 有肩包
    */
    EM_HAS_SHOULDER_BAG_YES(2, "有肩包");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_HAS_SHOULDER_BAG(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_HAS_SHOULDER_BAG enumType : EM_HAS_SHOULDER_BAG.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_HAS_SHOULDER_BAG enumType : EM_HAS_SHOULDER_BAG.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

