package com.ruoyi.haikang.enums;

import com.ruoyi.haikang.net.HCNetSDK;

public enum HCCruiseControlEnum {
    PAN_AUTO(HCNetSDK.PAN_AUTO, "云台左右自动扫描", "pan_auto"),
    FILL_PRE_SEQ(HCNetSDK.FILL_PRE_SEQ, "将预置点加入巡航序列", "fill_pre_seq"),
    SET_SEQ_DWELL(HCNetSDK.SET_SEQ_DWELL, "设置巡航点停顿时间", "set_seq_dwell"),
    SET_SEQ_SPEED(HCNetSDK.SET_SEQ_SPEED, "设置巡航速度", "set_seq_speed"),
    CLE_PRE_SEQ(HCNetSDK.CLE_PRE_SEQ, "将预置点从巡航序列中删除", "cle_pre_seq"),
    STA_MEM_CRUISE(HCNetSDK.STA_MEM_CRUISE, "开始记录巡航", "sta_mem_cruise"),
    STO_MEM_CRUISE(HCNetSDK.STO_MEM_CRUISE, "停止记录巡航", "sto_mem_cruise"),
    RUN_CRUISE(HCNetSDK.RUN_CRUISE, "开始巡航", "run_cruise"),
    RUN_SEQ(HCNetSDK.RUN_SEQ, "开始巡航序列", "run_seq"),
    STOP_SEQ(HCNetSDK.STOP_SEQ, "停止巡航", "stop_seq");

    private Integer code;
    private String msg;
    private String value;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getValue() {
        return value;
    }

    HCCruiseControlEnum(Integer code, String msg, String value) {
        this.code = code;
        this.msg = msg;
        this.value = value;
    }

    public static HCCruiseControlEnum fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (HCCruiseControlEnum item : HCCruiseControlEnum.values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
