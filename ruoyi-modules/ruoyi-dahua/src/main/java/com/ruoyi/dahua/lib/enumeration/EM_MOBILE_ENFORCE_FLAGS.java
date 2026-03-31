package com.ruoyi.dahua.lib.enumeration;

/**
 * 录像标志
*/
public enum EM_MOBILE_ENFORCE_FLAGS
{
    /**
     * 未知
    */
    EM_MOBILE_ENFORCE_FLAGS_UNKNOWN(0, "未知"),
    /**
     * 普通录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_TIMING(1, "普通录像和图片"),
    /**
     * 事件录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_EVENT(2, "事件录像和图片"),
    /**
     * 锁定录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_MARKED(3, "锁定录像和图片"),
    /**
     * 卡号录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_CARD(4, "卡号录像和图片"),
    /**
     * 标签录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_TAG(5, "标签录像和图片"),
    /**
     * 限制录像和图片
    */
    EM_MOBILE_ENFORCE_FLAGS_RESTRICT(6, "限制录像和图片");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MOBILE_ENFORCE_FLAGS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MOBILE_ENFORCE_FLAGS enumType : EM_MOBILE_ENFORCE_FLAGS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MOBILE_ENFORCE_FLAGS enumType : EM_MOBILE_ENFORCE_FLAGS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

