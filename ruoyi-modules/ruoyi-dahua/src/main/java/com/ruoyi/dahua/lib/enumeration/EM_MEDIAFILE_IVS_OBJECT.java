package com.ruoyi.dahua.lib.enumeration;

/**
 * 查询智能事件信息的对象类型
*/
public enum EM_MEDIAFILE_IVS_OBJECT
{
    /**
     * unknown
    */
    EM_MEDIAFILE_IVS_UNKNOWN(0, "unknown"),
    /**
     * other
    */
    EM_MEDIAFILE_IVS_OTHER(1, "other"),
    /**
     * human
    */
    EM_MEDIAFILE_IVS_HUMAN(2, "human"),
    /**
     * vehicle
    */
    EM_MEDIAFILE_IVS_VEHICLE(3, "vehicle"),
    /**
     * nonmotor
    */
    EM_MEDIAFILE_IVS_NONMOTOR(4, "nonmotor");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_MEDIAFILE_IVS_OBJECT(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_MEDIAFILE_IVS_OBJECT enumType : EM_MEDIAFILE_IVS_OBJECT.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_MEDIAFILE_IVS_OBJECT enumType : EM_MEDIAFILE_IVS_OBJECT.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

