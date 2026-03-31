package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 动物检测规则下的场景类型
 * @since ： Created in 2021/11/05 15:07
 */

public enum EM_DETECTION_SCENE_TYPE {
    /**
     * 未知
     */
    EM_DETECTION_SCENE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 兽类场景，可检测动物、人、车
     */
    EM_DETECTION_SCENE_TYPE_ANIMAL(1, "兽类场景，可检测动物、人、车"),
    /**
     * 鸟类场景，可检测鸟类，人、车
     */
    EM_DETECTION_SCENE_TYPE_BIRD(2, "鸟类场景，可检测鸟类，人、车"),
	/**
	 * 猪类场景，可检测猪只
	 */
    EM_DETECTION_SCENE_TYPE_PIG(3, "猪类场景，可检测猪只");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    EM_DETECTION_SCENE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_DETECTION_SCENE_TYPE enumType : EM_DETECTION_SCENE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_DETECTION_SCENE_TYPE enumType : EM_DETECTION_SCENE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_DETECTION_SCENE_TYPE getEnum(int value) {
        for (EM_DETECTION_SCENE_TYPE e : EM_DETECTION_SCENE_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_DETECTION_SCENE_TYPE.EM_DETECTION_SCENE_TYPE_UNKNOWN;
    }
}

