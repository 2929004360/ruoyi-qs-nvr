package com.ruoyi.dahua.lib.enumeration;

/**
 * 眼镜类型
*/
public enum EM_GLASSES_TYPE
{
    /**
     * 未知
    */
    EM_GLASSES_UNKNOWN(0, "未知"),
    /**
     * 太阳眼镜
    */
    EM_GLASSES_SUNGLASS(1, "太阳眼镜"),
    /**
     * 普通眼镜
    */
    EM_GLASSES_GLASS(2, "普通眼镜");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_GLASSES_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_GLASSES_TYPE enumType : EM_GLASSES_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_GLASSES_TYPE enumType : EM_GLASSES_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

