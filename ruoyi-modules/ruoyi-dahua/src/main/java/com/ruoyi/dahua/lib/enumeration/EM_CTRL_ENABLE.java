package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 报警使能控制方式枚举类型
 * @origin autoTool
 * @date 2023/11/01 20:42:35
 */
public enum EM_CTRL_ENABLE {
    /**
     * 不控制使能
     */
    EM_CTRL_NORMAL(0, "不控制使能"),
    /**
     * 总是使能
     */
    EM_CTRL_ALWAYS_EN(1, "总是使能"),
    /**
     * 旁路
     */
    EM_CTRL_ONCE_DIS(2, "旁路"),
    /**
     * 移除
     */
    EM_CTRL_ALWAYS_DIS(3, "移除"),
    /**
     * 枚举类型总数
     */
    EM_CTRL_NU(4, "枚举类型总数");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CTRL_ENABLE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CTRL_ENABLE enumType : EM_CTRL_ENABLE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CTRL_ENABLE enumType : EM_CTRL_ENABLE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_CTRL_ENABLE getEnum(int value) {
        for (EM_CTRL_ENABLE e : EM_CTRL_ENABLE.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return EM_CTRL_ENABLE.EM_CTRL_NORMAL;
    }
}

