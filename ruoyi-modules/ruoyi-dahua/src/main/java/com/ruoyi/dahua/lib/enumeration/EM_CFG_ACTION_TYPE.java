package com.ruoyi.dahua.lib.enumeration;

/**
 * 动作类型
*/
public enum EM_CFG_ACTION_TYPE
{
    /**
     * 未知
    */
    EM_CFG_ACTION_TYPE_UNKNOWN(0, "未知"),
    /**
     * 剧烈运动
    */
    EM_CFG_ACTION_TYPE_DRASTIC_MOTION(1, "剧烈运动"),
    /**
     * 单人打砸
    */
    EM_CFG_ACTION_TYPE_SINGLE_FIGHT(2, "单人打砸"),
    /**
     * 多人打架
    */
    EM_CFG_ACTION_TYPE_MULTI_FIGHT(3, "多人打架");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CFG_ACTION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CFG_ACTION_TYPE enumType : EM_CFG_ACTION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CFG_ACTION_TYPE enumType : EM_CFG_ACTION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

