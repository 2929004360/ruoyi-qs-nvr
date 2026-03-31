package com.ruoyi.dahua.lib.enumeration;

/**
 * 综合状态
*/
public enum EM_PARKINGSPACE_STATUS
{
    EM_PARKINGSPACE_STATUS_UNKNOWN(0, ""),
    /**
     * 占用
    */
    EM_PARKINGSPACE_STATUS_BUSY(1, "占用"),
    /**
     * 空闲
    */
    EM_PARKINGSPACE_STATUS_FREE(2, "空闲"),
    /**
     * 压线
    */
    EM_PARKINGSPACE_STATUS_OVERLINE(3, "压线");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_PARKINGSPACE_STATUS(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_PARKINGSPACE_STATUS enumType : EM_PARKINGSPACE_STATUS.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_PARKINGSPACE_STATUS enumType : EM_PARKINGSPACE_STATUS.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

