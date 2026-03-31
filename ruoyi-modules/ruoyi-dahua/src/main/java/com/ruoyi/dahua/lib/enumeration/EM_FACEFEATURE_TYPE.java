package com.ruoyi.dahua.lib.enumeration;

/**
 * ---------------------------------视频分析设备配置------------------------------------------, 目标属性类型
*/
public enum EM_FACEFEATURE_TYPE
{
    /**
     * 未知
    */
    EM_FACEFEATURE_UNKNOWN(0, "未知"),
    /**
     * 性别
    */
    EM_FACEFEATURE_SEX(1, "性别"),
    /**
     * 年龄
    */
    EM_FACEFEATURE_AGE(2, "年龄"),
    /**
     * 表情
    */
    EM_FACEFEATURE_EMOTION(3, "表情"),
    /**
     * 眼镜状态
    */
    EM_FACEFEATURE_GLASSES(4, "眼镜状态"),
    /**
     * 眼睛状态
    */
    EM_FACEFEATURE_EYE(5, "眼睛状态"),
    /**
     * 嘴巴状态
    */
    EM_FACEFEATURE_MOUTH(6, "嘴巴状态"),
    /**
     * 口罩状态
    */
    EM_FACEFEATURE_MASK(7, "口罩状态"),
    /**
     * 胡子状态
    */
    EM_FACEFEATURE_BEARD(8, "胡子状态"),
    /**
     * 魅力值
    */
    EM_FACEFEATURE_ATTRACTIVE(9, "魅力值");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_FACEFEATURE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_FACEFEATURE_TYPE enumType : EM_FACEFEATURE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_FACEFEATURE_TYPE enumType : EM_FACEFEATURE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

