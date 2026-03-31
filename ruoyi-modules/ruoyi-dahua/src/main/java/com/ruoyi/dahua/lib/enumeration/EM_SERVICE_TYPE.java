package com.ruoyi.dahua.lib.enumeration;

/**
 * UPnP服务类型
*/
public enum EM_SERVICE_TYPE
{
    /**
     * 未知服务
    */
    EM_SERVICE_UNKNOWN(-1, "未知服务"),
    /**
     * WEB服务
    */
    EM_SERVICE_WEB(0, "WEB服务"),
    /**
     * 私有协议服务
    */
    EM_SERVICE_PRIV(1, "私有协议服务"),
    /**
     * RTSP服务
    */
    EM_SERVICE_RTSP(2, "RTSP服务"),
    /**
     * HTTPS服务
    */
    EM_SERVICE_HTTPS(3, "HTTPS服务"),
    /**
     * SNMP服务
    */
    EM_SERVICE_SNMP(4, "SNMP服务");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_SERVICE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SERVICE_TYPE enumType : EM_SERVICE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SERVICE_TYPE enumType : EM_SERVICE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

