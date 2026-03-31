package com.ruoyi.dahua.lib.enumeration;

/**
 * UPnP配置模式
*/
public enum EM_CONFIGURATION_MODE
{
    /**
     * 未知模式
    */
    EM_CONFIGURATION_UNKNOWN(-1, "未知模式"),
    /**
     * 手动配置
    */
    EM_CONFIGURATION_MANUAL(0, "手动配置"),
    /**
     * 自动配置
    */
    EM_CONFIGURATION_AUTO(1, "自动配置");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CONFIGURATION_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CONFIGURATION_MODE enumType : EM_CONFIGURATION_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CONFIGURATION_MODE enumType : EM_CONFIGURATION_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

