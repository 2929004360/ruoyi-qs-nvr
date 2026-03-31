package com.ruoyi.dahua.lib.enumeration;

/**
 * 帧类型
*/
public enum EM_DATA_CALL_BACK_FRAM_TYPE
{
    /**
     * 帧类型不可知
    */
    EM_DATA_CALL_BACK_FRAM_TYPE_UNKNOWN(0, "帧类型不可知"),
    /**
     * 帧类型是视频帧
    */
    EM_DATA_CALL_BACK_FRAM_TYPE_VIDEO(1, "帧类型是视频帧"),
    /**
     * 帧类型是音频帧
    */
    EM_DATA_CALL_BACK_FRAM_TYPE_AUDIO(2, "帧类型是音频帧"),
    /**
     * 帧类型是数据帧
    */
    EM_DATA_CALL_BACK_FRAM_TYPE_DATA(3, "帧类型是数据帧");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DATA_CALL_BACK_FRAM_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DATA_CALL_BACK_FRAM_TYPE enumType : EM_DATA_CALL_BACK_FRAM_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DATA_CALL_BACK_FRAM_TYPE enumType : EM_DATA_CALL_BACK_FRAM_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

