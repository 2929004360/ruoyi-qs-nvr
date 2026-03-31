package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 421657
 * @description 云台动作配置, 对应枚举 NET_EM_CFG_PTZ_MOVEMENT
 * @origin autoTool
 * @date 2023/10/16 19:23:11
 */
public class NET_CFG_PTZ_MOVEMENT_INFO extends NetSDKLib.SdkStructure {
    /**
     * / 结构体大小, 必须赋值
     */
    public int              dwSize;
    /**
     * / 云台功能 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_FUNCTION}
     */
    public int              emFunction;
    /**
     * / 功能编号,当"Function"为"HeatMap"时, Index为热度图计划号
     */
    public int              nIndex;
    /**
     * / 任务名称 {@link com.ruoyi.dahua.lib.enumeration.EM_PTZ_TASK_NAME}
     */
    public int              emTaskName;

    public NET_CFG_PTZ_MOVEMENT_INFO() {
        this.dwSize = this.size();
    }
}

