package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 251823
 * @description 报警布撤防控制扩展输出参数
 * @date 2021/10/29
 */
public class CTRL_ARM_DISARM_PARAM_EX_OUT extends NetSDKLib.SdkStructure {
	/**
     * 结构体大小
     */
    public int              dwSize;
    /**
     *  有报警源输入布防失败的防区个数
     */
    public int              dwSourceNum;
    /**
     *  有报警源输入失败的防区号, 防区号从0开始
     */
    public int[]            nSource = new int[NetSDKLib.ARM_DISARM_ZONE_MAX];
    /**
     *  有联动报警布防失败的防区个数
     */
    public int              dwLinkNum;
    /**
     *  有联动报警失败的防区号, 防区号从0开始
     */
    public int[]            nLink = new int[NetSDKLib.ARM_DISARM_ZONE_MAX];

    public CTRL_ARM_DISARM_PARAM_EX_OUT(){
        this.dwSize = this.size();
    }
}

