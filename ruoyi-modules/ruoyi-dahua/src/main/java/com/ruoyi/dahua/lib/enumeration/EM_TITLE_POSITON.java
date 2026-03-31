package com.ruoyi.dahua.lib.enumeration;

/**
 * OSD显示位置
*/
public enum EM_TITLE_POSITON
{
    /**
     * 未知
    */
    EM_TITLE_POSITON_UNKNOW(0, "未知"),
    /**
     * 右上角
    */
    EM_TITLE_POSITON_RIGHTUP(1, "右上角"),
    /**
     * 左上角
    */
    EM_TITLE_POSITON_LEFTUP(2, "左上角"),
    /**
     * 左下角
    */
    EM_TITLE_POSITON_LEFTDOWN(3, "左下角"),
    /**
     * 右下角
    */
    EM_TITLE_POSITON_RIGHTDOWN(4, "右下角");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_TITLE_POSITON(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TITLE_POSITON enumType : EM_TITLE_POSITON.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TITLE_POSITON enumType : EM_TITLE_POSITON.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

