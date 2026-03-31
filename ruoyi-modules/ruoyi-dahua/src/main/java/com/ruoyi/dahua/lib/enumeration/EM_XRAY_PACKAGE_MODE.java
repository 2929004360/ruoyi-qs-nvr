package com.ruoyi.dahua.lib.enumeration;


/**
 * 包裹产生方式
 * 
 * @author ： 260611
 * @since ： Created in 2021/10/19 09:19
 */
public enum EM_XRAY_PACKAGE_MODE {
    EM_XRAY_PACKAGE_MODE_UNKNOWN(-1, "未知方式"),
    EM_XRAY_PACKAGE_MODE_LOCAL(0, "本地判图结果"),
    EM_XRAY_PACKAGE_MODE_CENTRAL(1, "集中判图模式中的本地预判结果");

    private int value;
    private String note;

    private EM_XRAY_PACKAGE_MODE(int givenValue, String note) {
        this.value = givenValue;
        this.note = note;
    }
}

