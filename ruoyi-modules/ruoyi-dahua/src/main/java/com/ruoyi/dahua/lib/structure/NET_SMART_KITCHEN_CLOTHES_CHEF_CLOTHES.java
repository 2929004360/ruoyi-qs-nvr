package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description 厨师服相关属性状态信息
 * @date 2021/7/26 15:29
 */
public class NET_SMART_KITCHEN_CLOTHES_CHEF_CLOTHES extends NetSDKLib.SdkStructure {
    public int              bChefClothes;                         // 厨师服相关属性是否生效，true 生效 ，false 不生效
    public int[]            emHasChefClothes = new int[3];        /** 是否有带厨师服 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_STATE} */
    public int[]            emChefClothesColor = new int[3];      /** 厨师服颜色 {@link com.ruoyi.dahua.lib.enumeration.EM_SMART_KITCHEN_CLOTHES_COLOUR} */
    public byte[]           byReserved = new byte[1024];          // 预留字段
}

