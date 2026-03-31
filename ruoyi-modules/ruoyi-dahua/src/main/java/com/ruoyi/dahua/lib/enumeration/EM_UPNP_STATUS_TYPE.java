package com.ruoyi.dahua.lib.enumeration;

/**
 * UPnP状态类型
*/
public enum EM_UPNP_STATUS_TYPE
{
    /**
     * 状态未知
    */
    EM_UPNP_STATUS_UNKNOWN(0, "状态未知"),
    /**
     * 获取成功
    */
    EM_UPNP_STATUS_SUCCESS(1, "获取成功"),
    /**
     * 获取失败
    */
    EM_UPNP_STATUS_ERROR(2, "获取失败");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_UPNP_STATUS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_UPNP_STATUS_TYPE enumType : EM_UPNP_STATUS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_UPNP_STATUS_TYPE enumType : EM_UPNP_STATUS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

