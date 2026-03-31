package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  厨师帽相关属性状态信息
 * @date 2021/7/26 15:05
 */
public class NET_SMART_KITCHEN_CLOTHES_CHEF_HAT extends NetSDKLib.SdkStructure {
    public  int             bChefHat;                             // 厨师帽相关属性是否生效，true 生效 ，false 不生效
    public int[]            emHasChefHat = new int[3];            /** 是否有带厨师帽 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_STATE} */
    public 	byte[]          byReserved = new		byte[1024];         // 预留字段
}

