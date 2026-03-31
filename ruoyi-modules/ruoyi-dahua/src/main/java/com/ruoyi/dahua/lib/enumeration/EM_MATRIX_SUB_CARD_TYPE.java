package com.ruoyi.dahua.lib.enumeration;

/**
 * 子卡类型
*/
public enum EM_MATRIX_SUB_CARD_TYPE
{
    /**
     * 未知
    */
    EM_MATRIX_SUB_CARD_TYPE_UNKNOWN(0, "未知"),
    /**
     * 主卡
    */
    EM_MATRIX_SUB_CARD_TYPE_MAIN(1, "主卡"),
    /**
     * 输入卡
    */
    EM_MATRIX_SUB_CARD_TYPE_INPUT(2, "输入卡"),
    /**
     * 输出卡
    */
    EM_MATRIX_SUB_CARD_TYPE_OUTPUT(3, "输出卡"),
    /**
     * 编码卡
    */
    EM_MATRIX_SUB_CARD_TYPE_ENCODE(4, "编码卡"),
    /**
     * 解码卡
    */
    EM_MATRIX_SUB_CARD_TYPE_DECODE(5, "解码卡"),
    /**
     * 级联卡
    */
    EM_MATRIX_SUB_CARD_TYPE_CASCADE(6, "级联卡"),
    /**
     * 智能卡
    */
    EM_MATRIX_SUB_CARD_TYPE_INTELLIGENT(7, "智能卡"),
    /**
     * 报警卡
    */
    EM_MATRIX_SUB_CARD_TYPE_ALARM(8, "报警卡"),
    /**
     * 硬Raid卡
    */
    EM_MATRIX_SUB_CARD_TYPE_RAID(9, "硬Raid卡"),
    /**
     * 网络解码卡
    */
    EM_MATRIX_SUB_CARD_TYPE_NETDECODE(10, "网络解码卡"),
    /**
     * 网络编码卡
    */
    EM_MATRIX_SUB_CARD_TYPE_NETENCODE(11, "网络编码卡"),
    /**
     * 网络交换卡
    */
    EM_MATRIX_SUB_CARD_TYPE_NETSWITCH(12, "网络交换卡"),
    /**
     * 触摸板
    */
    EM_MATRIX_SUB_CARD_TYPE_TOUCHSCREEN(13, "触摸板"),
    /**
     * 备份板
    */
    EM_MATRIX_SUB_CARD_TYPE_BACKUP(14, "备份板");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MATRIX_SUB_CARD_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MATRIX_SUB_CARD_TYPE enumType : EM_MATRIX_SUB_CARD_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MATRIX_SUB_CARD_TYPE enumType : EM_MATRIX_SUB_CARD_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

