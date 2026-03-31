package com.ruoyi.dahua.lib.enumeration;

/**
 * 非机动车属性类型
*/
public enum EM_CFG_NONMOTOR_FEATURE
{
    /**
     * 头盔
    */
    EM_CFG_NONMOTOR_FEATURE_HELMET(0, "头盔"),
    /**
     * 车上人数
    */
    EM_CFG_NONMOTOR_FEATURE_NUMOFCYCLING(1, "车上人数"),
    /**
     * 车身颜色
    */
    EM_CFG_NONMOTOR_FEATURE_NONMOTORCOLOR(2, "车身颜色"),
    /**
     * 性别
    */
    EM_CFG_NONMOTOR_FEATURE_SEX(3, "性别"),
    /**
     * 年龄
    */
    EM_CFG_NONMOTOR_FEATURE_AGE(4, "年龄"),
    /**
     * 帽子
    */
    EM_CFG_NONMOTOR_FEATURE_HAT(5, "帽子"),
    /**
     * 背包
    */
    EM_CFG_NONMOTOR_FEATURE_BAG(6, "背包"),
    /**
     * 手提包
    */
    EM_CFG_NONMOTOR_FEATURE_CARRIERBAG(7, "手提包"),
    /**
     * 雨伞
    */
    EM_CFG_NONMOTOR_FEATURE_UMBRELLA(8, "雨伞"),
    /**
     * 上衣颜色
    */
    EM_CFG_NONMOTOR_FEATURE_UPPERBODYCOLOR(9, "上衣颜色"),
    /**
     * 下衣颜色
    */
    EM_CFG_NONMOTOR_FEATURE_LOWERBODYCOLOR(10, "下衣颜色"),
    /**
     * 上衣类型
    */
    EM_CFG_NONMOTOR_FEATURE_UPCLOTHES(11, "上衣类型"),
    /**
     * 下衣类型
    */
    EM_CFG_NONMOTOR_FEATURE_DOWNCLOTHES(12, "下衣类型"),
    /**
     * 车类型
    */
    EM_CFG_NONMOTOR_FEATURE_CATEGORY(13, "车类型");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_CFG_NONMOTOR_FEATURE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_CFG_NONMOTOR_FEATURE enumType : EM_CFG_NONMOTOR_FEATURE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_CFG_NONMOTOR_FEATURE enumType : EM_CFG_NONMOTOR_FEATURE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

