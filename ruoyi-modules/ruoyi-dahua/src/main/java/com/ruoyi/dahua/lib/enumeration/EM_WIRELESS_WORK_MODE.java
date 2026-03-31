package com.ruoyi.dahua.lib.enumeration;

/**
 * 工作模式选择
*/
public enum EM_WIRELESS_WORK_MODE
{
    EM_WIRELESS_WORK_MODE_UNKNOWN(0, ""),
    /**
     * CDMA1x
    */
    EM_WIRELESS_WORK_MODE_CDMA1X(1, "CDMA1x"),
    /**
     * EVDO
    */
    EM_WIRELESS_WORK_MODE_EVDO(2, "EVDO"),
    /**
     * TD-SCDMA
    */
    EM_WIRELESS_WORK_MODE_TD_SCDMA(3, "TD-SCDMA"),
    /**
     * WCDMA
    */
    EM_WIRELESS_WORK_MODE_WCDMA(4, "WCDMA"),
    /**
     * EDGE
    */
    EM_WIRELESS_WORK_MODE_EDGE(5, "EDGE"),
    /**
     * TDD-LTE
    */
    EM_WIRELESS_WORK_MODE_TDD_LTE(6, "TDD-LTE"),
    /**
     * FDD-LTE
    */
    EM_WIRELESS_WORK_MODE_FDD_LTE(7, "FDD-LTE"),
    /**
     * GPRS
    */
    EM_WIRELESS_WORK_MODE_GPRS(8, "GPRS"),
    /**
     * NR
    */
    EM_WIRELESS_WORK_MODE_NR(9, "NR"),
    /**
     * NOService
    */
    EM_WIRELESS_WORK_MODE_NO_SERVICE(10, "NOService"),
    /**
     * AMPS
    */
    EM_WIRELESS_WORK_MODE_AMPS(11, "AMPS"),
    /**
     * CDMA2000
    */
    EM_WIRELESS_WORK_MODE_CDMA2000(12, "CDMA2000"),
    /**
     * GSM
    */
    EM_WIRELESS_WORK_MODE_GSM(13, "GSM"),
    /**
     * LTE
    */
    EM_WIRELESS_WORK_MODE_LTE(14, "LTE"),
    /**
     * TD-LTE
    */
    EM_WIRELESS_WORK_MODE_TD_LTE(15, "TD-LTE"),
    /**
     * Auto
    */
    EM_WIRELESS_WORK_MODE_AUTO(16, "Auto");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_WIRELESS_WORK_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_WIRELESS_WORK_MODE enumType : EM_WIRELESS_WORK_MODE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_WIRELESS_WORK_MODE enumType : EM_WIRELESS_WORK_MODE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

