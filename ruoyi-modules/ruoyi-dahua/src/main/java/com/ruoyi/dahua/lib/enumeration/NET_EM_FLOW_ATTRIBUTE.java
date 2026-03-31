package com.ruoyi.dahua.lib.enumeration;


/**
 * @author ： 260611
 * @description ： 车道流量信息属性
 * @since ： Created in 2022/03/10 10:12
 */

public enum NET_EM_FLOW_ATTRIBUTE {
    /**
     * 
     */
    EM_FLOW_ATTRIBUTE_UNKNOWN(0, ""),
    /**
     * 流量监测
     */
    EM_FLOW_ATTRIBUTE_FLOW_DETECTION(1, "流量监测"),
    /**
     * 排队检测
     */
    EM_FLOW_ATTRIBUTE_QUEUE_DETECTION(2, "排队检测");

    private int value;
    private String note;

    public String getNote() {
             return note;
         }

    public int getValue() {
             return value;
         }

    NET_EM_FLOW_ATTRIBUTE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

    public static String getNoteByValue(int givenValue) {
        for (NET_EM_FLOW_ATTRIBUTE enumType : NET_EM_FLOW_ATTRIBUTE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

    public static int getValueByNote(String givenNote) {
        for (NET_EM_FLOW_ATTRIBUTE enumType : NET_EM_FLOW_ATTRIBUTE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

    public static NET_EM_FLOW_ATTRIBUTE getEnum(int value) {
        for (NET_EM_FLOW_ATTRIBUTE e : NET_EM_FLOW_ATTRIBUTE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return NET_EM_FLOW_ATTRIBUTE.EM_FLOW_ATTRIBUTE_UNKNOWN;
    }
}

