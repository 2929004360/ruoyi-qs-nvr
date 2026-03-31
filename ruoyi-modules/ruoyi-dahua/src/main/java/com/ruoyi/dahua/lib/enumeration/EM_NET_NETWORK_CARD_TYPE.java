package com.ruoyi.dahua.lib.enumeration;

/**
 * 网卡类型
*/
public enum EM_NET_NETWORK_CARD_TYPE
{
    /**
     * 未知网卡类型
    */
    EM_NET_NETWORK_CARD_TYPE_UNKNOWN(0, "未知网卡类型"),
    /**
     * 普通有线网口
    */
    EM_NET_NETWORK_CARD_TYPE_NORMAL(1, "普通有线网口"),
    /**
     * 光口网络
    */
    EM_NET_NETWORK_CARD_TYPE_FIBER(2, "光口网络"),
    /**
     * 无线网络
    */
    EM_NET_NETWORK_CARD_TYPE_WIRELESS(3, "无线网络"),
    /**
     * TD
    */
    EM_NET_NETWORK_CARD_TYPE_TD_SCDMA(4, "TD"),
    /**
     * WCDMA
    */
    EM_NET_NETWORK_CARD_TYPE_WCDMA(5, "WCDMA"),
    /**
     * CDMA1X
    */
    EM_NET_NETWORK_CARD_TYPE_CDMA1X(6, "CDMA1X"),
    /**
     * CDMA2000
    */
    EM_NET_NETWORK_CARD_TYPE_CDMA2000(7, "CDMA2000"),
    /**
     * GSM
    */
    EM_NET_NETWORK_CARD_TYPE_GSM(8, "GSM"),
    /**
     * GPRS
    */
    EM_NET_NETWORK_CARD_TYPE_GPRS(9, "GPRS"),
    /**
     * EDGE
    */
    EM_NET_NETWORK_CARD_TYPE_EDGE(10, "EDGE"),
    /**
     * LTE
    */
    EM_NET_NETWORK_CARD_TYPE_LTE(11, "LTE"),
    /**
     * TD_LTE
    */
    EM_NET_NETWORK_CARD_TYPE_TD_LTE(12, "TD_LTE"),
    /**
     * FDD-LTE
    */
    EM_NET_NETWORK_CARD_TYPE_FDD_LTE(13, "FDD-LTE"),
    /**
     * EVDO
    */
    EM_NET_NETWORK_CARD_TYPE_EVDO(14, "EVDO");

    private int value;
    private String note;

    public String getNote() {
        return note;
    }

    public int getValue() {
        return value;
    }

    EM_NET_NETWORK_CARD_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (EM_NET_NETWORK_CARD_TYPE enumType : EM_NET_NETWORK_CARD_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (EM_NET_NETWORK_CARD_TYPE enumType : EM_NET_NETWORK_CARD_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }
}

