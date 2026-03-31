package com.ruoyi.dahua.lib.enumeration;

/**
 * Onvif 用户所在组
*/
public enum EM_GROUP_TYPE
{
    /**
     * UnKnown
    */
    EM_GROUP_TYPE_UNKNOWN(0, "UnKnown"),
    /**
     * admin
    */
    EM_GROUP_TYPE_ADMIN(1, "admin"),
    /**
     * operator
    */
    EM_GROUP_TYPE_OPERATOR(2, "operator"),
    /**
     * user
    */
    EM_GROUP_TYPE_USER(3, "user"),
    /**
     * anonymous
    */
    EM_GROUP_TYPE_ANONYMOUS(4, "anonymous");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_GROUP_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_GROUP_TYPE enumType : EM_GROUP_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_GROUP_TYPE enumType : EM_GROUP_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

