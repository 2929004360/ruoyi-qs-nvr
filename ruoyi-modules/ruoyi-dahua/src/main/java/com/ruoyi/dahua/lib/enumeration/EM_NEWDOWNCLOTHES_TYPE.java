package com.ruoyi.dahua.lib.enumeration;

/**
 * 新下衣类型
*/
public enum EM_NEWDOWNCLOTHES_TYPE
{
    /**
     * 未知
    */
    EM_NEWDOWNCLOTHES_TYPE_UNKNOWN(0, "未知"),
    /**
     * 长裤
    */
    EM_NEWDOWNCLOTHES_TYPE_TROUSERS(1, "长裤"),
    /**
     * 短裤
    */
    EM_NEWDOWNCLOTHES_TYPE_SHORTS(2, "短裤"),
    /**
     * 裙子
    */
    EM_NEWDOWNCLOTHES_TYPE_SKIRT(3, "裙子");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NEWDOWNCLOTHES_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NEWDOWNCLOTHES_TYPE enumType : EM_NEWDOWNCLOTHES_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NEWDOWNCLOTHES_TYPE enumType : EM_NEWDOWNCLOTHES_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

