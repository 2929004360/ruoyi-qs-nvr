package com.ruoyi.dahua.lib.enumeration;

/**
 * 检测事件类型
*/
public enum EM_OCR_DETECTION_TRIGGER_TYPE
{
    /**
     * 未知
    */
    EM_OCR_DETECTION_TRIGGER_TYPE_UNKNOWN(-1, "未知"),
    /**
     * 大于上限
    */
    EM_OCR_DETECTION_TRIGGER_TYPE_GREATER_THAN_TOPLIMIT(0, "大于上限"),
    /**
     * 小于下限
    */
    EM_OCR_DETECTION_TRIGGER_TYPE_LESS_THAN_LOWERLIMIT(1, "小于下限"),
    /**
     * 等于阈值
    */
    EM_OCR_DETECTION_TRIGGER_TYPE_EQUAL_TO_THRESHOLD(2, "等于阈值");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_OCR_DETECTION_TRIGGER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_OCR_DETECTION_TRIGGER_TYPE enumType : EM_OCR_DETECTION_TRIGGER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_OCR_DETECTION_TRIGGER_TYPE enumType : EM_OCR_DETECTION_TRIGGER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

