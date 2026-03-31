package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持的电梯控制方式类型的集合
*/
public enum EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES
{
    /**
     * 未知
    */
    EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES_UNKNOWN(0, "未知"),
    /**
     * 本地485协议梯控方式
    */
    EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES_LOCAL485(1, "本地485协议梯控方式"),
    /**
     * 网络协议梯控方式
    */
    EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES_NETWORK(2, "网络协议梯控方式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES enumType : EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES enumType : EM_FACE_RECOGNITION_SUPPORT_LIFT_CONTROL_TYPES.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

