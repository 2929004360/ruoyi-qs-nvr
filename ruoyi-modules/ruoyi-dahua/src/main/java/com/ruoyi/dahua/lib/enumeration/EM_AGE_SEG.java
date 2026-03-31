package com.ruoyi.dahua.lib.enumeration;

/**
 * 年龄段
*/
public enum EM_AGE_SEG
{
    /**
     * 未知
    */
    EM_AGE_SEG_UNKOWN(0, "未知"),
    /**
     * 婴儿
    */
    EM_AGE_SEG_BABY(2, "婴儿"),
    /**
     * 幼儿
    */
    EM_AGE_SEG_CHILD(10, "幼儿"),
    /**
     * 青年
    */
    EM_AGE_SEG_YOUTH(28, "青年"),
    /**
     * 中年
    */
    EM_AGE_SEG_MIDDLE(50, "中年"),
    /**
     * 老年
    */
    EM_AGE_SEG_OLD(60, "老年");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_AGE_SEG(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_AGE_SEG enumType : EM_AGE_SEG.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_AGE_SEG enumType : EM_AGE_SEG.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

