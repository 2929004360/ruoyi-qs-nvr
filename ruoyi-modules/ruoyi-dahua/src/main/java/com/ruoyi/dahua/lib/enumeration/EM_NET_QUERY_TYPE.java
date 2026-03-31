package com.ruoyi.dahua.lib.enumeration;


/** 
* @author 291189
* @description  获取网卡信息类型 CLIENT_QueryNetStat  接口使用 
* @origin autoTool
* @date 2023/06/16 16:38:23
*/
public enum EM_NET_QUERY_TYPE {
/**
获取协议栈统计数据,(输入结构体NET_IN_NETAPP_NET_DATA_STAT,输出结构体NET_OUT_NETAPP_NET_DATA_STAT)
*/
    NET_APP_DATA_STAT(0, "获取协议栈统计数据,(输入结构体NET_IN_NETAPP_NET_DATA_STAT,输出结构体NET_OUT_NETAPP_NET_DATA_STAT)"),
/**
获取物理链路状态,(输入结构体NET_IN_NETAPP_LINK_STATUS,输出结构体NET_OUT_NETAPP_LINK_STATUS)
*/
    NET_APP_LINK_STAT(1, "获取物理链路状态,(输入结构体NET_IN_NETAPP_LINK_STATUS,输出结构体NET_OUT_NETAPP_LINK_STATUS)");

private int value;
private String note;

public String getNote() {
        return note;
    }

public int getValue() {
        return value;
    }

EM_NET_QUERY_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }

public static String getNoteByValue(int givenValue) {
        for (EM_NET_QUERY_TYPE enumType : EM_NET_QUERY_TYPE.values()) {
            if (givenValue == enumType.getValue()) {
                return enumType.getNote();
            }
        }
        return null;
    }

public static int getValueByNote(String givenNote) {
        for (EM_NET_QUERY_TYPE enumType : EM_NET_QUERY_TYPE.values()) {
            if (givenNote.equals(enumType.getNote())) {
                return enumType.getValue();
            }
        }
        return -1;
    }

public static EM_NET_QUERY_TYPE getEnum(int value) {
        for (EM_NET_QUERY_TYPE e : EM_NET_QUERY_TYPE.values()) {
            if (e.getValue() == value)
                return e;
        }
        return EM_NET_QUERY_TYPE.NET_APP_DATA_STAT;
    }
}

