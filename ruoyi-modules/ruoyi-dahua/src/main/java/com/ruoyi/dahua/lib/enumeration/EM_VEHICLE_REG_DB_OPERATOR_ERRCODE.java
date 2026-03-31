package com.ruoyi.dahua.lib.enumeration;

public enum EM_VEHICLE_REG_DB_OPERATOR_ERRCODE {
    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_UNKNWON(0, "未知"),// 未知
    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_SUCCESS(1, "成功"),// 成功
    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_DB_ERROR(2, "数据库操作失败"),// 数据库操作失败
    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_NOT_EXIST(3, "车牌不存在"),// 车牌库不存在
    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_EXCEED_MAX_NUM(4, "超出车牌库数量限制");// 超出车牌库数量限制

    private int state;
    private String desc;

    EM_VEHICLE_REG_DB_OPERATOR_ERRCODE(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public EM_VEHICLE_REG_DB_OPERATOR_ERRCODE getTaskState(int state) {
        for (EM_VEHICLE_REG_DB_OPERATOR_ERRCODE taskState : EM_VEHICLE_REG_DB_OPERATOR_ERRCODE.values()) {
            if (taskState.state == state) {
                return taskState;
            }
        }
        return EM_VEHICLE_REG_DB_OPERATOR_ERRCODE_UNKNWON;
    }
}

