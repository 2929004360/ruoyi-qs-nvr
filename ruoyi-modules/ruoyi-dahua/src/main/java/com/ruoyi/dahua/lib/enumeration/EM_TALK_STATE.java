package com.ruoyi.dahua.lib.enumeration;

/**
 * 语音对讲状态
*/
public enum EM_TALK_STATE
{
    /**
     * 未知
    */
    EM_TALK_STATE_UNKNOWN(0, "未知"),
    /**
     * 主叫
    */
    EM_TALK_STATE_INVITING(1, "主叫"),
    /**
     * 被叫
    */
    EM_TALK_STATE_RINGING(2, "被叫"),
    /**
     * 应答
    */
    EM_TALK_STATE_ANSWER(3, "应答"),
    /**
     * 拒绝
    */
    EM_TALK_STATE_REFUSE(4, "拒绝"),
    /**
     * 挂断
    */
    EM_TALK_STATE_HANGUP(5, "挂断"),
    /**
     * 忙碌
    */
    EM_TALK_STATE_BUSYING(6, "忙碌"),
    /**
     * 取消呼叫
    */
    EM_TALK_STATE_CANCEL(7, "取消呼叫");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_TALK_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TALK_STATE enumType : EM_TALK_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TALK_STATE enumType : EM_TALK_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

