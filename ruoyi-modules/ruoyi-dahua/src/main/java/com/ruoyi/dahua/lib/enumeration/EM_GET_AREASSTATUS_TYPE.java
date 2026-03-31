package com.ruoyi.dahua.lib.enumeration;

/**
 * 获取异常防区类型
*/
public enum EM_GET_AREASSTATUS_TYPE
{
    /**
     * 未知
    */
    EM_GET_AREASSTATUS_TYPE_UNKNOWN(0, "未知"),
    /**
     * 激活
    */
    EM_GET_AREASSTATUS_TYPE_ACTIVE(1, "激活"),
    /**
     * 打开
    */
    EM_GET_AREASSTATUS_TYPE_OPEN(2, "打开");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_GET_AREASSTATUS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_GET_AREASSTATUS_TYPE enumType : EM_GET_AREASSTATUS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_GET_AREASSTATUS_TYPE enumType : EM_GET_AREASSTATUS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

