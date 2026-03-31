package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 目标类型
 * @date 2022/11/21 17:53:12
 */
public enum EM_DETECT_OBJECT_TYPE {
    /**
     * 未知
     */
    EM_DETECT_OBJECT_TYPE_UNKNOWN(0, "未知"),
    /**
     * 人脸
     */
    EM_DETECT_OBJECT_TYPE_FACE(1, "人脸"),
    /**
     * 车辆
     */
    EM_DETECT_OBJECT_TYPE_VEHICLE(2, "车辆"),
    /**
     * 结构化
     */
    EM_DETECT_OBJECT_TYPE_STRUCTLIZE(3, "结构化");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DETECT_OBJECT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DETECT_OBJECT_TYPE enumType : EM_DETECT_OBJECT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DETECT_OBJECT_TYPE enumType : EM_DETECT_OBJECT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_DETECT_OBJECT_TYPE getEnum(int value) {
        for (EM_DETECT_OBJECT_TYPE e : EM_DETECT_OBJECT_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DETECT_OBJECT_TYPE.EM_DETECT_OBJECT_TYPE_UNKNOWN;
    }
}

