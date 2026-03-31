package com.ruoyi.dahua.lib.enumeration;

/**
 * 比对方式
*/
public enum EM_MATCH_TWO_FACE_TYPE
{
    /**
     * 未知
    */
    EM_MATCH_TWO_FACE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 图片比对
    */
    EM_MATCH_TWO_FACE_TYPE_PIC(1, "图片比对"),
    /**
     * 特征值比对
    */
    EM_MATCH_TWO_FACE_TYPE_EIGENVALUE(2, "特征值比对");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MATCH_TWO_FACE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MATCH_TWO_FACE_TYPE enumType : EM_MATCH_TWO_FACE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MATCH_TWO_FACE_TYPE enumType : EM_MATCH_TWO_FACE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

