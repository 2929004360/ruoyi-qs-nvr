package com.ruoyi.dahua.lib.enumeration;

/**
 * 事件的数据类型
*/
public enum NETAEM_EVENT_TYPE
{
    /**
     * 未知
    */
    EM_EVENT_TYPE_UNKNOWN(0, "未知"),
    /**
     * 实时数据
    */
    EM_EVENT_TYPE_REAL(1, "实时数据"),
    /**
     * 报警数据
    */
    EM_EVENT_TYPE_ALARM(2, "报警数据");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NETAEM_EVENT_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NETAEM_EVENT_TYPE enumType : NETAEM_EVENT_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NETAEM_EVENT_TYPE enumType : NETAEM_EVENT_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

