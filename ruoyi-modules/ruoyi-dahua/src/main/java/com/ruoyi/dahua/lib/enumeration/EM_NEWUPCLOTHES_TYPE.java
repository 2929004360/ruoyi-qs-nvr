package com.ruoyi.dahua.lib.enumeration;

/**
 * 新上衣类型
*/
public enum EM_NEWUPCLOTHES_TYPE
{
    /**
     * 未知
    */
    EM_NEWUPCLOTHES_TYPE_UNKNOWN(0, "未知"),
    /**
     * 长袖
    */
    EM_NEWUPCLOTHES_TYPE_LONG_SLEEVE(1, "长袖"),
    /**
     * 短袖
    */
    EM_NEWUPCLOTHES_TYPE_SHORT_SLEEVE(2, "短袖"),
    /**
     * 长款大衣
    */
    EM_NEWUPCLOTHES_TYPE_LONGCOAT(3, "长款大衣"),
    /**
     * 夹克及牛仔服
    */
    EM_NEWUPCLOTHES_TYPE_JACKET_AND_JEANS(4, "夹克及牛仔服"),
    /**
     * T恤
    */
    EM_NEWUPCLOTHES_TYPE_TSHIRT(5, "T恤"),
    /**
     * 运动装
    */
    EM_NEWUPCLOTHES_TYPE_SPORTWEAR(6, "运动装"),
    /**
     * 羽绒服
    */
    EM_NEWUPCLOTHES_TYPE_DOWNJACKETS(7, "羽绒服"),
    /**
     * 衬衫
    */
    EM_NEWUPCLOTHES_TYPE_SHIRT(8, "衬衫"),
    /**
     * 连衣裙
    */
    EM_NEWUPCLOTHES_TYPE_DRESS(9, "连衣裙"),
    /**
     * 西装
    */
    EM_NEWUPCLOTHES_TYPE_SUIT(10, "西装"),
    /**
     * 毛衣
    */
    EM_NEWUPCLOTHES_TYPE_SWEATER(11, "毛衣"),
    /**
     * 无袖
    */
    EM_NEWUPCLOTHES_TYPE_SLEEVELESS(12, "无袖"),
    /**
     * 背心
    */
    EM_NEWUPCLOTHES_TYPE_VEST(13, "背心");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NEWUPCLOTHES_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NEWUPCLOTHES_TYPE enumType : EM_NEWUPCLOTHES_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NEWUPCLOTHES_TYPE enumType : EM_NEWUPCLOTHES_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

