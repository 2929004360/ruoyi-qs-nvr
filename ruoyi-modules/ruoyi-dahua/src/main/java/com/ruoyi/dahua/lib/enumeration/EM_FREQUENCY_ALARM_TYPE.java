package com.ruoyi.dahua.lib.enumeration;
/**
 * @author 291189
 * @version 1.0
 * @description  报警类型
 * @date 2021/7/21 11:18
 */
public enum EM_FREQUENCY_ALARM_TYPE {
    EM_FREQUENCY_ALARM_UNKNOWN(-1),// 未知
    EM_FREQUENCY_ALARM_HIGH(0),// 高频报警
    EM_FREQUENCY_ALARM_LOW(1);// 低频报警

    private int id ;

    private EM_FREQUENCY_ALARM_TYPE(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

