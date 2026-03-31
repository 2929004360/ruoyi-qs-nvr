package com.ruoyi.dahua.lib.enumeration;

/**
 * 亮度上限调节模式
*/
public enum EM_LC_BRIGHTNESSUPPERLIMIT_MODE
{
    /**
     * 未知
    */
    EM_LC_BRIGHTNESSUPPERLIMIT_UNKNOWN(0, "未知"),
    /**
     * 手动
    */
    EM_LC_BRIGHTNESSUPPERLIMIT_MANUAL(1, "手动"),
    /**
     * 自动
    */
    EM_LC_BRIGHTNESSUPPERLIMIT_AUTO(2, "自动"),
    /**
     * 关闭
    */
    EM_LC_BRIGHTNESSUPPERLIMIT_OFF(3, "关闭");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LC_BRIGHTNESSUPPERLIMIT_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LC_BRIGHTNESSUPPERLIMIT_MODE enumType : EM_LC_BRIGHTNESSUPPERLIMIT_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LC_BRIGHTNESSUPPERLIMIT_MODE enumType : EM_LC_BRIGHTNESSUPPERLIMIT_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

