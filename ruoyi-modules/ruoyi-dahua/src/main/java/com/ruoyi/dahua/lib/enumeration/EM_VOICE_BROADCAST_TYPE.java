package com.ruoyi.dahua.lib.enumeration;

/**
 * EM_VOICE_BROADCAST_TYPE 播放的内容类型
*/
public enum EM_VOICE_BROADCAST_TYPE
{
    /**
     * 未知
    */
    EM_TYPE_UNKOWN(0, "未知"),
    /**
     * 文字播放
    */
    EM_TYPE_TEXT(1, "文字播放"),
    /**
     * 文件播放
    */
    EM_TYPE_FILE(2, "文件播放");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VOICE_BROADCAST_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VOICE_BROADCAST_TYPE enumType : EM_VOICE_BROADCAST_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VOICE_BROADCAST_TYPE enumType : EM_VOICE_BROADCAST_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

