package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 251823
 * @description 报警布撤防控制扩展(对应DH_CTRL_ARMED_EX)
 * @date 2021/10/29
 */
public class CTRL_ARM_DISARM_PARAM_EX extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  报警布撤防控制输入
     */
    public CTRL_ARM_DISARM_PARAM_EX_IN stuIn = new CTRL_ARM_DISARM_PARAM_EX_IN();
    /**
     *  报警布撤防控制输出
     */
    public CTRL_ARM_DISARM_PARAM_EX_OUT stuOut = new CTRL_ARM_DISARM_PARAM_EX_OUT();

    public CTRL_ARM_DISARM_PARAM_EX(){
        this.dwSize = this.size();
    }
}

