package com.ruoyi.dahua.lib.enumeration;

/**
 * APP的证书状态
*/
public enum EM_APP_LICENSE_STATE
{
    /**
     * 未知
    */
    EM_APP_LICENSE_STATE_UNKNOEN(0, "未知"),
    /**
     * 证书试用期
    */
    EM_APP_LICENSE_STATE_IN_TRAL(1, "证书试用期"),
    /**
     * 证书正式合法
    */
    EM_APP_LICENSE_STATE_IN_LICENSE(2, "证书正式合法"),
    /**
     * 证书过期
    */
    EM_APP_LICENSE_STATE_EXPIRED(3, "证书过期");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_APP_LICENSE_STATE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_APP_LICENSE_STATE enumType : EM_APP_LICENSE_STATE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_APP_LICENSE_STATE enumType : EM_APP_LICENSE_STATE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

