package com.ruoyi.dahua.lib.structure;


import com.ruoyi.dahua.lib.NetSDKLib;
import com.ruoyi.dahua.lib.enumeration.EM_COMPLIANCE_STATE;
import com.ruoyi.dahua.lib.enumeration.EM_WEARING_STATE;

/**
 * @author ： 260611
 * @description ： 口罩相关属性状态信息
 * @since ： Created in 2022/03/10 11:17
 */

public class NET_MASK_ATTRIBUTE extends NetSDKLib.SdkStructure {
    /**
     * 是否有戴口罩,{@link EM_WEARING_STATE}
     */
    public int              emHasMask;
    /**
     * 口罩检测结果,{@link EM_COMPLIANCE_STATE}
     */
    public int              emHasLegalMask;
}

