package com.ruoyi.gb28181.api.common.enums;

/**
 * 录像控制命令类型
 *
 * @author ruoyi
 */
public enum RecordType {

    /**
     * 停止录像
     */
    STOP("0", "停止录像"),

    /**
     * 开始录像
     */
    START("1", "开始录像"),

    /**
     * 定时录像
     */
    TIMED("2", "定时录像");

    private final String val;
    private final String desc;

    RecordType(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
