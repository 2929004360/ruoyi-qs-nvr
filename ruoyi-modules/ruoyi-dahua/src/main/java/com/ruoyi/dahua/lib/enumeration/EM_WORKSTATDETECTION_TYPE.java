package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 作业统计检测类型
 * @since ： Created in 2022/03/08 15:25
 */

public enum EM_WORKSTATDETECTION_TYPE {
    /**
     * 未知
     */
    EM_WORKSTATDETECTION_UNKNOWN(0, "未知"),
    /**
     * 脱岗检测
     */
    EM_WORKSTATDETECTION_NO_WORKER(1, "脱岗检测"),
    /**
     * 单人作业
     */
    EM_WORKSTATDETECTION_SINGLE_WORKER(2, "单人作业"),
    /**
     * 工作负责人脱岗检测
     */
    EM_WORKSTATDETECTION_NO_WORKER_LEADER(3, "工作负责人脱岗检测"),
    /**
     * 手部消毒检测
     */
    EM_WORKSTATDETECTION_HAND_DISINFECTION(4, "手部消毒检测"),
    /**
     * 蘸脚动作检测
     */
    EM_WORKSTATDETECTION_FOOT_DIP(5, "蘸脚动作检测"),
    /**
     * 换鞋动作检测
     */
    EM_WORKSTATDETECTION_SHOES_CHANGE(6, "换鞋动作检测");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_WORKSTATDETECTION_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WORKSTATDETECTION_TYPE enumType : EM_WORKSTATDETECTION_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WORKSTATDETECTION_TYPE enumType : EM_WORKSTATDETECTION_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_WORKSTATDETECTION_TYPE getEnum(int value) {
        for (EM_WORKSTATDETECTION_TYPE e : EM_WORKSTATDETECTION_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_WORKSTATDETECTION_TYPE.EM_WORKSTATDETECTION_UNKNOWN;
    }
}

