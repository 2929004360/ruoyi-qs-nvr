package com.ruoyi.dahua.lib.enumeration;

/**
 * 单个平台接入启动选项
 *
 * @author 47040
 * @version 1.0.0
 * @since Created in 2021/3/9 8:42
 */
public enum EM_SERVER_OPTION {
    /**
     * 未知
     */
    EM_SERVER_OPTION_UNKNOWN(0, "未知"),
    /**
     * 平台接入不启动
     */
    EM_SERVER_OPTION_DISABLE(1, "平台接入不启动"),
    /**
     * 以28181接入方式开启
     */
    EM_SERVER_OPTION_GB28181(2, "以28181接入方式开启"),
    /**
     * 以35114接入方式开启
     */
    EM_SERVER_OPTION_GB35114(3, "以35114接入方式开启");

    private final int value;
    private final String note;

    EM_SERVER_OPTION(int givenValue, String note) {
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
        for (EM_SERVER_OPTION enumType : EM_SERVER_OPTION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SERVER_OPTION enumType : EM_SERVER_OPTION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SERVER_OPTION getEnum(int value) {
        for (EM_SERVER_OPTION e : EM_SERVER_OPTION.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SERVER_OPTION.EM_SERVER_OPTION_UNKNOWN;
    }
}

