package com.ruoyi.dahua.lib.enumeration;

/**
 * 远程文件访问协议
*/
public enum EM_REMOTE_FILE_PROTOCOL_TYPE
{
    /**
     * 未知
    */
    EM_REMOTE_FILE_PROTOCOL_UNKNOWN(0, "未知"),
    /**
     * RTSP
    */
    EM_REMOTE_FILE_PROTOCOL_RTSP(1, "RTSP"),
    /**
     * RabbitMq
    */
    EM_REMOTE_FILE_PROTOCOL_RABBITMQ(2, "RabbitMq"),
    /**
     * HTTP
    */
    EM_REMOTE_FILE_PROTOCOL_HTTP(3, "HTTP"),
    /**
     * Private
    */
    EM_REMOTE_FILE_PROTOCOL_PRIVATE(4, "Private"),
    /**
     * Onvif
    */
    EM_REMOTE_FILE_PROTOCOL_ONVIF(5, "Onvif"),
    /**
     * GB28181
    */
    EM_REMOTE_FILE_PROTOCOL_GB28181(6, "GB28181"),
    EM_REMOTE_FILE_PROTOCOL_HIKVISION(7, ""),
    EM_REMOTE_FILE_PROTOCOL_BSCP(8, ""),
    /**
     * ActiveMq
    */
    EM_REMOTE_FILE_PROTOCOL_ACTIVEMQ(9, "ActiveMq");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_REMOTE_FILE_PROTOCOL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_REMOTE_FILE_PROTOCOL_TYPE enumType : EM_REMOTE_FILE_PROTOCOL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_REMOTE_FILE_PROTOCOL_TYPE enumType : EM_REMOTE_FILE_PROTOCOL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

