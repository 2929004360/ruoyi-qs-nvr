package com.ruoyi.dahua.lib.enumeration;

/**
 * 端口映射状态类型
*/
public enum EM_PORTMAP_STATUS_TYPE
{
    /**
     * 状态未知
    */
    EM_PORTMAP_STATUS_UNKNOWN(0, "状态未知"),
    /**
     * 映射成功
    */
    EM_PORTMAP_STATUS_SUCCESS(1, "映射成功"),
    /**
     * 映射失败
    */
    EM_PORTMAP_STATUS_FAILED(2, "映射失败");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PORTMAP_STATUS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PORTMAP_STATUS_TYPE enumType : EM_PORTMAP_STATUS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PORTMAP_STATUS_TYPE enumType : EM_PORTMAP_STATUS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

