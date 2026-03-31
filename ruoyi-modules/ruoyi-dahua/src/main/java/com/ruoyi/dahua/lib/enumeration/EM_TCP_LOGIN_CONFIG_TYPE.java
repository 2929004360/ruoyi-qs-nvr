package com.ruoyi.dahua.lib.enumeration;

/**
 * 连接类型
*/
public enum EM_TCP_LOGIN_CONFIG_TYPE
{
    /**
     * 未知配置
    */
    EM_TCP_LOGIN_CONFIG_TYPE_UNKNOWN(0, "未知配置"),
    /**
     * 组播配置
    */
    EM_TCP_LOGIN_CONFIG_TYPE_MULTICAST(1, "组播配置");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_TCP_LOGIN_CONFIG_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_TCP_LOGIN_CONFIG_TYPE enumType : EM_TCP_LOGIN_CONFIG_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_TCP_LOGIN_CONFIG_TYPE enumType : EM_TCP_LOGIN_CONFIG_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

