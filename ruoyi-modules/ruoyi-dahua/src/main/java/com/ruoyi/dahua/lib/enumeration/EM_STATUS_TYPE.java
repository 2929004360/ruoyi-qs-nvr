package com.ruoyi.dahua.lib.enumeration;

/**
 * 设备导入文件状态
*/
public enum EM_STATUS_TYPE
{
    /**
     * 未知
    */
    EM_IMPORT_STATUS_UNKNOWN(0, "未知"),
    /**
     * 开始
    */
    EM_IMPORT_STATUS_BEGIN(1, "开始"),
    /**
     * 追加
    */
    EM_IMPORT_STATUS_APPEND(2, "追加"),
    /**
     * 结束
    */
    EM_IMPORT_STATUS_END(3, "结束");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_STATUS_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_STATUS_TYPE enumType : EM_STATUS_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_STATUS_TYPE enumType : EM_STATUS_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

