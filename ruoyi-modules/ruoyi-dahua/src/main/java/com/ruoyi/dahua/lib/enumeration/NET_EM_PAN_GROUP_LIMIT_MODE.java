package com.ruoyi.dahua.lib.enumeration;

/**
 * 水平旋转组边界模式
*/
public enum NET_EM_PAN_GROUP_LIMIT_MODE
{
    /**
     * 左边界
    */
    NET_EM_PAN_GROUP_LIMIT_MODE_LEFT(1, "左边界"),
    /**
     * 右边界
    */
    NET_EM_PAN_GROUP_LIMIT_MODE_RIGHT(2, "右边界"),
    /**
     * 上边界
    */
    NET_EM_PAN_GROUP_LIMIT_MODE_UP(3, "上边界"),
    /**
     * 下边界
    */
    NET_EM_PAN_GROUP_LIMIT_MODE_DOWN(4, "下边界");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    NET_EM_PAN_GROUP_LIMIT_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_PAN_GROUP_LIMIT_MODE enumType : NET_EM_PAN_GROUP_LIMIT_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_PAN_GROUP_LIMIT_MODE enumType : NET_EM_PAN_GROUP_LIMIT_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

