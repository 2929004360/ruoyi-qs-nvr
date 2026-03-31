package com.ruoyi.dahua.lib.enumeration;

/**
 * 检测类型
*/
public enum EM_OCR_DETECTION_DETECT_TYPE
{
    /**
     * 未知
    */
    EM_OCR_DETECTION_DETECT_TYPE_UNKNOWN(0, "未知"),
    /**
     * 温度
    */
    EM_OCR_DETECTION_DETECT_TYPE_TEMPERATURE(1, "温度"),
    /**
     * 称重
    */
    EM_OCR_DETECTION_DETECT_TYPE_WEIGH(2, "称重");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_OCR_DETECTION_DETECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_OCR_DETECTION_DETECT_TYPE enumType : EM_OCR_DETECTION_DETECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_OCR_DETECTION_DETECT_TYPE enumType : EM_OCR_DETECTION_DETECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

