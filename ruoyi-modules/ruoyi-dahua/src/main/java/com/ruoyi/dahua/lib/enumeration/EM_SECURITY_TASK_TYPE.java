package com.ruoyi.dahua.lib.enumeration;

/**
 * 当前任务类型
*/
public enum EM_SECURITY_TASK_TYPE
{
    /**
     * 未知
    */
    EM_TYPE_STATUS_UNKNOWN(0, "未知"),
    /**
     * 当前无任务
    */
    EM_TYPE_STATUS_NONE(1, "当前无任务"),
    /**
     * 有导入任务
    */
    EM_TYPE_STATUS_IMPORT(2, "有导入任务"),
    /**
     * 有导出任务
    */
    EM_TYPE_STATUS_EXPORT(3, "有导出任务");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SECURITY_TASK_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SECURITY_TASK_TYPE enumType : EM_SECURITY_TASK_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SECURITY_TASK_TYPE enumType : EM_SECURITY_TASK_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

