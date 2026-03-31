package com.ruoyi.dahua.lib.enumeration;

/**
 * 分区打包类型
*/
public enum EM_STORAGE_PACKET_TYPE
{
    /**
     * 未知
    */
    EM_STORAGE_PACKET_UNKNOWN(0, "未知"),
    /**
     * 分区方式
    */
    EM_STORAGE_PACKET_PARTITION(1, "分区方式"),
    /**
     * 文件方式
    */
    EM_STORAGE_PACKET_FILE(2, "文件方式"),
    /**
     * DHOP方式
    */
    EM_STORAGE_PACKET_DHOP(3, "DHOP方式");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STORAGE_PACKET_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STORAGE_PACKET_TYPE enumType : EM_STORAGE_PACKET_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STORAGE_PACKET_TYPE enumType : EM_STORAGE_PACKET_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

