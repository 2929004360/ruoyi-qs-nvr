package com.ruoyi.dahua.lib.enumeration;

/**
 * 抓拍策略取值
 *
 * @author 47040
 * @since Created in 2021/4/25 19:52
 */
public enum EM_SNAP_POLICY_TYPE {
    EM_SNAP_POLICY_TYPE_UNKNOWN(0, "未知"),
    EM_SNAP_POLICY_TYPE_REALTIME(1, "实时"),
    EM_SNAP_POLICY_TYPE_OPTIMAL(2, "优选"),
    EM_SNAP_POLICY_TYPE_QUALITY(3, "质量"),
    EM_SNAP_POLICY_TYPE_TRIPLINE(4, "拌线");

    private final int value;
    private final String note;

    EM_SNAP_POLICY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_SNAP_POLICY_TYPE enumType : EM_SNAP_POLICY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_SNAP_POLICY_TYPE enumType : EM_SNAP_POLICY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static EM_SNAP_POLICY_TYPE getEnum(int value) {
        for (EM_SNAP_POLICY_TYPE e : EM_SNAP_POLICY_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_SNAP_POLICY_TYPE.EM_SNAP_POLICY_TYPE_UNKNOWN;
    }
}

