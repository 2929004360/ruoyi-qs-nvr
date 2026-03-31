package com.ruoyi.dahua.lib.enumeration;

/**
 * 已经定义的访客信息字段的key值
*/
public enum NET_EM_DEFINED_VISITOR_KEY
{
    /**
     * 未知类型
    */
    NET_EM_DEFINED_VISITOR_KEY_UNKNOWN(0, "未知类型"),
    /**
     * 访客证件号
    */
    NET_EM_DEFINED_VISITOR_KEY_ID_NUMBER(1, "访客证件号"),
    /**
     * 访客证件类型
    */
    NET_EM_DEFINED_VISITOR_KEY_ID_TYPE(2, "访客证件类型"),
    /**
     * 来访开始时间
    */
    NET_EM_DEFINED_VISITOR_KEY_STARTTIME(3, "来访开始时间"),
    /**
     * 来访结束时间
    */
    NET_EM_DEFINED_VISITOR_KEY_ENDTIME(4, "来访结束时间"),
    /**
     * 被访人姓名
    */
    NET_EM_DEFINED_VISITOR_KEY_INTERVIEWEE_NAME(5, "被访人姓名"),
    /**
     * 被访人部门
    */
    NET_EM_DEFINED_VISITOR_KEY_INTERVIEWEE_DEPARTMENT(6, "被访人部门"),
    /**
     * 访客姓名
    */
    NET_EM_DEFINED_VISITOR_KEY_NAME(7, "访客姓名"),
    /**
     * 访客MZ
    */
    NET_EM_DEFINED_VISITOR_KEY_MZ(8, "访客MZ"),
    /**
     * 访客性别
    */
    NET_EM_DEFINED_VISITOR_KEY_GENDER(9, "访客性别"),
    /**
     * 访客联系电话
    */
    NET_EM_DEFINED_VISITOR_KEY_PHONE(10, "访客联系电话"),
    /**
     * 来访单位
    */
    NET_EM_DEFINED_VISITOR_KEY_UNIT(11, "来访单位"),
    /**
     * 访客住址
    */
    NET_EM_DEFINED_VISITOR_KEY_ADDRESS(12, "访客住址"),
    /**
     * 访客卡号
    */
    NET_EM_DEFINED_VISITOR_KEY_CARDID(13, "访客卡号"),
    /**
     * 访客车牌号
    */
    NET_EM_DEFINED_VISITOR_KEY_PLATE_NUMBER(14, "访客车牌号"),
    /**
     * 访客二维码
    */
    NET_EM_DEFINED_VISITOR_KEY_QRCODE(15, "访客二维码"),
    /**
     * 访客照片
    */
    NET_EM_DEFINED_VISITOR_KEY_PICTURE(16, "访客照片"),
    /**
     * 来访事由
    */
    NET_EM_DEFINED_VISITOR_KEY_REASON(17, "来访事由"),
    /**
     * 来访人数
    */
    NET_EM_DEFINED_VISITOR_KEY_NUMBER(18, "来访人数");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_DEFINED_VISITOR_KEY(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_DEFINED_VISITOR_KEY enumType : NET_EM_DEFINED_VISITOR_KEY.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_DEFINED_VISITOR_KEY enumType : NET_EM_DEFINED_VISITOR_KEY.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

