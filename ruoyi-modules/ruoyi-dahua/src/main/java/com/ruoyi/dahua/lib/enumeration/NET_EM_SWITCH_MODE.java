package com.ruoyi.dahua.lib.enumeration;

/**
 * 切换模式
*/
public enum NET_EM_SWITCH_MODE
{
    /**
     * 不切换，总是使用白天配置
    */
    NET_EM_SWITCH_MODE_WIGHT(0, "不切换，总是使用白天配置"),
    /**
     * 根据亮度切换
    */
    NET_EM_SWITCH_MODE_LIGHT(1, "根据亮度切换"),
    /**
     * 根据时间切换
    */
    NET_EM_SWITCH_MODE_TIME(2, "根据时间切换"),
    /**
     * 不切换，总是使用夜晚配置
    */
    NET_EM_SWITCH_MODE_NIGHT(3, "不切换，总是使用夜晚配置"),
    /**
     * 使用普通配置
    */
    NET_EM_SWITCH_MODE_NORMAL(4, "使用普通配置"),
    /**
     * 按ICR(双滤光片切换器)的状态切换日夜
    */
    NET_EM_SWITCH_MODE_ICR(5, "按ICR(双滤光片切换器)的状态切换日夜"),
    /**
     * 多场景模式：按时间段关联光照条件切换图像场景
    */
    NET_EM_SWITCH_MODE_MULTISCENNE(6, "多场景模式：按时间段关联光照条件切换图像场景");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_SWITCH_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_SWITCH_MODE enumType : NET_EM_SWITCH_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_SWITCH_MODE enumType : NET_EM_SWITCH_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

