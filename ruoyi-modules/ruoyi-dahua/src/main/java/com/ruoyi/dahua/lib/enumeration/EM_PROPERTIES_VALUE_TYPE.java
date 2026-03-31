package com.ruoyi.dahua.lib.enumeration;


/**
 * @author 260611
 * @description 属性值类型
 * @date 2022/04/20 11:28:15
 */
public enum EM_PROPERTIES_VALUE_TYPE {
    /**
     * 未知
     */
    EM_PROPERTIES_VALUE_UNKNOWN(0, "未知"),
    /**
     * int类型,1,100,1000
     */
    EM_PROPERTIES_VALUE_INT(1, "int类型,1,100,1000"),
    /**
     * bool类型,true,false
     */
    EM_PROPERTIES_VALUE_BOOL(2, "bool类型,true,false"),
    /**
     * string类型, 1 , 100 , 1000
     */
    EM_PROPERTIES_VALUE_STRING(3, "string类型, 1 , 100 , 1000 "),
    /**
     * int数组类型, [1,2,3]
     */
    EM_PROPERTIES_VALUE_INTARRAY(4, "int数组类型, [1,2,3] ");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PROPERTIES_VALUE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PROPERTIES_VALUE_TYPE enumType : EM_PROPERTIES_VALUE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PROPERTIES_VALUE_TYPE enumType : EM_PROPERTIES_VALUE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_PROPERTIES_VALUE_TYPE getEnum(int value) {
        for (EM_PROPERTIES_VALUE_TYPE e : EM_PROPERTIES_VALUE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_PROPERTIES_VALUE_TYPE.EM_PROPERTIES_VALUE_UNKNOWN;
    }
}

