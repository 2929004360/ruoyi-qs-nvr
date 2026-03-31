package com.ruoyi.dahua.lib.enumeration;

/**
 * 非机动车子类型
*/
public enum EM_NONMOTOR_SUBTYPE
{
    /**
     * 未知
    */
    EM_NONMOTOR_SUBTYPE_UNKNOWN(0, "未知"),
    /**
     * 非机动车
    */
    EM_NONMOTOR_SUBTYPE_NONMOTOR(1, "非机动车"),
    /**
     * 自行车
    */
    EM_NONMOTOR_SUBTYPE_BICYCLE(2, "自行车"),
    /**
     * 三轮车
    */
    EM_NONMOTOR_SUBTYPE_TRICYCLE(3, "三轮车"),
    /**
     * 摩托车
    */
    EM_NONMOTOR_SUBTYPE_MOTORCYCLE(4, "摩托车"),
    /**
     * (两) 三轮摩托车
    */
    EM_NONMOTOR_SUBTYPE_DUALTRIWHEEL(5, "(两) 三轮摩托车"),
    /**
     * 轻便摩托车
    */
    EM_NONMOTOR_SUBTYPE_LIGHT_MOTORCYCLE(6, "轻便摩托车"),
    /**
     * 使馆摩托车
    */
    EM_NONMOTOR_SUBTYPE_EMBASSY_MOTORCYCLE(7, "使馆摩托车"),
    /**
     * 领馆摩托车
    */
    EM_NONMOTOR_SUBTYPE_MARGINAL_MOTORCYCLE(8, "领馆摩托车"),
    /**
     * 境外摩托车
    */
    EM_NONMOTOR_SUBTYPE_AREAOUT_MOTORCYCLE(9, "境外摩托车"),
    /**
     * 外籍摩托车
    */
    EM_NONMOTOR_SUBTYPE_FOREIGN_MOTORCYCLE(10, "外籍摩托车"),
    /**
     * 试验摩托车
    */
    EM_NONMOTOR_SUBTYPE_TRIAL_MOTORCYCLE(11, "试验摩托车"),
    /**
     * 教练摩托车
    */
    EM_NONMOTOR_SUBTYPE_COACH_MOTORCYCLE(12, "教练摩托车"),
    /**
     * 行人
    */
    EM_NONMOTOR_SUBTYPE_PASSERBY(13, "行人");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NONMOTOR_SUBTYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NONMOTOR_SUBTYPE enumType : EM_NONMOTOR_SUBTYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NONMOTOR_SUBTYPE enumType : EM_NONMOTOR_SUBTYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

