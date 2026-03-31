package com.ruoyi.dahua.lib.enumeration;

/**
 * 界面图片状态
 *
 * @author 47040
 * @since Created in 2021/4/27 19:17
 */
public enum EM_MAIN_PAGE_STATE {
    /**
     * 未知
     */
    EM_MAIN_PAGE_STATE_UNKNOWN(0, "未知"),
    /**
     * 正常
     */
    EM_MAIN_PAGE_STATE_NORMAL(1, "正常"),
    /**
     * 待机
     */
    EM_MAIN_PAGE_STATE_STANDBY(2, "待机"),
    /**
     * 通道关闭
     */
    EM_MAIN_PAGE_STATE_CHANNELCLOSED(3, "通道关闭"),
    /**
     * 禁止通行
     */
    EM_MAIN_PAGE_STATE_FORBIDENTER(4, "禁止通行"),
    /**
     * 紧急疏散
     */
    EM_MAIN_PAGE_STATE_URGENTEVACUATE(5, "紧急疏散");

    private final int value;
    private final String note;

    EM_MAIN_PAGE_STATE(int givenValue, String note) {
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
        for (EM_MAIN_PAGE_STATE enumType : EM_MAIN_PAGE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MAIN_PAGE_STATE enumType : EM_MAIN_PAGE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_MAIN_PAGE_STATE getEnum(int value) {
        for (EM_MAIN_PAGE_STATE e : EM_MAIN_PAGE_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_MAIN_PAGE_STATE.EM_MAIN_PAGE_STATE_UNKNOWN;
    }
}

