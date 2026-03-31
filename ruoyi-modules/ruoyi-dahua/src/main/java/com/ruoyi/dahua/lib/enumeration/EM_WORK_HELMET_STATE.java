package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 安全帽佩戴状态
 * @since ： Created in 2022/03/10 11:17
 */

public enum EM_WORK_HELMET_STATE {
    /**
     * 未知
     */
    EM_HELMET_STATE_UNKNOWN(0, "未知"),
    /**
     * 不带安全帽
     */
    EM_HELMET_STATE_NOTWEAR(1, "不带安全帽"),
    /**
     * 有带安全帽
     */
    EM_HELMET_STATE_WEAR(2, "有带安全帽"),
    /**
     * 不存在指定颜色的安全帽
     */
    EM_HELMET_STATE_INCONFORMITY_COLOR(3, "不存在指定颜色的安全帽");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_WORK_HELMET_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WORK_HELMET_STATE enumType : EM_WORK_HELMET_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WORK_HELMET_STATE enumType : EM_WORK_HELMET_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_WORK_HELMET_STATE getEnum(int value) {
        for (EM_WORK_HELMET_STATE e : EM_WORK_HELMET_STATE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WORK_HELMET_STATE.EM_HELMET_STATE_UNKNOWN;
    }
}

