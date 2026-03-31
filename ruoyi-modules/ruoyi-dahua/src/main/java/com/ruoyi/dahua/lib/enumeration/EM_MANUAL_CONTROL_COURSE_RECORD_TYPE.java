package com.ruoyi.dahua.lib.enumeration;

/**
 * 控制类型
*/
public enum EM_MANUAL_CONTROL_COURSE_RECORD_TYPE
{
    /**
     * 未知
    */
    EM_MANUAL_CONTROL_COURSE_RECORD_TYPE_UNKNOWN(0, "未知"),
    /**
     * 开始
    */
    EM_MANUAL_CONTROL_COURSE_RECORD_TYPE_ON(1, "开始"),
    /**
     * 暂停
    */
    EM_MANUAL_CONTROL_COURSE_RECORD_TYPE_PAUSE(2, "暂停"),
    /**
     * 停止
    */
    EM_MANUAL_CONTROL_COURSE_RECORD_TYPE_OFF(3, "停止");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MANUAL_CONTROL_COURSE_RECORD_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MANUAL_CONTROL_COURSE_RECORD_TYPE enumType : EM_MANUAL_CONTROL_COURSE_RECORD_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MANUAL_CONTROL_COURSE_RECORD_TYPE enumType : EM_MANUAL_CONTROL_COURSE_RECORD_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

