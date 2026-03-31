package com.ruoyi.dahua.lib.enumeration;

/**
 * 红外功能模式
 */
public enum EM_INFRARED_FUNC_MODE {
    EM_INFRARED_FUNC_MODE_UNKNOWN(0, "未知"),
    EM_INFRARED_FUNC_MODE_ON(1, "开启"),
    EM_INFRARED_FUNC_MODE_OFF(2, "关闭"),
    EM_INFRARED_FUNC_MODE_AUTO(3, "自动");

    private final int value;
    private final String note;

    EM_INFRARED_FUNC_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_INFRARED_FUNC_MODE enumType : EM_INFRARED_FUNC_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_INFRARED_FUNC_MODE enumType : EM_INFRARED_FUNC_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_INFRARED_FUNC_MODE getEnum(int value) {
        for (EM_INFRARED_FUNC_MODE e : EM_INFRARED_FUNC_MODE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_INFRARED_FUNC_MODE.EM_INFRARED_FUNC_MODE_UNKNOWN;
    }
}

