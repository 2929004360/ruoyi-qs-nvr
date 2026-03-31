package com.ruoyi.dahua.lib.enumeration;

/**
 * 眼镜状态
*/
public enum EM_GLASSES_STATE
{
    /**
     * 未知
    */
    EM_GLASSES_STATE_UNKNOWN(0, "未知"),
    /**
     * 不戴
    */
    EM_GLASSES_STATE_NO(1, "不戴"),
    /**
     * 戴
    */
    EM_GLASSES_STATE_WEAR(2, "戴");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_GLASSES_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_GLASSES_STATE enumType : EM_GLASSES_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_GLASSES_STATE enumType : EM_GLASSES_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

