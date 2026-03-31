package com.ruoyi.dahua.lib.enumeration;

/**
 * 输入通道类型
*/
public enum EM_CAMERA_CHANNEL_TYPE
{
    /**
     * 未知
    */
    EM_CAMERA_CHANNEL_TYPE_UNKNOWN(0, "未知"),
    /**
     * 本地通道（即物理通道）
    */
    EM_CAMERA_CHANNEL_TYPE_LOCAL(1, "本地通道（即物理通道）"),
    /**
     * 远程通道
    */
    EM_CAMERA_CHANNEL_TYPE_REMOTE(2, "远程通道"),
    /**
     * 保留32通道数(兼容二代设备协议)
    */
    EM_CAMERA_CHANNEL_TYPE_RESERVED32(3, "保留32通道数(兼容二代设备协议)"),
    /**
     * 输出通道
    */
    EM_CAMERA_CHANNEL_TYPE_OUTPUT(4, "输出通道"),
    /**
     * 模拟矩阵通道
    */
    EM_CAMERA_CHANNEL_TYPE_MATRIX(5, "模拟矩阵通道"),
    /**
     * 合成通道
    */
    EM_CAMERA_CHANNEL_TYPE_COMPOSE(6, "合成通道"),
    /**
     * 光纤级联通道
    */
    EM_CAMERA_CHANNEL_TYPE_CASCADE(7, "光纤级联通道");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CAMERA_CHANNEL_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CAMERA_CHANNEL_TYPE enumType : EM_CAMERA_CHANNEL_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CAMERA_CHANNEL_TYPE enumType : EM_CAMERA_CHANNEL_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

