package com.ruoyi.dahua.lib.enumeration;

/**
 * @author ： 291189
 * @since ： Created in 2021/6/30 11:11
 * 安检门事件人员是否戴眼镜
 */
public enum EM_SECURITYGATE_GLASSES_TYPE {
    EM_SECURITYGATE_GLASSES_UNKNOWN(-1, "未知"),// 未知
    EM_SECURITYGATE_GLASSES_UNDEFINE(0, "未识别"),// 未识别
    EM_SECURITYGATE_WITHOUT_GLASSES(1, "未戴眼镜"),// 未戴眼镜
    EM_SECURITYGATE_WITH_GLASSES(2, "戴眼镜");// 戴眼镜

    private final int value;
    private final String note;

    EM_SECURITYGATE_GLASSES_TYPE(int givenValue, String note) {
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
        for (EM_SECURITYGATE_GLASSES_TYPE enumType : EM_SECURITYGATE_GLASSES_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITYGATE_GLASSES_TYPE enumType : EM_SECURITYGATE_GLASSES_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SECURITYGATE_GLASSES_TYPE getEnum(int value) {
        for (EM_SECURITYGATE_GLASSES_TYPE e : EM_SECURITYGATE_GLASSES_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SECURITYGATE_GLASSES_TYPE.EM_SECURITYGATE_GLASSES_UNKNOWN;
    }
}

