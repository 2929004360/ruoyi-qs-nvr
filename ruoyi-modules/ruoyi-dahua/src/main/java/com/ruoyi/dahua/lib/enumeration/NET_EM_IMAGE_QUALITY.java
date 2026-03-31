package com.ruoyi.dahua.lib.enumeration;

/**
 * 画质
 *
 * @author 47040
 * @since Created at 2021/5/26 22:25
 */
public enum NET_EM_IMAGE_QUALITY {
    EM_IMAGE_QUALITY_Q10(1, "图像质量10%"),
    EM_IMAGE_QUALITY_Q30(2, "图像质量30%"),
    EM_IMAGE_QUALITY_Q50(3, "图像质量50%"),
    EM_IMAGE_QUALITY_Q60(4, "图像质量60%"),
    EM_IMAGE_QUALITY_Q80(5, "图像质量80%"),
    EM_IMAGE_QUALITY_Q100(6, "图像质量100%");

    private final int value;
    private final String note;

    NET_EM_IMAGE_QUALITY(int givenValue, String note) {
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
        for (NET_EM_IMAGE_QUALITY enumType : NET_EM_IMAGE_QUALITY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_IMAGE_QUALITY enumType : NET_EM_IMAGE_QUALITY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_IMAGE_QUALITY getEnum(int value) {
        for (NET_EM_IMAGE_QUALITY e : NET_EM_IMAGE_QUALITY.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_IMAGE_QUALITY.EM_IMAGE_QUALITY_Q10;
    }
}

