package com.ruoyi.dahua.lib.enumeration;

/**
 * 支持json订阅的接口
*/
public enum EM_SUPPORT_ATTACH_TYPE
{
    /**
     * CLIENT_StartListenEx, 订阅参数对应NET_ATTACH_NORMAL_STARTLISTENEX_PARAM，退订参数为NULL
    */
    EM_STARTLISTENEX(0, "CLIENT_StartListenEx, 订阅参数对应NET_ATTACH_NORMAL_STARTLISTENEX_PARAM，退订参数为NULL");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SUPPORT_ATTACH_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SUPPORT_ATTACH_TYPE enumType : EM_SUPPORT_ATTACH_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SUPPORT_ATTACH_TYPE enumType : EM_SUPPORT_ATTACH_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

