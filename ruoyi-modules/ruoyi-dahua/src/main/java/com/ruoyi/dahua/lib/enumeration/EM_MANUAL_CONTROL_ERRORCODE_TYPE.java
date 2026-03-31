package com.ruoyi.dahua.lib.enumeration;

/**
 * 错误码枚举
*/
public enum EM_MANUAL_CONTROL_ERRORCODE_TYPE
{
    /**
     * 未知
    */
    EM_MANUAL_CONTROL_ERRORCODE_TYPE_UNKNOWN(-1, "未知"),
    /**
     * 正常
    */
    EM_MANUAL_CONTROL_ERRORCODE_TYPE_NORMAL(0, "正常"),
    /**
     * 状态错误（重复动作）
    */
    EM_MANUAL_CONTROL_ERRORCODE_TYPE_ERROR(1, "状态错误（重复动作）"),
    /**
     * 录像控制异常（导播通道不允许录像）
    */
    EM_MANUAL_CONTROL_ERRORCODE_TYPE_ABNORMAL(2, "录像控制异常（导播通道不允许录像）");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MANUAL_CONTROL_ERRORCODE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MANUAL_CONTROL_ERRORCODE_TYPE enumType : EM_MANUAL_CONTROL_ERRORCODE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MANUAL_CONTROL_ERRORCODE_TYPE enumType : EM_MANUAL_CONTROL_ERRORCODE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

