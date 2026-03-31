package com.ruoyi.dahua.lib.enumeration;

/**
 * 当前任务状态
*/
public enum EM_SECURITY_TASK_STATUS
{
    /**
     * 未知
    */
    EM_TASK_STATUS_UNKNOWN(0, "未知"),
    /**
     * 任务执行中
    */
    EM_TASK_STATUS_RUNNING(1, "任务执行中"),
    /**
     * 任务执行成功
    */
    EM_TASK_STATUS_SUCCEEDED(2, "任务执行成功"),
    /**
     * 任务执行失败
    */
    EM_TASK_STATUS_FAILED(3, "任务执行失败");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SECURITY_TASK_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SECURITY_TASK_STATUS enumType : EM_SECURITY_TASK_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITY_TASK_STATUS enumType : EM_SECURITY_TASK_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

