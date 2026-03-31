package com.ruoyi.dahua.lib.enumeration;

/**
 * 对接第三方平台数据
*/
public enum EM_RENT_STATE
{
    /**
     * 未知
    */
    EM_RENT_STATE_UNKNOWN(0, "未知"),
    /**
     * 正常缴费
    */
    EM_RENT_STATE_NORMAL_PAYMENT(1, "正常缴费"),
    /**
     * 欠费0~3个月
    */
    EM_RENT_STATE_0_TO_3_MONTHS_IN_ARREARS(2, "欠费0~3个月"),
    /**
     * 欠费3~6个月
    */
    EM_RENT_STATE_3_TO_6_MONTHS_IN_ARREARS(3, "欠费3~6个月"),
    /**
     * 欠费6~12个月
    */
    EM_RENT_STATE_6_TO_12_MONTHS_IN_ARREARS(4, "欠费6~12个月"),
    /**
     * 欠费12个月以上
    */
    EM_RENT_STATE_MORE_THAN_12_MONTHS_IN_ARREARS(5, "欠费12个月以上"),
    /**
     * 临时访客
    */
    EM_RENT_STATE_TEMPORARY_VISITOR(6, "临时访客");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_RENT_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_RENT_STATE enumType : EM_RENT_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_RENT_STATE enumType : EM_RENT_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

