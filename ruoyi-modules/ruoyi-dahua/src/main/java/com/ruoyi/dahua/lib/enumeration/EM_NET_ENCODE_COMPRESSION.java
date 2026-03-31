package com.ruoyi.dahua.lib.enumeration;

/**
 * 码流的编码方式
*/
public enum EM_NET_ENCODE_COMPRESSION
{
    /**
     * 未知
    */
    EM_NET_ENCODE_COMPRESSION_UNKNOWN(0, "未知"),
    /**
     * H264
    */
    EM_NET_ENCODE_COMPRESSION_H264(1, "H264"),
    /**
     * MPEG4
    */
    EM_NET_ENCODE_COMPRESSION_MPEG4(2, "MPEG4"),
    /**
     * MJPEG
    */
    EM_NET_ENCODE_COMPRESSION_MJPEG(3, "MJPEG"),
    /**
     * SVAC
    */
    EM_NET_ENCODE_COMPRESSION_SVAC(4, "SVAC"),
    /**
     * HIK
    */
    EM_NET_ENCODE_COMPRESSION_HIK(5, "HIK"),
    /**
     * H265
    */
    EM_NET_ENCODE_COMPRESSION_H265(6, "H265");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_ENCODE_COMPRESSION(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_ENCODE_COMPRESSION enumType : EM_NET_ENCODE_COMPRESSION.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_ENCODE_COMPRESSION enumType : EM_NET_ENCODE_COMPRESSION.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

