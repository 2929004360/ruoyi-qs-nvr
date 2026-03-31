package com.ruoyi.dahua.lib.enumeration;

/**
 * 日志等级
*/
public enum EM_DBGINFO_LEVEL
{
    /**
     * 无打印
    */
    EM_DBGINFO_LEVEL_NOPRINT(0, "无打印"),
    /**
     * 致命
    */
    EM_DBGINFO_LEVEL_FATAL(1, "致命"),
    /**
     * 错误
    */
    EM_DBGINFO_LEVEL_ERROR(2, "错误"),
    /**
     * 警告
    */
    EM_DBGINFO_LEVEL_WARN(3, "警告"),
    /**
     * 信息
    */
    EM_DBGINFO_LEVEL_INFO(4, "信息"),
    /**
     * 跟踪
    */
    EM_DBGINFO_LEVEL_TRACE(5, "跟踪"),
    /**
     * 调试
    */
    EM_DBGINFO_LEVEL_DEBUG(6, "调试");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_DBGINFO_LEVEL(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DBGINFO_LEVEL enumType : EM_DBGINFO_LEVEL.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DBGINFO_LEVEL enumType : EM_DBGINFO_LEVEL.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

