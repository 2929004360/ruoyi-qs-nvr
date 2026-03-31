package com.ruoyi.dahua.lib.enumeration;

public enum EM_FACE_CHECK {
    EM_FACE_CHECK_UNKNOWN(-1, "未知"),// 未知
    EM_FACE_CHECK_NODATA(0, "该人员无人脸数据"),// 该人员无人脸数据
    EM_FACE_CHECK_CONSISTENT(1, "刷卡和人脸人员一致"),// 刷卡和人脸人员一致
    EM_FACE_CHECK_NOT_CONSISTENT(2, "刷卡和人脸人员不一致");// 刷卡和人脸人员不一致

    private final int value;
    private final String note;

    EM_FACE_CHECK(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_FACE_CHECK enumType : EM_FACE_CHECK.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_FACE_CHECK enumType : EM_FACE_CHECK.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_EVENT_LEVEL getEnum(int value) {
        for (EM_EVENT_LEVEL e : EM_EVENT_LEVEL.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_EVENT_LEVEL.EM_EVENT_LEVEL_UNKNOWN;
    }
}

