package com.ruoyi.dahua.lib.enumeration;


/**
 * X光机图片类型
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 09:19
 */
public enum EM_XRAY_IMAGE_TYPE {
    EM_XRAY_IMAGE_TYPE_UNKNOWN(-1, "未知"),
    EM_XRAY_IMAGE_TYPE_COLOR(0, "彩图"),
    EM_XRAY_IMAGE_COLOR_TYPE_OVERLAY(1, "彩色叠加图");

    private int value;
    private String note;

    private EM_XRAY_IMAGE_TYPE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }
}

