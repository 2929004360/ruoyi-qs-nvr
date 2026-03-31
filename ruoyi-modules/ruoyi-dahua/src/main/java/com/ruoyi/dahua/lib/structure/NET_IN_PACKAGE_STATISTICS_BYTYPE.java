package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;
/**
 * @author 291189
 * @version 1.0
 * @description   按危险类型统计的信息
 * @date 2021/7/1
 */
public class NET_IN_PACKAGE_STATISTICS_BYTYPE extends NetSDKLib.SdkStructure {
    /**
     *  危险物等级,参考枚举{@link NetSDKLib.EM_INSIDE_OBJECT_TYPE }
     */
    public int              emType;                               // 危险物类型
    public int              nCount;                               // 危险物数量
    public byte[]           byReserved = new byte[64];            // 预留字段
}

