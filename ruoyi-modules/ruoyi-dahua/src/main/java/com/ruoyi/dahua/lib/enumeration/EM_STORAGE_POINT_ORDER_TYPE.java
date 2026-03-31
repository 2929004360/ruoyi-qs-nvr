package com.ruoyi.dahua.lib.enumeration;

/**
 * 断网续传上传顺序
*/
public enum EM_STORAGE_POINT_ORDER_TYPE
{
    /**
     * 未知
    */
    EM_STORAGE_POINT_ORDER_TYPE_UNKNOW(0, "未知"),
    /**
     * 正序，抓拍时间早的先上传
    */
    EM_STORAGE_POINT_ORDER_TYPE_ASCENT(1, "正序，抓拍时间早的先上传"),
    /**
     * 倒序，抓拍时间晚的先上传
    */
    EM_STORAGE_POINT_ORDER_TYPE_DESCENT(2, "倒序，抓拍时间晚的先上传");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STORAGE_POINT_ORDER_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STORAGE_POINT_ORDER_TYPE enumType : EM_STORAGE_POINT_ORDER_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STORAGE_POINT_ORDER_TYPE enumType : EM_STORAGE_POINT_ORDER_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

