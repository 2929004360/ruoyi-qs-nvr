package com.ruoyi.dahua.lib.enumeration;

/**
 * 场景类型
*/
public enum EM_PARKING_SPACE_SCENE_TYPE
{
    /**
     * 未知
    */
    EM_PARKING_SPACE_SCENE_TYPE_UNKNOWN(0, "未知"),
    /**
     * 车位检测类型
    */
    EM_PARKING_SPACE_SCENE_TYPE_PARKING_SPACE(1, "车位检测类型"),
    /**
     * 车位统计类型
    */
    EM_PARKING_SPACE_SCENE_TYPE_PARKING_STATISTICS(2, "车位统计类型");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKING_SPACE_SCENE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKING_SPACE_SCENE_TYPE enumType : EM_PARKING_SPACE_SCENE_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKING_SPACE_SCENE_TYPE enumType : EM_PARKING_SPACE_SCENE_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

