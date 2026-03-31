package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 语音类型
 * @date 2022/11/15 20:11:54
 */
public enum EM_CUSTOM_MEDICAL_VOICE_TYPE {
    /**
     * 未知
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 门已打开，可以进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_1(1, "门已打开，可以进入"),
    /**
     * 不是本科室，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_2(2, "不是本科室，不能进入"),
    /**
     * 已出院，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_3(3, "已出院，不能进入"),
    /**
     * 核酸检测超期，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_4(4, "核酸检测超期，不能进入"),
    /**
     * 体温不正常，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_5(5, "体温不正常，不能进入"),
    /**
     * 证件信息缺失，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_6(6, "证件信息缺失，不能进入"),
    /**
     * 其他问题，不能进入
     */
    EM_CUSTOM_MEDICAL_VOICE_TYPE_7(7, "其他问题，不能进入");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CUSTOM_MEDICAL_VOICE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CUSTOM_MEDICAL_VOICE_TYPE enumType : EM_CUSTOM_MEDICAL_VOICE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CUSTOM_MEDICAL_VOICE_TYPE enumType : EM_CUSTOM_MEDICAL_VOICE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_CUSTOM_MEDICAL_VOICE_TYPE getEnum(int value) {
        for (EM_CUSTOM_MEDICAL_VOICE_TYPE e : EM_CUSTOM_MEDICAL_VOICE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_CUSTOM_MEDICAL_VOICE_TYPE.EM_CUSTOM_MEDICAL_VOICE_TYPE_UNKNOWN;
    }
}

