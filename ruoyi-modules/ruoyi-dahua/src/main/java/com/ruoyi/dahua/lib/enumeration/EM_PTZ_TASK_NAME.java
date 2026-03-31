package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 421657
 * @description 任务名称
 * @origin autoTool
 * @date 2023/10/16 19:23:11
 */
public enum EM_PTZ_TASK_NAME {
    /**
     * 未知
     */
    NET_EM_PTZ_TASK_NAME_UNKNOWN(0, "未知"),
    /**
     * 定时任务
     */
    NET_EM_PTZ_TASK_NAME_AUTOMOVEMENT(1, "定时任务"),
    /**
     * 空闲任务
     */
    NET_EM_PTZ_TASK_NAME_IDLEMOTION(2, "空闲任务"),
    /**
     * 开机动作
     */
    NET_EM_PTZ_TASK_NAME_POWERUP(3, "开机动作"),
    /**
     * 云台联动
     */
    NET_EM_PTZ_TASK_NAME_LINK(4, "云台联动"),
    /**
     * 智能
     */
    NET_EM_PTZ_TASK_NAME_INTELIGENCE(5, "智能"),
    /**
     * 手动
     */
    NET_EM_PTZ_TASK_NAME_MANUAL(6, "手动");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PTZ_TASK_NAME(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PTZ_TASK_NAME enumType : EM_PTZ_TASK_NAME.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PTZ_TASK_NAME enumType : EM_PTZ_TASK_NAME.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_PTZ_TASK_NAME getEnum(int value) {
        for (EM_PTZ_TASK_NAME e : EM_PTZ_TASK_NAME.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return EM_PTZ_TASK_NAME.NET_EM_PTZ_TASK_NAME_UNKNOWN;
    }
}

