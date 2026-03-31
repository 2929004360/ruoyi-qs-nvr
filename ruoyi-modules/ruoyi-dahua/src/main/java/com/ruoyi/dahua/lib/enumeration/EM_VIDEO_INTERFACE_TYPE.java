package com.ruoyi.dahua.lib.enumeration;

/**
 * 视频输入/输出通道接口类型
*/
public enum EM_VIDEO_INTERFACE_TYPE
{
    /**
     * 未知
    */
    EM_VIDEO_INTERFACE_TYPE_UNKNOWN(0, "未知"),
    /**
     * CVBS
    */
    EM_VIDEO_INTERFACE_TYPE_CVBS(1, "CVBS"),
    /**
     * VGA
    */
    EM_VIDEO_INTERFACE_TYPE_VGA(2, "VGA"),
    /**
     * DVI
    */
    EM_VIDEO_INTERFACE_TYPE_DVI(3, "DVI"),
    /**
     * HDMI
    */
    EM_VIDEO_INTERFACE_TYPE_HDMI(4, "HDMI"),
    /**
     * SDI
    */
    EM_VIDEO_INTERFACE_TYPE_SDI(5, "SDI");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_VIDEO_INTERFACE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_VIDEO_INTERFACE_TYPE enumType : EM_VIDEO_INTERFACE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_VIDEO_INTERFACE_TYPE enumType : EM_VIDEO_INTERFACE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

