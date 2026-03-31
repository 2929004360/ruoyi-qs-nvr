package com.ruoyi.dahua.lib.enumeration;

/**
 * 联动码流类型
*/
public enum EM_LINKAGE_STREAM_TYPE
{
    /**
     * 未知
    */
    EM_LINKAGE_STREAM_TYPE_UNKNOWN(0, "未知"),
    /**
     * 主码流
    */
    EM_LINKAGE_STREAM_TYPE_MAIN(1, "主码流"),
    /**
     * 辅码流1
    */
    EM_LINKAGE_STREAM_TYPE_EXTRA_1(2, "辅码流1"),
    /**
     * 辅码流2
    */
    EM_LINKAGE_STREAM_TYPE_EXTRA_2(3, "辅码流2"),
    /**
     * 辅码流3
    */
    EM_LINKAGE_STREAM_TYPE_EXTRA_3(4, "辅码流3");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_LINKAGE_STREAM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_LINKAGE_STREAM_TYPE enumType : EM_LINKAGE_STREAM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_LINKAGE_STREAM_TYPE enumType : EM_LINKAGE_STREAM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

