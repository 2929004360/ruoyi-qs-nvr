package com.ruoyi.dahua.lib.enumeration;

/**
 * 帧子类型
*/
public enum EM_DATA_CALL_BACK_FRAM_SUB_TYPE
{
    /**
     * 数据无效
    */
    EM_DATA_CALL_BACK_FRAM_SUB_TYPE_INVALID(-1, "数据无效"),
    /**
     * 视频I帧
    */
    EM_DATA_CALL_BACK_FRAM_SUB_TYPE_I_FRAME(0, "视频I帧"),
    /**
     * 视频P帧
    */
    EM_DATA_CALL_BACK_FRAM_SUB_TYPE_P_FRAME(1, "视频P帧"),
    /**
     * 视频B帧
    */
    EM_DATA_CALL_BACK_FRAM_SUB_TYPE_B_FRAME(2, "视频B帧");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DATA_CALL_BACK_FRAM_SUB_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DATA_CALL_BACK_FRAM_SUB_TYPE enumType : EM_DATA_CALL_BACK_FRAM_SUB_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DATA_CALL_BACK_FRAM_SUB_TYPE enumType : EM_DATA_CALL_BACK_FRAM_SUB_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

