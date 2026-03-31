package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 291189
 * @description 三态布尔类型
 * @origin autoTool
 * @date 2023/09/06 16:00:01
 */
public enum NET_THREE_STATUS_BOOL {
    /**
     *
     */
    BOOL_STATUS_FALSE(0, "失败"),
    /**
     *
     */
    BOOL_STATUS_TRUE(1, "成功"),
    /**
     * 未知
     */
    BOOL_STATUS_UNKNOWN(2, "未知");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_THREE_STATUS_BOOL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_THREE_STATUS_BOOL enumType : NET_THREE_STATUS_BOOL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_THREE_STATUS_BOOL enumType : NET_THREE_STATUS_BOOL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_THREE_STATUS_BOOL getEnum(int value) {
        for (NET_THREE_STATUS_BOOL e : NET_THREE_STATUS_BOOL.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return NET_THREE_STATUS_BOOL.BOOL_STATUS_FALSE;
    }
}

