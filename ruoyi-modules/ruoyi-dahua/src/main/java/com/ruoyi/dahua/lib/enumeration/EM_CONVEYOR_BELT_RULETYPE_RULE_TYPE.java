package com.ruoyi.dahua.lib.enumeration;

/**
 * 传送带运动状态检测类型
*/
public enum EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE
{
    /**
     * 未知
    */
    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 物品从有到无
    */
    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE_STH_TO_NTH(1, "物品从有到无"),
    /**
     * 物品从无到有
    */
    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE_NTH_TO_STH(2, "物品从无到有"),
    /**
     * 运动到静止
    */
    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE_MOVE_TO_STOP(3, "运动到静止"),
    /**
     * 静止到运动
    */
    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE_STOP_TO_MOVE(4, "静止到运动");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE enumType : EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE enumType : EM_CONVEYOR_BELT_RULETYPE_RULE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

