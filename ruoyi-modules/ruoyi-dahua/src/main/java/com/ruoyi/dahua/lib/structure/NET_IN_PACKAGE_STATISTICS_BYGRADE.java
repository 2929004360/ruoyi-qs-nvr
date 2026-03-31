package com.ruoyi.dahua.lib.structure;

import com.ruoyi.dahua.lib.NetSDKLib;

/**
 * @author 291189
 * @version 1.0
 * @description  通过危险物等级统计的包裹信息
 * @date 2021/7/1
 */
public class NET_IN_PACKAGE_STATISTICS_BYGRADE extends NetSDKLib.SdkStructure {
    /**
     *  危险物等级,参考枚举{@link NetSDKLib.EM_DANGER_GRADE_TYPE }
     */
    public int              emGrade;                              // 危险物等级
    public int              nCount;                               // 危险物数量
    public byte[]           byReserved = new byte[64];            // 预留字段
}

