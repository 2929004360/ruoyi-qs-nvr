package com.ruoyi.dahua.lib.enumeration;

/**
 * 码流类型
*/
public enum EM_STREAM_TYPE
{
    /**
     * 未知状态
    */
    EM_STREAM_TYPE_UNKNOWN(0, "未知状态"),
    /**
     * 主码流
    */
    EM_STREAM_TYPE_MAIN(1, "主码流"),
    /**
     * 辅码流1
    */
    EM_STREAM_TYPE_EXTRA1(2, "辅码流1"),
    /**
     * 辅码流2
    */
    EM_STREAM_TYPE_EXTRA2(3, "辅码流2"),
    /**
     * 辅码流3
    */
    EM_STREAM_TYPE_EXTRA3(4, "辅码流3"),
    /**
     * 所有辅码流
    */
    EM_STREAM_TYPE_EXTRAALL(5, "所有辅码流");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STREAM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STREAM_TYPE enumType : EM_STREAM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STREAM_TYPE enumType : EM_STREAM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

