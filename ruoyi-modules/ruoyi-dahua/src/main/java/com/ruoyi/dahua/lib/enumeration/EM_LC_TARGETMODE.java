package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持防补光过曝的目标类型
*/
public enum EM_LC_TARGETMODE
{
    /**
     * 未知
    */
    EM_LC_TARGETMODE_UNKNOWN(0, "未知"),
    /**
     * 关闭
    */
    EM_LC_TARGETMODE_OFF(1, "关闭"),
    /**
     * 车牌
    */
    EM_LC_TARGETMODE_PLATE(2, "车牌"),
    /**
     * 人脸
    */
    EM_LC_TARGETMODE_FACE(3, "人脸"),
    /**
     * 通用
    */
    EM_LC_TARGETMODE_GENERAL(4, "通用");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LC_TARGETMODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LC_TARGETMODE enumType : EM_LC_TARGETMODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LC_TARGETMODE enumType : EM_LC_TARGETMODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

