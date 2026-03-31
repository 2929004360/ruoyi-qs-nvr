package com.ruoyi.dahua.lib.enumeration;

/**
 * 码流类型
 *
 * @author 47040
 * @since Created at 2021/5/26 22:09
 */
public enum NET_EM_FORMAT_TYPE {
    EM_FORMAT_TYPE_UNKNOWN(0, "未知类型"),
    /*主码流*/
    EM_FORMAT_MAIN_NORMAL(1, "主码流普通编码"),
    EM_FORMAT_MAIN_MOVEEXAMINE(2, "主码流动检编码"),
    EM_FORMAT_MAIN_ALARM(3, "主码流报警编码"),
    /*辅码流*/
    EM_FORMAT_EXTRA1(4, "辅码流1"),
    EM_FORMAT_EXTRA2(5, "辅码流2"),
    EM_FORMAT_EXTRA3(6, "辅码流3");

    private final int value;
    private final String note;

    NET_EM_FORMAT_TYPE(int givenValue, String note) {
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
        for (TALK_CODING_TYPE enumType : TALK_CODING_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_FORMAT_TYPE enumType : NET_EM_FORMAT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_FORMAT_TYPE getEnum(int value) {
        for (NET_EM_FORMAT_TYPE e : NET_EM_FORMAT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_FORMAT_TYPE.EM_FORMAT_TYPE_UNKNOWN;
    }
}

